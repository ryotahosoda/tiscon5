package com.tiscon.service;

import com.tiscon.code.OptionalServiceType;
import com.tiscon.code.PackageType;
import com.tiscon.dao.EstimateDao;
import com.tiscon.domain.Customer;
import com.tiscon.domain.CustomerOptionService;
import com.tiscon.domain.CustomerPackage;
import com.tiscon.domain.TruckCapacity;
import com.tiscon.dto.UserOrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;

/**
 * 引越し見積もり機能において業務処理を担当するクラス。
 *
 * @author Oikawa Yumi
 */
@Service
public class EstimateService {

    /** 引越しする距離の1 kmあたりの料金[円] */
    private static final int PRICE_PER_DISTANCE = 100;

    private final EstimateDao estimateDAO;

    /**
     * コンストラクタ
     *
     * @param estimateDAO EstimateDaoクラス
     */
    public EstimateService(EstimateDao estimateDAO) {
        this.estimateDAO = estimateDAO;
    }

    /**
     * 見積もり依頼をDBに登録する。
     *
     * @param dto 見積もり依頼情報
     */
    @Transactional
    public void registerOrder(UserOrderDto dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        estimateDAO.insertCustomer(customer);

        if (dto.getHasWashingMachineSettingOption()) {
            CustomerOptionService washingMachine = new CustomerOptionService();
            washingMachine.setCustomerId(customer.getCustomerId());
            washingMachine.setServiceId(OptionalServiceType.WASHING_MACHINE.getCode());
            estimateDAO.insertCustomersOptionService(washingMachine);
        }

        List<CustomerPackage> packageList = new ArrayList<>();

        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BOX.getCode(), dto.getBox()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BED.getCode(), dto.getBed()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BICYCLE.getCode(), dto.getBicycle()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.WASHING_MACHINE.getCode(), dto.getWashingMachine()));
        estimateDAO.batchInsertCustomerPackage(packageList);
    }

    /**
     * 見積もり依頼に応じた概算見積もりを行う。
     *
     * @param dto 見積もり依頼情報
     * @return 概算見積もり結果の料金
     */
    public int[] getPrice(UserOrderDto dto) {
        double distance = estimateDAO.getDistance(dto.getOldPrefectureId(), dto.getNewPrefectureId());
        // 小数点以下を切り捨てる
        int distanceInt = (int) Math.floor(distance);

        // 距離当たりの料金を算出する
        int priceForDistance = distanceInt * PRICE_PER_DISTANCE;

        int boxes = getBoxForPackage(dto.getBox(), PackageType.BOX)
                + getBoxForPackage(dto.getBed(), PackageType.BED)
                + getBoxForPackage(dto.getBicycle(), PackageType.BICYCLE)
                + getBoxForPackage(dto.getWashingMachine(), PackageType.WASHING_MACHINE);

        // 箱に応じてトラックの種類と台数が変わり、それに応じて料金が変わるためトラック料金を算出する。
        int pricePerTruck = this.getPriceForTruck(boxes);


        // オプションサービスの料金を算出する。
        int priceForOptionalService = 0;

        if (dto.getHasWashingMachineSettingOption()) {
            priceForOptionalService = estimateDAO.getPricePerOptionalService(OptionalServiceType.WASHING_MACHINE.getCode());
        }
        int [] a = {priceForDistance, pricePerTruck, priceForOptionalService, priceForDistance + pricePerTruck + priceForOptionalService};
        return a;
        //return priceForDistance + pricePerTruck + priceForOptionalService;
    }

    /**
     * 荷物当たりの段ボール数を算出する。
     *
     * @param packageNum 荷物数
     * @param type       荷物の種類
     * @return 段ボール数
     */
    private int getBoxForPackage(int packageNum, PackageType type) {
        return packageNum * estimateDAO.getBoxPerPackage(type.getCode());
    }


    /**
     * 段ボール数からトラック料金を算出する。
     *
     * @param boxes ダンボール数
     * @return トラック料金
     */
    private int getPriceForTruck(int boxes) {
        List<TruckCapacity> listTruckCapacity = estimateDAO.getAllTruckCapacity();
        List<TruckCapacity> ascListTruckCapacityOrderByMaxBox = listTruckCapacity.stream().sorted(Comparator.comparing(TruckCapacity::getMaxBox, reverseOrder())).collect(Collectors.toList());
        List<TruckCapacity> descListTruckCapacityOrderByMaxBox = listTruckCapacity.stream().sorted(Comparator.comparing(TruckCapacity::getMaxBox)).collect(Collectors.toList());

        int price;
        int maxBox;
        int numTrucks;
        int remBoxes = boxes;
        int priceForTruck;

        /* 積載量が最大のトラックが必要となる場合、その台数分の費用を加算する */
        price = Integer.parseInt(descListTruckCapacityOrderByMaxBox.get(0).getPrice());
        maxBox = Integer.parseInt(descListTruckCapacityOrderByMaxBox.get(0).getMaxBox());
        numTrucks = remBoxes / maxBox;
        priceForTruck = price * numTrucks;
        remBoxes = remBoxes % maxBox;

        if (remBoxes == 0) {
            return priceForTruck;
        }

        /* 残りのダンボールを積むのに必要なトラックの費用を加算する */
        for (TruckCapacity truckCapacity : ascListTruckCapacityOrderByMaxBox){
            maxBox = Integer.parseInt(truckCapacity.getMaxBox());
            System.out.println(maxBox);
            if (maxBox >= remBoxes) {
                price = Integer.parseInt(truckCapacity.getPrice());
                priceForTruck += price;
                break;
            }
        }

        return priceForTruck;
    }
}
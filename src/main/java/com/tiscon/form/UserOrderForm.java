package com.tiscon.form;

import com.tiscon.validator.Numeric;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 顧客が入力する見積もり情報を保持するクラス。
 *
 * @author Oikawa Yumi
 */
public class UserOrderForm {
    @NotBlank
    private String customerName;

    @NotBlank
    @Numeric
    @Size(min = 10, max = 11)
    private String tel;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String oldZip;

    @NotBlank
    private String oldPrefectureId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String oldAddress;

    @NotBlank
    private String oldX;

    @NotBlank String oldY;

    @NotBlank
    private String newZip;

    @NotBlank
    private String newPrefectureId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String newAddress;

    @NotBlank
    private String newX;

    @NotBlank
    private String newY;

    @Numeric
    @NotBlank
    private String box;

    @Numeric
    @NotBlank
    private String bed;

    @Numeric
    @NotBlank
    private String bicycle;

    @Numeric
    @NotBlank
    private String washingMachine;

    @NotNull
    private Boolean hasWashingMachineSettingOption;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldZip() { return oldZip; }

    public void setOldZip(String oldZip) {this.oldZip = oldZip; }

    public String getOldPrefectureId() {
        return oldPrefectureId;
    }

    public void setOldPrefectureId(String oldPrefectureId) {
        this.oldPrefectureId = oldPrefectureId;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public String getOldX() { return oldX; }

    public void setOldX(String oldX) { this.oldX = oldX; }

    public String getOldY() { return oldY; }

    public void setOldY(String oldY) { this.oldY = oldY; }

    public String getNewZip() { return newZip; }

    public void setNewZip(String newZip) { this.newZip = newZip; }

    public String getNewPrefectureId() {
        return newPrefectureId;
    }

    public void setNewPrefectureId(String newPrefectureId) {
        this.newPrefectureId = newPrefectureId;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getNewX() { return newX; }

    public void setNewX(String newX) { this.newX = newX; }

    public String getNewY() { return newY; }

    public void setNewY(String newY) { this.newY = newY; }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBicycle() {
        return bicycle;
    }

    public void setBicycle(String bicycle) {
        this.bicycle = bicycle;
    }

    public String getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(String washingMachine) {
        this.washingMachine = washingMachine;
    }

    public Boolean getHasWashingMachineSettingOption() {
        return hasWashingMachineSettingOption;
    }

    public void setHasWashingMachineSettingOption(Boolean hasWashingMachineSettingOption) {
        this.hasWashingMachineSettingOption = hasWashingMachineSettingOption;
    }
}

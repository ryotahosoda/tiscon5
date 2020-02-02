package com.tiscon.domain;

import java.io.Serializable;

public class TruckCapacity implements Serializable {
    private String truckId;

    private String truckType;

    private String maxBox;

    private String price;

    public String getTruckId() { return truckId; }

    public void setTruckId(String truckId) { this.truckId = truckId; }

    public String getTruckType() { return truckType; }

    public void setTruckType(String truckType) { this.truckType = truckType; }

    public String getMaxBox() { return maxBox; }

    public void setMaxBox(String maxBox) { this.maxBox = maxBox; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }
}
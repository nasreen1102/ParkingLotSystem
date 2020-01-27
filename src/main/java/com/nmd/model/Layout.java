package com.nmd.model;

import com.nmd.enums.VehicleType;

import java.util.Map;
import java.util.UUID;

public class Layout {
    private UUID parkingLotId;
    private Map<VehicleType, SlotLayout> vehicleTypeSlotLayoutMap;

    public UUID getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(UUID parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Map<VehicleType, SlotLayout> getVehicleTypeSlotLayoutMap() {
        return vehicleTypeSlotLayoutMap;
    }

    public void setVehicleTypeSlotLayoutMap(Map<VehicleType, SlotLayout> vehicleTypeSlotLayoutMap) {
        this.vehicleTypeSlotLayoutMap = vehicleTypeSlotLayoutMap;
    }
}

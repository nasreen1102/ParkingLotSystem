package com.nmd.model;

import com.nmd.enums.VehicleType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLotDetail {
    private UUID parkingLotId;
    private List<ParkingFare> parkingFares;
    private Map<VehicleType, Integer> vehicleTypeToSlotCountMap;

    public UUID getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(UUID parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public List<ParkingFare> getParkingFares() {
        return parkingFares;
    }

    public void setParkingFares(List<ParkingFare> parkingFares) {
        this.parkingFares = parkingFares;
    }

    public Map<VehicleType, Integer> getVehicleTypeToSlotCountMap() {
        return vehicleTypeToSlotCountMap;
    }

    public void setVehicleTypeToSlotCountMap(Map<VehicleType, Integer> vehicleTypeToSlotCountMap) {
        this.vehicleTypeToSlotCountMap = vehicleTypeToSlotCountMap;
    }

    public boolean isValid(){
        return parkingLotId !=null && (parkingFares!=null && !parkingFares.isEmpty()) && vehicleTypeToSlotCountMap!=null && !vehicleTypeToSlotCountMap.isEmpty();
    }
}

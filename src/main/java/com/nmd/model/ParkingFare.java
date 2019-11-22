package com.nmd.model;

import com.nmd.enums.VehicleType;

public class ParkingFare {
    private VehicleType vehicleType;
    private Float firstOneHourFare;
    private Float nextEachHourFare;

    public ParkingFare(VehicleType vehicleType, Float firstOneHourFare, Float nextEachHourFare) {
        this.vehicleType = vehicleType;
        this.firstOneHourFare = firstOneHourFare;
        this.nextEachHourFare = nextEachHourFare;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Float getFirstOneHourFare() {
        return firstOneHourFare;
    }

    public void setFirstOneHourFare(Float firstOneHourFare) {
        this.firstOneHourFare = firstOneHourFare;
    }

    public Float getNextEachHourFare() {
        return nextEachHourFare;
    }

    public void setNextEachHourFare(Float nextEachHourFare) {
        this.nextEachHourFare = nextEachHourFare;
    }
}

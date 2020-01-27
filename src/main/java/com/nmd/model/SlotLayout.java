package com.nmd.model;

import com.nmd.enums.VehicleType;

public class SlotLayout {
    private Integer id;
    private VehicleType vehicleType;
    private boolean isBooked;

    public SlotLayout(Integer id, VehicleType vehicleType, boolean isBooked) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.isBooked = isBooked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}

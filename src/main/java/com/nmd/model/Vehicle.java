package com.nmd.model;

import com.nmd.enums.VehicleType;

public class Vehicle {
    private String id;
    private VehicleType vehicleType;

    public Vehicle(String id, VehicleType vehicleType) {
        this.id = id;
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }

    public boolean isValid() {
        return id!=null && VehicleType.isValid(vehicleType) ;
    }
}

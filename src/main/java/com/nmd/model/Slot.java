package com.nmd.model;

import com.nmd.enums.VehicleType;

public class Slot {
    private Integer id;
    private VehicleType vehicleType;
    private ParkingLot parkingLot;
    private Booking booking;

    public Slot() {
    }

    public Slot(Integer id, VehicleType vehicleType, ParkingLot parkingLot) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.parkingLot = parkingLot;
    }

    public Integer getId() {
        return id;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Boolean isBooked(){
        return booking != null;
    }

    public boolean isValid() {
        return id!=null && VehicleType.isValid(vehicleType) && parkingLot.isValid();
    }
}

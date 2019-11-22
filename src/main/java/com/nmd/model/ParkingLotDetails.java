package com.nmd.model;

import com.nmd.enums.VehicleType;

import java.util.List;
import java.util.Map;

public class ParkingLotDetails {
    private  List<Slot> slots;
    private Map<VehicleType, ParkingFare> slotTypeParkingFareMap;

    public ParkingLotDetails(List<Slot> slots, Map<VehicleType, ParkingFare> slotTypeParkingFareMap) {
        this.slots = slots;
        this.slotTypeParkingFareMap = slotTypeParkingFareMap;
    }

    public Map<VehicleType, ParkingFare> getSlotTypeParkingFareMap() {
        return slotTypeParkingFareMap;
    }

    public void setSlotTypeParkingFareMap(Map<VehicleType, ParkingFare> slotTypeParkingFareMap) {
        this.slotTypeParkingFareMap = slotTypeParkingFareMap;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public Map<VehicleType, List<Slot>> getSlotTypeToSlotMap() {
//todo:
        return  null;
    }
}

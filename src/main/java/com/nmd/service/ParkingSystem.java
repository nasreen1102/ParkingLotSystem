package com.nmd.service;

import com.nmd.enums.VehicleType;
import com.nmd.model.ParkingFare;
import com.nmd.model.ParkingLotDetails;
import com.nmd.model.Slot;
import com.nmd.util.ParkingLotutil;

import java.util.List;
import java.util.Map;

public class ParkingSystem {
    private  List<Slot> slots;
    private Map<VehicleType, List<Slot>> slotTypeToSlotMap;
    private Map<VehicleType, ParkingFare> slotTypeToParkingFareMap;

    public ParkingSystem() {
        ParkingLotDetails parkingLotDetails = ParkingLotutil.buildParkingLot();
        this.slotTypeToSlotMap = parkingLotDetails.getSlotTypeToSlotMap();
        this.slotTypeToParkingFareMap = parkingLotDetails.getSlotTypeParkingFareMap();
        this.slots = parkingLotDetails.getSlots();
    }

    public void showLayout(){
        int column = 10;
        System.out.println("***********parking layout***********");
        System.out.println("--------------------------------------");
        for (int i = 0; i < slots.size(); i++) {
            System.out.print(slots.get(i).getVehicleType().getSpecification());
            if((i+1)%column !=0 ){
                System.out.print(" | ");
            }else {
                System.out.println();
                System.out.println("--------------------------------------");
            }
        }

        if(slots.size()%column !=0) {
            System.out.println();
            System.out.println("--------------------------------------");
        }

    }


}

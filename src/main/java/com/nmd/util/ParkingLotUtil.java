package com.nmd.util;

import com.nmd.enums.VehicleType;
import com.nmd.model.ParkingFare;
import com.nmd.model.ParkingLot;
import com.nmd.model.ParkingLotDetails;
import com.nmd.model.Slot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotUtil {
    private static Scanner sc = new Scanner(System.in);

    public static ParkingLotDetails buildParkingLot() {
        Set<VehicleType> vehicleTypes = buildVehicleTypes();
        List<Slot> slots = getParkingSlotDetails(vehicleTypes);
        Map<VehicleType, ParkingFare> slotTypeParkingFareMap = getFareDetails(vehicleTypes);
        sc.close();
        return new ParkingLotDetails(slots, slotTypeParkingFareMap);
    }

    private static Map<VehicleType, ParkingFare> getFareDetails(Set<VehicleType> vehicleTypes) {
        Map<VehicleType, ParkingFare> slotTypeParkingFareMap = new HashMap<>();
        vehicleTypes.forEach(sType ->{
            System.out.println("Enter the "+sType+" parking fare for first hour");
            float firstOneHourFare = sc.nextFloat();
            System.out.println("Enter the "+sType+" parking fare for next Each hour");
            float nextEachHourFare = sc.nextFloat();
            ParkingFare fare = new ParkingFare(sType, firstOneHourFare, nextEachHourFare);
            slotTypeParkingFareMap.put(sType, fare);
        });
        return slotTypeParkingFareMap;
    }

    private static List<Slot> getParkingSlotDetails(Set<VehicleType> vehicleTypes) {

        ParkingLot parkingLot = new ParkingLot(UUID.randomUUID());
        int count=0;
        List<Slot> slots = new LinkedList<>();
        for (VehicleType vehicleType : vehicleTypes) {
            sc.nextLine();
            System.out.println("Enter the total no of " + vehicleType + " slots for your Parking Lot");
            int noOfSlots = count + sc.nextInt();
            List<Slot> slotList = IntStream.range(count, noOfSlots).boxed().map(id-> new Slot(id, vehicleType, parkingLot)).collect(Collectors.toList());
            count += noOfSlots;
            slots.addAll(slotList);
        }
        parkingLot.setSlots(slots);
        return slots;
    }

    private static Set<VehicleType> buildVehicleTypes() {
        Set<VehicleType> selectedVehicleTypes = new HashSet<>();
        System.out.println("Add type of vehicles to be allowed in the parkingLot(just select id)");
        boolean valid;
        availableVehicleTypes(selectedVehicleTypes);
        System.out.println("Do you need to select all? (y/n):");
        String input = limitScannerToYesOrNo(sc);
        if(input.equals("y")){
            selectedVehicleTypes.addAll(Arrays.asList(VehicleType.values()));
            System.out.println("Added all the listed vehicle types");
        }else {
            System.out.println("Please select id");
            availableVehicleTypes(selectedVehicleTypes);
            String next = "n";
            do {
                int selectedSlotTypeId = sc.nextInt();
                VehicleType selectedVehicleType = VehicleType.getById(selectedSlotTypeId);
                valid = selectedVehicleType != null;
                if (valid && !selectedVehicleTypes.add(selectedVehicleType)) {
                    System.out.println("Duplicate id selected");
                }

                if (selectedVehicleTypes.size() == VehicleType.values().length) {
                    System.out.println("All the vehicle types are selected. Thank you!");
                    break;
                }

                if (valid) {
                    System.out.println("Do you need to add more? (y/n):");
                    next = limitScannerToYesOrNo(sc);
                    if (next.equals("y")) {
                        System.out.println("Please select id");
                        availableVehicleTypes(selectedVehicleTypes);
                    }
                } else {
                    System.out.println("Selected id:" + selectedSlotTypeId + " is invalid ");
                }

            } while (!valid || !Objects.equals(next, "n"));
        }
        return selectedVehicleTypes;
    }

    private static String limitScannerToYesOrNo(Scanner sc) {
        String next;
        do {
            next = sc.next();
            if(!(next.equals("y") || next.equals("n"))){
                System.out.println("Invalid input. Please select either 'y' or 'n'");
            }
        }while(!(next.equals("y") || next.equals("n")));
        return next;
    }

    public static Set<VehicleType> availableVehicleTypes(Set<VehicleType> selectedVehicleTypes) {
        Set<VehicleType> remainingVehicleTypes = new HashSet<>();
        for (VehicleType vehicleType : VehicleType.values()) {
            if(!selectedVehicleTypes.contains(vehicleType)) {
               // System.out.println(vehicleType.getId() + "." + vehicleType.name());
                remainingVehicleTypes.add(vehicleType);
            }
        }

        return remainingVehicleTypes;
    }

    public static void main(String[] args) {
        buildParkingLot();
    }
}

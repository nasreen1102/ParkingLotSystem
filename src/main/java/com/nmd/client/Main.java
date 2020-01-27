package com.nmd.client;

import com.nmd.enums.VehicleType;
import com.nmd.model.Booking;
import com.nmd.model.ParkingLot;
import com.nmd.model.Slot;
import com.nmd.model.Vehicle;
import com.nmd.service.ParkingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService();
        ParkingLot parkingLot = new ParkingLot(UUID.randomUUID());
        parkingService.showLayout(parkingLot.getId());
        Vehicle scooter = new Vehicle("KA01", VehicleType.SCOOTER);
        Booking scooterBooking1 = parkingService.allot(scooter);
        Vehicle bus = new Vehicle("KA02", VehicleType.BUS);
        Booking busBooking1 = parkingService.allot(bus);
        Vehicle car = new Vehicle("KA03", VehicleType.CAR);
        Booking carBooking1 = parkingService.allot(car);

        //Case 1: Parking full case
        Vehicle duplicateCar = new Vehicle("KA03", VehicleType.CAR);//todo: can check duplicate vehicle ids
        parkingService.allot(duplicateCar);

        //case2: release a slot of similar type and assign another vehicle
        parkingService.release(carBooking1);

        Booking carBooking2 = parkingService.allot(duplicateCar);

        //case2: release a slot of different type and assign another vehicle
        parkingService.release(busBooking1);
        parkingService.allot(car);

        ParkingLot parkingLot2 = new ParkingLot(UUID.randomUUID());
        parkingLot2.setSlots(new ArrayList<>());
        parkingService.release(new Booking(UUID.randomUUID(), scooter, LocalDateTime.now(), new Slot(1, scooter.getVehicleType(), parkingLot2)));
    }
}

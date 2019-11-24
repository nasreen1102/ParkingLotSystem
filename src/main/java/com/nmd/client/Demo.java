package com.nmd.client;

import com.nmd.enums.VehicleType;
import com.nmd.model.Booking;
import com.nmd.model.Vehicle;
import com.nmd.service.ParkingSystem;

public class Demo {

    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem();
        parkingSystem.showLayout();
        Vehicle scooter = new Vehicle("KA01", VehicleType.SCOOTER);
        Booking scooterBooking1 = parkingSystem.allot(scooter);
        Vehicle bus = new Vehicle("KA02", VehicleType.BUS);
        Booking busBooking1 = parkingSystem.allot(bus);
        Vehicle car = new Vehicle("KA03", VehicleType.CAR);
        Booking carBooking1 = parkingSystem.allot(car);

        //Case 1: Parking full case
        Vehicle duplicateCar = new Vehicle("KA03", VehicleType.CAR);//todo: can check duplicate vehicle ids
        parkingSystem.allot(duplicateCar);

        //case2: release a slot of similar type and assign another vehicle
        parkingSystem.release(carBooking1);

        Booking carBooking2 = parkingSystem.allot(duplicateCar);

        //case2: release a slot of different type and assign another vehicle
        parkingSystem.release(busBooking1);
        parkingSystem.allot(car);
    }
}

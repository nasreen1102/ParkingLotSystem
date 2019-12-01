package com.nmd.service;

import com.nmd.enums.VehicleType;
import com.nmd.model.*;
import com.nmd.util.ParkingLotutil;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParkingSystem {
    private  List<Slot> slots;
    private Map<VehicleType, List<Slot>> slotTypeToSlotMap;
    private Map<VehicleType, ParkingFare> slotTypeToParkingFareMap;

    //todo: Annotate
    PaymentService paymentService;

    public ParkingSystem() {
        ParkingLotDetails parkingLotDetails = ParkingLotutil.buildParkingLot();
        this.slotTypeToSlotMap = parkingLotDetails.getSlotTypeToSlotMap();
        this.slotTypeToParkingFareMap = parkingLotDetails.getSlotTypeParkingFareMap();
        this.slots = parkingLotDetails.getSlots();
        paymentService = new PaymentService();
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

    public Booking allot(Vehicle vehicle){
        List<Slot> totalSlots = slotTypeToSlotMap.get(vehicle.getVehicleType());
        Predicate<Slot> isBooked = Slot::isBooked;
        Predicate<Slot> isNotBooked = isBooked.negate();
        List<Slot> availableSlots = totalSlots.stream().filter(isNotBooked).collect(Collectors.toList());
        if(availableSlots.isEmpty()){
            System.out.println("Sorry, "+vehicle.getVehicleType().name()+"'s parking is not available");
            return null;
        }else{
            Slot slotToBeAllocated = availableSlots.get(0);
            Booking booking = new Booking(UUID.randomUUID(), vehicle, LocalDateTime.now(), slotToBeAllocated);
            slotToBeAllocated.setBooking(booking);
            System.out.println("Your slot is booked with bookingId:"+booking);
            return booking;
        }
    }

    public void release(Booking booking){
        if (booking == null || !booking.isValid()) {
            System.out.println("Please submit a valid booking");
            return;
        }

        VehicleType vehicleType = booking.getVehicle().getVehicleType();
        List<Slot> allSlots = slotTypeToSlotMap.get(vehicleType);
        Slot bookedSlot = allSlots.stream().filter(slot -> slot.getBooking().equals(booking)).findFirst().orElse(null);
        if(bookedSlot == null){
            System.out.println("Invalid Booking");
            return;
        }

        LocalDateTime endTime = LocalDateTime.now();
        long totalHours = ChronoUnit.HOURS.between(booking.getStartTime(), endTime);
        long totalMinutes = ChronoUnit.MINUTES.between(booking.getStartTime(),endTime) - (totalHours*60);
        ParkingFare parkingFare = slotTypeToParkingFareMap.get(vehicleType);
        float fare = parkingFare.getFirstOneHourFare() + (totalHours!=0 ? ((totalHours - 1) * parkingFare.getNextEachHourFare()) : 0)
                + (totalMinutes!=0 ? (totalMinutes * parkingFare.getNextEachHourFare() / 60): 0);
        System.out.println("Parking fare for "+vehicleType.name()+" is "+fare);
        PaymentDetail paymentDetail = paymentService.pay(fare, booking);
        if(paymentDetail !=null){
            booking.setPaymentDetail(paymentDetail);
            bookedSlot.setBooking(null);//freed up the slot
            System.out.println("Your vehicle is released. Hope you liked the service. Visit us again");
        }

    }

    public static void main(String[] args) {
        LocalDateTime x = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.println(x);

        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusHours(2).plusMinutes(33);
        long hours = ChronoUnit.HOURS.between(start, end);
        long minutes = ChronoUnit.MINUTES.between(start, end);
        System.out.println(minutes);
        minutes -= hours*60;
        System.out.println(hours+" hours");
        System.out.println(minutes+ " minutes");


    }

}

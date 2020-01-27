package com.nmd.service;

import com.nmd.enums.VehicleType;
import com.nmd.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ParkingService {
    private Map<VehicleType, List<Slot>> vehicleTypeToSlotsMap;
    private Map<VehicleType, ParkingFare> vehicleTypeParkingFareMap;
    private Map<UUID, ParkingLot> parkingLotMap;

    @Autowired
    PaymentService paymentService;

    public ParkingService() {
        vehicleTypeToSlotsMap = new HashMap<>();
        parkingLotMap = new HashMap<>();
        setParkingLotDetail(onboardParkingLot()); //todo: remove
        paymentService = new PaymentService();
    }

    public void setParkingLotDetail(ParkingLotDetail parkingLotDetail) {
        vehicleTypeParkingFareMap = parkingLotDetail.getParkingFares().stream().collect(Collectors.toMap(ParkingFare::getVehicleType, pf -> pf));
        ParkingLot parkingLot = new ParkingLot(parkingLotDetail.getParkingLotId());
        Map<VehicleType, Integer> vehicleTypeToSlotCountMap = parkingLotDetail.getVehicleTypeToSlotCountMap();
        int count=0;
        for (VehicleType vehicleType : vehicleTypeToSlotCountMap.keySet()) {
            int noOfSlots = count + vehicleTypeToSlotCountMap.get(vehicleType);
            List<Slot> slotList = IntStream.range(count, noOfSlots).boxed().map(id-> new Slot(id, vehicleType, parkingLot)).collect(Collectors.toList());
            count += noOfSlots;
            this.vehicleTypeToSlotsMap.put(vehicleType, slotList);
            parkingLot.getSlots().addAll(slotList);
        }
        parkingLotMap.put(parkingLot.getId(), parkingLot);
    }


    public void showLayout(UUID id){
        ParkingLot parkingLot = parkingLotMap.get(id);
        if(parkingLot==null || parkingLot.isNotValid()){
            System.out.println("No parkingLot found with id: "+id);
            return;
        }
        List<Slot> slots = parkingLot.getSlots();
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

    public Layout layout(UUID id){
        ParkingLot parkingLot = parkingLotMap.get(id);
        if(parkingLot==null || parkingLot.isNotValid()){
            System.out.println("No parkingLot found with id: "+id);
            return null;
        }

        Layout layout = new Layout();
        layout.setParkingLotId(parkingLot.getId());
        Map<VehicleType, SlotLayout> vehicleTypeSlotLayoutMap = parkingLot.getSlots().stream().map(m -> {
            return new SlotLayout(m.getId(), m.getVehicleType(), m.isBooked());
        }).collect(Collectors.toMap(SlotLayout::getVehicleType, slotLayout -> slotLayout));
        layout.setVehicleTypeSlotLayoutMap(vehicleTypeSlotLayoutMap);

        return layout;

    }
    public Booking allot(Vehicle vehicle){
        List<Slot> totalSlots = vehicleTypeToSlotsMap.get(vehicle.getVehicleType());
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
        List<Slot> allSlots = vehicleTypeToSlotsMap.get(vehicleType);
        Slot bookedSlot = allSlots.stream().filter(slot -> slot.getBooking().equals(booking)).findFirst().orElse(null);
        if(bookedSlot == null){
            System.out.println("Invalid Booking");
            return;
        }

        LocalDateTime endTime = LocalDateTime.now();
        long totalHours = ChronoUnit.HOURS.between(booking.getStartTime(), endTime);
        long totalMinutes = ChronoUnit.MINUTES.between(booking.getStartTime(),endTime) - (totalHours*60);
        ParkingFare parkingFare = vehicleTypeParkingFareMap.get(vehicleType);
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

    public ParkingLotDetail onboardParkingLot() {
        ParkingFare carParkingFare = new ParkingFare(VehicleType.CAR, 1f,2f);
        ParkingFare scooterParkingFare = new ParkingFare(VehicleType.SCOOTER, 3f,4f);
        ParkingFare busParkingFare = new ParkingFare(VehicleType.BUS, 5f,6f);
        Map<VehicleType, Integer> vehicleTypeToSlotCountMap = new HashMap<>();
        vehicleTypeToSlotCountMap.put(VehicleType.CAR, 1);
        vehicleTypeToSlotCountMap.put(VehicleType.SCOOTER,1);
        vehicleTypeToSlotCountMap.put(VehicleType.BUS,1);

        ParkingLotDetail parkingLotDetail = new ParkingLotDetail();
        parkingLotDetail.setParkingLotId(UUID.randomUUID());
        parkingLotDetail.setVehicleTypeToSlotCountMap(vehicleTypeToSlotCountMap);
        parkingLotDetail.setParkingFares(Arrays.asList(carParkingFare,scooterParkingFare, busParkingFare));

        return parkingLotDetail;
    }
}

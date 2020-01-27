package com.nmd.controller;

import com.nmd.enums.VehicleType;
import com.nmd.model.Booking;
import com.nmd.model.Layout;
import com.nmd.model.Vehicle;
import com.nmd.service.ParkingService;
import com.nmd.util.ParkingLotUtil;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingService parkingService;

    @GetMapping("/{id}/layout")
    public String show(@PathVariable("id") String id, Model model) {
        Validate.notNull(id);
        Layout layout = parkingService.layout(UUID.fromString(id));
        if (layout == null) {
            model.addAttribute("message", "No parking lot found with the given id: " + id);
            return "message";
        }
        model.addAttribute("layout", layout);
        return "slot_layout";
    }

    @PostMapping("/{id}/allot")
    public String allot(@RequestParam List<Vehicle> vehicleList, Model model) {
        Validate.notNull(vehicleList);
        Validate.notEmpty(vehicleList);
        Map<Boolean, List<Booking>> failedAndSuccessfulBookings = vehicleList.stream().map(v -> {
            Booking booking = parkingService.allot(v);
            return booking;
        }).collect(Collectors.groupingBy(Objects::isNull));
        List<Booking> failedBookings = failedAndSuccessfulBookings.get(Boolean.TRUE);
        List<Booking> successfulBookings = failedAndSuccessfulBookings.get(Boolean.FALSE);
        model.addAttribute("successfulBookings", successfulBookings);
        model.addAttribute("failedBookings", failedBookings);
        return "bookings";
    }

    @GetMapping("/parking-form")
    public String parkingForm(Model model){
        Set<VehicleType> availableVehicleTypes = ParkingLotUtil.availableVehicleTypes(Collections.emptySet());
        model.addAttribute("vehicleTypes", availableVehicleTypes);
        return "/parking-form";
    }
}

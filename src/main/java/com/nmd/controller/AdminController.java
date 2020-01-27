package com.nmd.controller;

import com.nmd.enums.VehicleType;
import com.nmd.model.ParkingLotDetail;
import com.nmd.service.ParkingService;
import com.nmd.util.ParkingLotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ParkingService parkingService;

    @GetMapping("/enroll")
    public String enroll(Model model){
        Set<VehicleType> availableVehicleTypes = ParkingLotUtil.availableVehicleTypes(Collections.emptySet());
        model.addAttribute("vehicleTypes", availableVehicleTypes);
        return "parking_lot_details";
    }

    @PostMapping("/onboard")
    public String onboard(ParkingLotDetail parkingLotDetail, Model model){
        parkingLotDetail = parkingService.onboardParkingLot();//todo: temporary
        parkingService.setParkingLotDetail(parkingLotDetail);
        model.addAttribute("message", (parkingLotDetail.isValid()?"Onboarded":"Failed to onboard") + " parking lot");
        return "message";
    }

    //todo: New API to edit the existing parking lot


    @GetMapping
    public String test(Model model){
        model.addAttribute("message","Admin is running");
        return "message";
    }

}

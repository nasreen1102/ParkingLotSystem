package com.nmd.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ParkingLot {
    private UUID id;
    private List<Slot> slots;

    public ParkingLot(UUID id) {
        this.id = id;
        this.slots = new LinkedList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public boolean isValid() {
        return id!=null && slots != null;
    }

    public boolean isNotValid() {
        return !isValid();
    }
}

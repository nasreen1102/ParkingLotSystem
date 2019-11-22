package com.nmd.model;

import java.util.List;
import java.util.UUID;

public class ParkingLot {
    private UUID id;
    private List<Slot> slots;

    public ParkingLot(UUID id) {
        this.id = id;
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

}

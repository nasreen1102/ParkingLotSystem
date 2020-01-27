package com.nmd.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {
    private UUID id;
    private Vehicle vehicle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Slot slot;
    private PaymentDetail paymentDetail;

    public Booking(UUID id, Vehicle vehicle, LocalDateTime startTime, Slot slot) {
        this.id = id;
        this.vehicle = vehicle;
        this.startTime = startTime;
        this.slot = slot;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", slot=" + slot +
                '}';
    }

    public boolean isValid() {
        return id != null && vehicle.isValid() && startTime != null && slot.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return getId().equals(booking.getId()) &&
                getVehicle().equals(booking.getVehicle()) &&
                getStartTime().equals(booking.getStartTime()) &&
                getSlot().equals(booking.getSlot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVehicle(), getStartTime(), getSlot());
    }
}

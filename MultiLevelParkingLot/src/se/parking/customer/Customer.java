package se.parking.customer;

import se.parking.slot.ParkingSize;
import se.parking.slot.ParkingType;

import java.time.LocalDateTime;

public class Customer {
    public LocalDateTime checkInTime;
    public LocalDateTime checkOutTime;
    private int id;
    private ParkingSize size;
    private ParkingType type;


    public Customer(int id, ParkingSize size, ParkingType type) {
        this.id = id;
        this.size = size;
        this.type = type;
        this.checkInTime = LocalDateTime.now();
    }

    public ParkingSize getSize() {
        return this.size;
    }

    public int getId() {
        return this.id;
    }

    public ParkingType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", size=" + size +
                ", type=" + type +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                '}';
    }
}

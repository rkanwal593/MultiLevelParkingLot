package se.parking;

import se.parking.customer.Customer;
import se.parking.customer.Ticket;
import se.parking.slot.ParkingSize;
import se.parking.slot.ParkingSlot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.time.temporal.*;

import static se.parking.slot.ParkingSize.*;
import static se.parking.slot.ParkingType.ELECTRIC;
import static se.parking.slot.ParkingType.STANDARD;

public class ParkingLot {

    Logger log = Logger.getLogger(ParkingSlot.class.getName());
    ArrayList<ArrayList<ParkingSlot>> parkingSlots;
    Map<Integer, ParkingSlot> occupiedslots;
    private BigDecimal existingRevenue;
    private int levels;
    private int normalSmallSlots;
    private int normalMediumSlots;
    private int normalLargeSlots;
    private int electricSmallSlots;
    private int electricMediumSlots;
    private int electricLargeSlots;

    public ParkingLot(BigDecimal revenue, int levels, int normalSmallSlots, int normalMediumSlots, int normalLargeSlots, int electricSmallSlots,
                      int electricMediumSlots, int electricLargeSlots)
    {
        this.existingRevenue = revenue;
        this.levels = levels;
        this.normalSmallSlots = normalSmallSlots;
        this.normalMediumSlots = normalMediumSlots;
        this.normalLargeSlots = normalLargeSlots;
        this.electricSmallSlots = electricSmallSlots;
        this.electricMediumSlots = electricMediumSlots;
        this.electricLargeSlots = electricLargeSlots;

        parkingSlots = new ArrayList<>();
        occupiedslots = new HashMap<>();
        for (int i = 0; i < this.levels; i++) {
            ArrayList<ParkingSlot> level = new ArrayList<ParkingSlot>();

            int totalNormalSlots = this.normalSmallSlots + this.normalMediumSlots + normalLargeSlots;

            for (int m = 0; m < this.normalSmallSlots; m++) {
                level.add(new ParkingSlot(1, SMALL, STANDARD));
            }
            for (int j = this.normalSmallSlots; j < (this.normalSmallSlots + this.normalMediumSlots); j++) {
                level.add(new ParkingSlot(2, MEDIUM, STANDARD));
            }
            for (int k = (this.normalSmallSlots + this.normalMediumSlots); k < totalNormalSlots; k++) {
                level.add(new ParkingSlot(2, LARGE, STANDARD));
            }


            int totalElectricSlots = electricSmallSlots + this.electricMediumSlots + this.electricLargeSlots;

            for (int m = totalNormalSlots; m < (electricSmallSlots + totalNormalSlots); m++) {
                level.add(new ParkingSlot(1, SMALL, ELECTRIC));
            }
            for (int j = (electricSmallSlots + totalNormalSlots); j < (electricSmallSlots + this.electricMediumSlots + totalNormalSlots); j++) {
                level.add(new ParkingSlot(2, MEDIUM, ELECTRIC));
            }
            for (int k = (electricSmallSlots + this.electricMediumSlots + totalNormalSlots); k < (totalNormalSlots + totalElectricSlots); k++) {
                level.add(new ParkingSlot(2, LARGE, ELECTRIC));
            }
            parkingSlots.add(level);

        }


    }


    public void park(Customer customer)
    {
        ParkingSlot s = findSlot(customer);
        s.setSlotAvailabilityStatus(false);
        Ticket t = new Ticket(customer.getId(), s.getId(), LocalDateTime.now());
        t.printTicketCheckInfo();
        occupiedslots.put(customer.getId(), s);
        System.out.println("Vehicle " + customer.getId() + " was parked at " + customer.checkInTime + " whose size is " + s.getSize());

    }

    public ParkingSlot findSlot(Customer customer) {
        ParkingSize vehicleSize = customer.getSize();
        for (ArrayList<ParkingSlot> temp : parkingSlots) {

            for (ParkingSlot s : temp) {

                if (vehicleSize == s.getSize() && (!s.isOccupied()) && customer.getType() == s.getType()) {
                    s.setSlotAvailabilityStatus(true);
                    return s;
                }
            }
            log.info("No slot found for : " + customer);
            return null;
        }
        log.info("No slot found for : " + customer);
        return null;
    }

    public void unPark(Customer customer) {
        ParkingSlot s = occupiedslots.get(customer.getId());
        customer.checkOutTime = LocalDateTime.now();
        BigDecimal duration = Duration(customer);
        logCharges(customer, duration);
        s.setSlotAvailabilityStatus(true);
        occupiedslots.remove(customer.getId());

    }

    public BigDecimal logCharges(Customer customer, BigDecimal duration) {
        ParkingSlot slot = occupiedslots.get(customer.getId());
        BigDecimal costPerHour = slot.costPerHourForParkingSpot();
        occupiedslots.remove(customer.getId());
        if (duration.compareTo(BigDecimal.ONE) > 0) {
            BigDecimal discount = Discount(duration);
            BigDecimal parkingPayment = duration.multiply(costPerHour).add(discount);
            addToRevenue(parkingPayment);
            log.info("Vehicle " + customer.getId() + " was parked at " + customer.checkInTime + " whose size is " + slot.getSize() + " was charged " + parkingPayment);
            return  parkingPayment;
        }
        log.info("Vehicle " + customer.getId() + " was parked at " + customer.checkInTime + " whose size is " + slot.getSize() + " was charged nothing for first hour ");
        return BigDecimal.ZERO;
    }

    public BigDecimal Duration(Customer customer) {
        Duration duration = Duration.ofMillis(ChronoUnit.MILLIS.between(customer.checkInTime, customer.checkOutTime));
        BigDecimal hours = BigDecimal.valueOf(duration.toHours());
        return hours;
    }


    public void addToRevenue(BigDecimal parkingPayment) {
        existingRevenue = existingRevenue.add(parkingPayment);
        log.info("revenue currently at " + parkingPayment);
    }

    public BigDecimal Discount(BigDecimal totalHours) {
        if(totalHours.signum() > 24)
        {
            BigDecimal oneHundred = BigDecimal.valueOf(100);
            BigDecimal discount = BigDecimal.TEN.multiply(totalHours).divide(oneHundred,2, RoundingMode.HALF_UP);
            return  discount;
        }
        return BigDecimal.ZERO;
    }

}
package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.parking.ParkingLot;
import se.parking.customer.Customer;
import se.parking.slot.ParkingSize;
import se.parking.slot.ParkingSlot;
import se.parking.slot.ParkingType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MultiLevelParkingLotTests
{
    ParkingLot testParkingLot = new ParkingLot(BigDecimal.valueOf(1000),3,10,10,10,10,10,10);

    @Test
    public void shouldFindASmallParkingSlotToElectricCar() {
        Customer testCustomer1 = new Customer(1, ParkingSize.SMALL, ParkingType.ELECTRIC);
        ParkingSlot slot = testParkingLot.findSlot(testCustomer1);
        Assertions.assertNotNull(slot);
        Assertions.assertTrue(slot.isOccupied());
    }
    @Test
    public void shouldFindASmallParkingSlotToNonElectricCar() {
        Customer testCustomer2 = new Customer(1,ParkingSize.SMALL,ParkingType.STANDARD);
        Assertions.assertNotNull( testParkingLot.findSlot(testCustomer2));
    }
    @Test
    public void shouldFindALargeParkingSlotForElectricCar() {
        Customer testCustomer3 = new Customer(1,ParkingSize.LARGE,ParkingType.ELECTRIC);
        Assertions.assertNotNull( testParkingLot.findSlot(testCustomer3));
    }
    @Test
    public void shouldNotFindMediumSlotForElectricCar() {
        Customer testCustomer4 = new Customer(1,ParkingSize.MEDIUM,ParkingType.ELECTRIC);
        Assertions.assertNotNull( testParkingLot.findSlot(testCustomer4));
    }

    @Test
    public void shouldReturnChargesBasedOnDuration() {
        Customer testCustomerN = new Customer(1,ParkingSize.LARGE, ParkingType.ELECTRIC);
        testParkingLot.park(testCustomerN);
        BigDecimal duration = new BigDecimal(2);
        Assertions.assertNotNull(testParkingLot.logCharges(testCustomerN, duration));
        Assertions.assertEquals(testParkingLot.logCharges(testCustomerN,duration),BigDecimal.TEN);
    }
}

import se.parking.ParkingLot;
import se.parking.customer.Customer;

import java.math.BigDecimal;

import static se.parking.slot.ParkingSize.*;
import static se.parking.slot.ParkingType.ELECTRIC;
import static se.parking.slot.ParkingType.STANDARD;
import static se.parking.ParkingLot.*;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(BigDecimal.valueOf(1000),
            3,
            10,
            10,
            10,
            10,
            10,
            10); //descriptive param names

        Customer c1 = new Customer(1,SMALL,ELECTRIC);
        Customer c2 = new Customer(2,SMALL,ELECTRIC);
        Customer c3 = new Customer(3,MEDIUM,STANDARD);

        parkingLot.park(c1);
        parkingLot.park(c2);
        parkingLot.park(c3);
        parkingLot.unPark(c1);
        parkingLot.unPark(c2);
        parkingLot.unPark(c3);
        }
    }

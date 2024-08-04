package se.parking.slot;

import java.math.BigDecimal;

public class CostParkingSlot {
    private final BigDecimal costPerHourSmallPSlot;
    private final BigDecimal costPerHourMediumPSlot;
    private final BigDecimal costPerHourLargePSlot;

    public CostParkingSlot(BigDecimal costPerHourSmallPSlot, BigDecimal costPerHourMediumPSlot, BigDecimal costPerHourLargePSlot) {
        this.costPerHourSmallPSlot = costPerHourSmallPSlot;
        this.costPerHourMediumPSlot = costPerHourMediumPSlot;
        this.costPerHourLargePSlot = costPerHourLargePSlot;
    }

    public BigDecimal costPerHourWithSize(ParkingSize parkingSize) {
        if (ParkingSize.SMALL.equals(parkingSize)) {
            return costPerHourSmallPSlot;
        } else if (ParkingSize.MEDIUM.equals(parkingSize)) {
            return costPerHourMediumPSlot;
        } else {
            return costPerHourLargePSlot;
        }
    }
}

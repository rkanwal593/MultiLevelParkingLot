package se.parking.slot;

import java.math.BigDecimal;

public class ParkingSlot {
    private final CostParkingSlot costNormalVehiclePSlot = new CostParkingSlot(BigDecimal.valueOf(2.50), BigDecimal.valueOf(3.00), BigDecimal.valueOf(4.00));
    private final CostParkingSlot costElectricVehiclePSlot = new CostParkingSlot(BigDecimal.valueOf(1.50), BigDecimal.valueOf(2.00), BigDecimal.valueOf(3.00));
    private int id;
    private ParkingSize size;
    private ParkingType type;
    private boolean occupied;

    public ParkingSlot(int id, ParkingSize size, ParkingType type) {
        this.id = id;
        this.size = size;
        this.type = type;
        this.occupied = false;
    }

    public ParkingSize getSize() {
        return this.size;
    }

    public int getId() {
        return this.id;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public void setSlotAvailabilityStatus(boolean status) {
        this.occupied = status;
    }

    public BigDecimal costPerHourForParkingSpot() {
        if (ParkingType.ELECTRIC.equals(this.type)) {
            return costElectricVehiclePSlot.costPerHourWithSize(this.size);
        } else  {
            return costNormalVehiclePSlot.costPerHourWithSize(this.size);
        }
    }

    public ParkingType getType() {
        return this.type;
    }

}

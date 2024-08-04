package se.parking.customer;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Ticket {
    public LocalDateTime checkInTime;
    Logger log = Logger.getLogger(Ticket.class.getName());
    private int customerId;
    private int slotId;

    public Ticket(int customerId, int slotId, LocalDateTime checkInTime) {
        this.customerId = customerId;
        this.slotId = slotId;
        this.checkInTime = checkInTime;
    }

    public void printTicketCheckInfo() {
        log.info("Car parked # :" + this.customerId  + "at CheckInTime # :" + this.checkInTime);
    }

}

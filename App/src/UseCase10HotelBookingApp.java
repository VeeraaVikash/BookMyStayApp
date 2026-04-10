import java.time.LocalDate;

/**
 * UseCase10HotelBookingApp - Booking Cancellation with Stack-Based Rollback
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase10HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Cancellation Service       ");
        System.out.println("   Use Case 10: Stack LIFO Rollback        ");
        System.out.println("============================================\n");

        RoomInventory       inventory  = new RoomInventory();
        BookingRequestQueue queue      = new BookingRequestQueue();
        BookingService      booking    = new BookingService(inventory);
        CancellationService cancel     = new CancellationService(inventory, booking);

        // Create and confirm several bookings
        Reservation[] requests = {
            new Reservation("Alice",   "SINGLE", LocalDate.of(2025,10,1), LocalDate.of(2025,10,5)),
            new Reservation("Bob",     "DOUBLE", LocalDate.of(2025,10,2), LocalDate.of(2025,10,6)),
            new Reservation("Charlie", "SUITE",  LocalDate.of(2025,10,3), LocalDate.of(2025,10,7)),
        };

        System.out.println("[1] Confirming bookings:");
        for (Reservation r : requests) {
            queue.enqueue(r);
        }
        booking.processAll(queue);

        // Register all confirmed reservations with cancellation service
        for (Reservation r : requests) {
            if (r.getStatus() == Reservation.Status.CONFIRMED) {
                cancel.registerReservation(r);
            }
        }

        System.out.println("\n[2] Inventory after bookings:");
        inventory.displayInventory();

        System.out.println("\n[3] Cancelling Bob's reservation:");
        try {
            cancel.cancel(requests[1].getReservationId());
        } catch (InvalidBookingException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        System.out.println("\n[4] Inventory after cancellation (DOUBLE restored):");
        inventory.displayInventory();

        System.out.println("\n[5] Released room stack (LIFO order):");
        cancel.displayReleasedStack();

        System.out.println("\n[6] Attempting to cancel Bob's booking again (should fail):");
        try {
            cancel.cancel(requests[1].getReservationId());
        } catch (InvalidBookingException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        System.out.println("\n[7] Cancelling Charlie's reservation:");
        try {
            cancel.cancel(requests[2].getReservationId());
        } catch (InvalidBookingException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        System.out.println("\n[8] Released room stack after second cancellation:");
        cancel.displayReleasedStack();

        System.out.println("\n[Note] Stack captures LIFO release order.");
        System.out.println("[Note] Inventory restored correctly after each cancellation.");
    }
}

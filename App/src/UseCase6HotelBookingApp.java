import java.time.LocalDate;

/**
 * UseCase6HotelBookingApp - Room Allocation with Uniqueness Enforcement
 *
 * <p>Demonstrates the {@link BookingService} processing a full queue of
 * requests, assigning unique room IDs via a Set, and updating inventory
 * atomically after each confirmation.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase6HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Room Allocation            ");
        System.out.println("   Use Case 6: Set-Based Uniqueness        ");
        System.out.println("============================================\n");

        RoomInventory       inventory  = new RoomInventory();
        BookingRequestQueue queue      = new BookingRequestQueue();
        BookingService      booking    = new BookingService(inventory);

        System.out.println("[1] Guests submit requests:");
        queue.enqueue(new Reservation("Alice",   "SINGLE", LocalDate.of(2025,7,1), LocalDate.of(2025,7,4)));
        queue.enqueue(new Reservation("Bob",     "DOUBLE", LocalDate.of(2025,7,1), LocalDate.of(2025,7,5)));
        queue.enqueue(new Reservation("Charlie", "SUITE",  LocalDate.of(2025,7,2), LocalDate.of(2025,7,6)));
        queue.enqueue(new Reservation("Diana",   "SUITE",  LocalDate.of(2025,7,2), LocalDate.of(2025,7,7)));
        queue.enqueue(new Reservation("Eve",     "SUITE",  LocalDate.of(2025,7,3), LocalDate.of(2025,7,8)));

        System.out.println("\n[2] Processing all queued requests:");
        booking.processAll(queue);

        System.out.println("\n[3] Final inventory state:");
        inventory.displayInventory();

        System.out.println("\n[4] Allocated room IDs by type:");
        booking.displayAllocations();

        System.out.println("\n[Note] Set prevents duplicate room IDs automatically.");
        System.out.println("[Note] Inventory updated atomically with each allocation.");
    }
}

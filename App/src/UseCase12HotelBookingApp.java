import java.time.LocalDate;

/**
 * UseCase12HotelBookingApp - Data Persistence & System Recovery
 *
 * <p>Demonstrates saving inventory and booking history to disk, then
 * simulating an application restart by loading from those files into
 * fresh in-memory objects.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase12HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Data Persistence           ");
        System.out.println("   Use Case 12: Save & Restore             ");
        System.out.println("============================================\n");

        PersistenceService persistence = new PersistenceService();

        // -----------------------------------------------------------------
        // PHASE 1: Run the system and build some state
        // -----------------------------------------------------------------
        System.out.println("=== PHASE 1: System Running ===\n");

        RoomInventory       inventory  = new RoomInventory();
        BookingRequestQueue queue      = new BookingRequestQueue();
        BookingService      booking    = new BookingService(inventory);
        BookingHistory      history    = new BookingHistory();

        Reservation[] requests = {
            new Reservation("Alice",   "SINGLE", LocalDate.of(2025,12,1), LocalDate.of(2025,12,4)),
            new Reservation("Bob",     "DOUBLE", LocalDate.of(2025,12,2), LocalDate.of(2025,12,6)),
            new Reservation("Charlie", "SUITE",  LocalDate.of(2025,12,3), LocalDate.of(2025,12,7)),
        };

        for (Reservation r : requests) queue.enqueue(r);
        booking.processAll(queue);
        for (Reservation r : requests) {
            if (r.getStatus() == Reservation.Status.CONFIRMED) history.addBooking(r);
        }

        System.out.println("\n[Pre-save] Inventory:");
        inventory.displayInventory();

        System.out.println("\n[Pre-save] History (" + history.getTotalBookings() + " records):");
        history.displayHistory();

        // -----------------------------------------------------------------
        // PHASE 2: Persist state before shutdown
        // -----------------------------------------------------------------
        System.out.println("=== PHASE 2: Shutdown — Persisting State ===\n");
        persistence.saveInventory(inventory);
        persistence.saveBookingHistory(history);

        // -----------------------------------------------------------------
        // PHASE 3: Simulate restart — restore from files into fresh objects
        // -----------------------------------------------------------------
        System.out.println("\n=== PHASE 3: System Restart — Restoring State ===\n");

        RoomInventory restoredInventory = new RoomInventory(); // starts with defaults
        BookingHistory restoredHistory  = new BookingHistory();

        persistence.restoreInventory(restoredInventory);
        persistence.restoreBookingHistory(restoredHistory);

        System.out.println("\n[Post-restore] Inventory:");
        restoredInventory.displayInventory();

        System.out.println("\n[Post-restore] History (" + restoredHistory.getTotalBookings() + " records):");
        restoredHistory.displayHistory();

        // -----------------------------------------------------------------
        // PHASE 4: Demonstrate graceful handling of missing files
        // -----------------------------------------------------------------
        System.out.println("=== PHASE 4: Graceful Handling of Missing Files ===\n");
        persistence.clearPersistedData();

        RoomInventory emptyInventory = new RoomInventory();
        BookingHistory emptyHistory  = new BookingHistory();
        persistence.restoreInventory(emptyInventory);
        persistence.restoreBookingHistory(emptyHistory);

        System.out.println("\n[Note] System handled missing files without crashing.");
        System.out.println("[Note] In production, this pattern extends to databases.");
    }
}

import java.time.LocalDate;

/**
 * UseCase11HotelBookingApp - Concurrent Booking with Synchronization
 *
 * <p>Spawns multiple threads that all attempt to book rooms simultaneously,
 * demonstrating that {@code synchronized} critical sections prevent double
 * allocation even under concurrent load.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase11HotelBookingApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Concurrent Booking         ");
        System.out.println("   Use Case 11: Thread Safety              ");
        System.out.println("============================================\n");

        // Only 2 SUITE rooms available but 5 guests will try to book one
        RoomInventory       inventory  = new RoomInventory();
        BookingRequestQueue queue      = new BookingRequestQueue();
        BookingService      booking    = new BookingService(inventory);
        BookingHistory      history    = new BookingHistory();

        System.out.println("[1] Five guests simultaneously request a SUITE:");
        String[] guests = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        for (String g : guests) {
            queue.enqueue(new Reservation(g, "SUITE",
                    LocalDate.of(2025,11,1), LocalDate.of(2025,11,4)));
        }

        ConcurrentBookingProcessor processor =
                new ConcurrentBookingProcessor(queue, inventory, booking, history);

        System.out.println("\n[2] Processing with " + guests.length + " concurrent threads:");
        processor.simulateConcurrentBooking(guests.length);

        System.out.println("\n[3] Final inventory (should show 0 SUITE rooms):");
        inventory.displayInventory();

        System.out.println("\n[4] Confirmed bookings (should be exactly 2 for SUITE):");
        history.displayHistory();

        System.out.println("[5] Allocated rooms (no duplicates):");
        booking.displayAllocations();

        System.out.println("[Note] Synchronized critical sections prevented double allocation.");
        System.out.println("[Note] Only " + inventory.getAllAvailability().get("SUITE")
                + " SUITE rooms were available; extra requests were correctly rejected.");
    }
}

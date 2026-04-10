import java.time.LocalDate;

/**
 * UseCase5HotelBookingApp - Booking Request Queue (FIFO)
 *
 * <p>Shows how a {@link BookingRequestQueue} captures incoming requests in
 * strict arrival order. No rooms are allocated in this use case — that is
 * introduced in Use Case 6.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase5HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Booking Request Queue      ");
        System.out.println("   Use Case 5: FIFO Queue                  ");
        System.out.println("============================================\n");

        BookingRequestQueue queue = new BookingRequestQueue();

        System.out.println("[1] Guests submit booking requests:");
        queue.enqueue(new Reservation("Alice",   "SINGLE", LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 5)));
        queue.enqueue(new Reservation("Bob",     "DOUBLE", LocalDate.of(2025, 7, 2), LocalDate.of(2025, 7, 6)));
        queue.enqueue(new Reservation("Charlie", "SUITE",  LocalDate.of(2025, 7, 3), LocalDate.of(2025, 7, 7)));
        queue.enqueue(new Reservation("Diana",   "SINGLE", LocalDate.of(2025, 7, 4), LocalDate.of(2025, 7, 8)));

        System.out.println("\n[2] Current queue state:");
        queue.displayQueue();

        System.out.println("\n[3] Dequeue first request (should be Alice - FIRST IN):");
        Reservation first = queue.dequeue();
        if (first != null) {
            System.out.println("  Dequeued: " + first.getGuestName() + " (" + first.getRoomType() + ")");
        }

        System.out.println("\n[4] Remaining queue (Bob should now be first):");
        queue.displayQueue();

        System.out.println("\n[Note] FIFO order is preserved automatically by Queue.");
        System.out.println("[Note] No room allocation or inventory change occurred here.");
    }
}

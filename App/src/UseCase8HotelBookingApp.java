import java.time.LocalDate;

/**
 * UseCase8HotelBookingApp - Booking History & Reporting
 *
 * <p>Demonstrates how confirmed reservations are stored in an ordered List
 * and analysed by a dedicated report service.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase8HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Booking History & Reports  ");
        System.out.println("   Use Case 8: List + Reporting            ");
        System.out.println("============================================\n");

        RoomInventory       inventory = new RoomInventory();
        BookingRequestQueue queue     = new BookingRequestQueue();
        BookingService      booking   = new BookingService(inventory);
        BookingHistory      history   = new BookingHistory();

        // Submit several requests
        Reservation[] requests = {
            new Reservation("Alice",   "SINGLE", LocalDate.of(2025,8,1), LocalDate.of(2025,8,4)),
            new Reservation("Bob",     "DOUBLE", LocalDate.of(2025,8,2), LocalDate.of(2025,8,7)),
            new Reservation("Charlie", "SUITE",  LocalDate.of(2025,8,3), LocalDate.of(2025,8,6)),
            new Reservation("Diana",   "SINGLE", LocalDate.of(2025,8,5), LocalDate.of(2025,8,8))
        };

        System.out.println("[1] Processing booking requests:");
        for (Reservation r : requests) {
            queue.enqueue(r);
        }
        booking.processAll(queue);

        // Record only CONFIRMED reservations in history
        for (Reservation r : requests) {
            if (r.getStatus() == Reservation.Status.CONFIRMED) {
                history.addBooking(r);
            }
        }

        System.out.println("\n[2] Booking History (insertion order preserved):");
        history.displayHistory();

        System.out.println("[3] Summary Report:");
        BookingReportService reporter = new BookingReportService(history);
        reporter.generateSummaryReport();

        System.out.println("\n[Note] Reporting reads history data — does not modify it.");
    }
}

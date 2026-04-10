import java.time.LocalDate;

/**
 * UseCase7HotelBookingApp - Optional Add-On Services (Map + List)
 *
 * <p>Demonstrates how optional services are composed with reservations without
 * touching booking or inventory logic.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase7HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Add-On Services            ");
        System.out.println("   Use Case 7: Map + List Composition      ");
        System.out.println("============================================\n");

        // Set up a confirmed reservation to attach services to
        RoomInventory       inventory = new RoomInventory();
        BookingRequestQueue queue     = new BookingRequestQueue();
        BookingService      booking   = new BookingService(inventory);

        Reservation res = new Reservation("Alice", "SUITE",
                LocalDate.of(2025,8,1), LocalDate.of(2025,8,5));
        queue.enqueue(res);
        booking.processAll(queue);

        System.out.println();

        // Add-on service catalogue
        AddOnService breakfast  = new AddOnService("Breakfast Buffet",  25.00);
        AddOnService spa        = new AddOnService("Spa Package",        80.00);
        AddOnService airportPU  = new AddOnService("Airport Pickup",     45.00);
        AddOnService laundry    = new AddOnService("Laundry Service",    15.00);

        AddOnServiceManager manager = new AddOnServiceManager();

        String resId = res.getReservationId();

        System.out.println("[1] Guest selects add-on services:");
        manager.addService(resId, breakfast);
        manager.addService(resId, spa);
        manager.addService(resId, airportPU);

        System.out.println("\n[2] Service summary for reservation:");
        manager.displayServices(resId);

        System.out.println("\n[3] Booking state was NOT changed by add-on selection.");
        System.out.println("  Reservation status : " + res.getStatus());
        System.out.println("  Inventory (SUITE)  : " + inventory.getAvailability("SUITE"));

        System.out.println("\n[Note] New service types can be added without changing BookingService.");
    }
}

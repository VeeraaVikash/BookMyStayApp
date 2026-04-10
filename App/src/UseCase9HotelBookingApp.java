import java.time.LocalDate;

/**
 * UseCase9HotelBookingApp - Validation & Error Handling
 *
 * <p>Exercises all validation rules and custom exceptions to show how the
 * system handles incorrect input gracefully without crashing.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase9HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Validation & Error Handling");
        System.out.println("   Use Case 9: Custom Exceptions           ");
        System.out.println("============================================\n");

        RoomInventory          inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);

        // Exhaust SUITE inventory before testing
        inventory.decrementAvailability("SUITE");
        inventory.decrementAvailability("SUITE");

        // Test cases: [valid, invalid room type, bad dates, no availability]
        Reservation[] tests = {
            new Reservation("Alice",  "SINGLE",  LocalDate.of(2025,9,1), LocalDate.of(2025,9,5)),
            new Reservation("Bob",    "PENTHOUSE",LocalDate.of(2025,9,1), LocalDate.of(2025,9,5)),
            new Reservation("Carol",  "DOUBLE",   LocalDate.of(2025,9,5), LocalDate.of(2025,9,1)),
            new Reservation("David",  "SUITE",    LocalDate.of(2025,9,1), LocalDate.of(2025,9,5)),
        };

        for (Reservation r : tests) {
            System.out.println("--- Validating: " + r.getGuestName()
                    + " | " + r.getRoomType() + " ---");
            try {
                validator.validate(r);
            } catch (InvalidBookingException e) {
                System.out.println("  [ERROR]  " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("[Note] All errors handled gracefully — system still running.");
        System.out.println("[Note] No inventory was corrupted by invalid requests.");
        inventory.displayInventory();
    }
}

/**
 * UseCase3HotelBookingApp - Centralized Inventory Management with HashMap
 *
 * <p>Demonstrates how replacing scattered availability variables with a
 * centralized {@link RoomInventory} backed by a HashMap solves state
 * management problems identified in Use Case 2.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase3HotelBookingApp {

    /**
     * Entry point for Use Case 3.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Centralized Inventory      ");
        System.out.println("   Use Case 3: HashMap Inventory           ");
        System.out.println("============================================\n");

        RoomInventory inventory = new RoomInventory();

        System.out.println("[1] Initial Inventory State:");
        inventory.displayInventory();

        System.out.println("\n[2] Simulating a SINGLE room booking...");
        inventory.decrementAvailability("SINGLE");
        inventory.displayInventory();

        System.out.println("\n[3] Simulating a SUITE room booking...");
        inventory.decrementAvailability("SUITE");
        inventory.displayInventory();

        System.out.println("\n[4] Simulating a DOUBLE room cancellation (restore)...");
        inventory.incrementAvailability("DOUBLE");
        inventory.displayInventory();

        System.out.println("\n[Note] All state lives in one HashMap — O(1) access,");
        System.out.println("[Note] single source of truth, zero scattered variables.");
    }
}

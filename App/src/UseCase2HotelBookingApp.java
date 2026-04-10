/**
 * UseCase2HotelBookingApp - Room Object Modeling with Inheritance
 *
 * <p>Introduces domain object design using abstract classes and inheritance.
 * Room availability is stored in simple variables to deliberately highlight
 * the limitations of scattered state management before centralized inventory
 * is introduced in Use Case 3.
 *
 * @author  BookMyStay Development Team
 * @version 1.0
 */
public class UseCase2HotelBookingApp {

    /**
     * Entry point for Use Case 2.
     *
     * <p>Creates room template objects and displays their details alongside
     * hardcoded availability variables, illustrating the problem of scattered state.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStay - Room Inventory Overview    ");
        System.out.println("   Use Case 2: Room Object Modeling        ");
        System.out.println("============================================\n");

        // Concrete room domain objects (polymorphic references)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom  = new SuiteRoom();

        // -----------------------------------------------------------------
        // Availability stored in SEPARATE VARIABLES — intentional limitation.
        // Notice: updating availability means touching multiple unrelated
        // variables scattered across the code. This is what UC3 will fix.
        // -----------------------------------------------------------------
        int singleRoomsAvailable = 5;
        int doubleRoomsAvailable = 3;
        int suiteRoomsAvailable  = 2;

        // Display each room type with its current availability
        displayRoomAvailability(singleRoom, singleRoomsAvailable);
        displayRoomAvailability(doubleRoom, doubleRoomsAvailable);
        displayRoomAvailability(suiteRoom,  suiteRoomsAvailable);

        System.out.println("\n[Note] Availability stored in scattered variables.");
        System.out.println("[Note] Use Case 3 will centralize this using HashMap.");
    }

    /**
     * Prints a room's details together with its current availability count.
     *
     * @param room      the room domain object
     * @param available number of rooms currently available
     */
    private static void displayRoomAvailability(Room room, int available) {
        System.out.println("--------------------------------------------");
        room.displayRoomInfo();
        System.out.println("  Available     : " + available + " room(s)");
        System.out.println();
    }
}

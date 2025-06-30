import java.util.*;
public class StadiumBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StadiumManager manager = new StadiumManager();

        System.out.println("Welcome to Stadium Booking System!");

        while (true) {
            System.out.println("\n1. Add Facility");
            System.out.println("2. View Facilities");
            System.out.println("3. Make Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    manager.addFacility(scanner);
                    break;
                case 2:
                    manager.viewFacilities();
                    break;
                case 3:
                    manager.makeBooking(scanner);
                    break;
                case 4:
                    manager.viewBookings();
                    break;
                case 5:
                    manager.cancelBooking(scanner);
                    break;
                case 6:
                    System.out.println("Exiting Stadium Booking System. Goodbye");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}





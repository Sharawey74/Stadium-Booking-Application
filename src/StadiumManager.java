import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StadiumManager {
    private final List<StadiumFacility> facilities = new ArrayList<>();
    // Add a facility (Seating Section or Conference Room)
    public void addFacility(Scanner scanner) {
        System.out.println("Choose Facility Type:");
        System.out.println("1. Seating Section");
        System.out.println("2. Conference Room");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter facility name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Invalid name. Please enter a valid name.");
            return;
        }
        System.out.print("Enter facility capacity: ");
        int capacity = scanner.nextInt();
        if (capacity <= 0) {
            System.out.println("Invalid capacity. Please enter a valid capacity.");
            return;
        }
        scanner.nextLine(); // Consume newline

        StadiumFacility facility;
        if (choice == 1) {
            System.out.print("Enter seat type (VIP/Regular): ");
            String seatType = scanner.nextLine();
            facility = new SeatingSection(name, capacity, seatType);
        } else if (choice == 2) {
            System.out.print("Is a projector available? (true/false): ");
            boolean projectorAvailable = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline
            facility = new ConferenceRoom(name, capacity, projectorAvailable);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        facilities.add(facility);
        System.out.println("Facility added successfully!");
    }

    // View all facilities
    public void viewFacilities() {
        if (facilities.isEmpty()) {
            System.out.println("No facilities available.");
        } else {
            for (StadiumFacility facility : facilities) {
                System.out.println(facility);
            }
        }
    }

    // Make a booking for a facility
    public void makeBooking(Scanner scanner) {
        System.out.print("Enter facility name: ");
        String facilityName = scanner.nextLine();

        System.out.print("Enter booking date (yyyy-MM-dd): ");
        String bookingDate = scanner.nextLine();
        if (!bookingDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        // Validate that the date is not in the past
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate enteredDate = java.time.LocalDate.parse(bookingDate);
        if (enteredDate.isBefore(today)) {
            System.out.println("Cannot book for a past date. Please select a valid date.");
            return;
        }

        System.out.print("Enter booking time (HH:mm): ");
        String bookingTime = scanner.nextLine();
        if (!bookingTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return;
        }

        for (StadiumFacility facility : facilities) {
            if (facility.getFacilityName().equalsIgnoreCase(facilityName)) {
                System.out.print("Enter number of units to book: ");
                int units = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (units <= 0) {
                    System.out.println("Invalid number of booked units");
                    return;
                }

                if (facility.checkAvailability(units)) {
                    facility.bookUnits(units);
                    if (Booking.isAvailable(facilityName, bookingDate, bookingTime)) {
                        Booking.makeBooking(facilityName, bookingDate, bookingTime); // Add booking with date and time
                        return;
                    } else {
                        System.out.println("Booking failed. Facility not available for the selected date and time.");
                    }
                } else {
                    System.out.println("Booking cannot proceed. Insufficient capacity.");
                }
                return;
            }
        }
        System.out.println("Facility not found.");
    }

    // Cancel a booking
    public void cancelBooking(Scanner scanner) {
        System.out.print("Enter facility name: ");
        String facilityName = scanner.nextLine();

        System.out.print("Enter booking date (yyyy-MM-dd): ");
        String bookingDate = scanner.nextLine();

        System.out.print("Enter booking time (HH:mm): ");
        String bookingTime = scanner.nextLine();

        boolean isCanceled = Booking.cancelBooking(facilityName, bookingDate, bookingTime);
        if (!isCanceled) {
            System.out.println("Booking not found.");
        }
    }

    // View all bookings
    public void viewBookings() {
        System.out.println("Bookings for all facilities:");
        Booking.viewBookings().forEach(System.out::println); // Displays all bookings with date and time
    }

    // GUI functionality for adding facilities
    public void addFacility(String name, int capacity, String seatType, boolean hasProjector) {
        StadiumFacility facility;
        if (seatType != null && !seatType.isEmpty()) {
            facility = new SeatingSection(name, capacity, seatType);
        } else {
            facility = new ConferenceRoom(name, capacity, hasProjector);
        }
        facilities.add(facility);
    }

    // Get all facilities as a list for GUI
    public List<StadiumFacility> getFacilitiesAsList() {
        return facilities;
    }

    // Get facilities as a formatted string for the GUI
    public String getFacilitiesAsString() {
        if (facilities.isEmpty()) {
            return "No facilities available.";
        }
        StringBuilder sb = new StringBuilder();
        for (StadiumFacility facility : facilities) {
            sb.append(facility).append("\n");
        }
        return sb.toString();
    }

    // Make a booking (GUI method)
    public boolean makeBooking(String facilityName, String bookingDate, String bookingTime) {
        for (StadiumFacility facility : facilities) {
            if (facility.getFacilityName().equalsIgnoreCase(facilityName)) {
                if (Booking.isAvailable(facilityName, bookingDate, bookingTime)) {
                    Booking.makeBooking(facilityName, bookingDate, bookingTime);

                    int bookedUnits = Booking.getBookedUnits(facilityName);
                    System.out.println("Booking successful! Total booked units for " + facilityName + ": " + bookedUnits);
                    return true;
                } else {
                    System.out.println("Booking failed. Facility not available for the selected date and time.");
                    return false;
                }
            }
        }
        System.out.println("Facility not found.");
        return false;
    }

    // Get all bookings as a formatted string for GUI
    public List<Booking> getBookingsAsString() {
        return Booking.bookings;
    }

    // Cancel a booking (GUI method)
    public boolean cancelBooking(String facilityName, String bookingDate, String bookingTime) {
        boolean success = Booking.cancelBooking(facilityName, bookingDate, bookingTime);
        if (success) {
            System.out.println("Booking canceled successfully for " + facilityName + " on " + bookingDate + " at " + bookingTime + "!");
        } else {
            System.out.println("Booking not found.");
        }
        return success;
    }

    // Inside the manager class
    public boolean cancelBookingById(String bookingID) {
        // Simulate finding the booking by ID and canceling it.
        // Here, I assume we have a list of bookings, and we're removing the one with the matching ID.

        for (Booking booking : Booking.bookings) {  // Use the static bookings list from Booking class
            if (booking.getClass().equals(bookingID)) {
                Booking.bookings.remove(booking);  // Remove the booking if IDs match
                return true;  // Return true if successfully canceled
            }
        }

        return false;  // Return false if no matching booking ID found
    }
}

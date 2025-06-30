import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private static final int TOTAL_UNITS = 100; // Example total units, adjust as needed
    private final StringProperty facilityName;
    private final ObjectProperty<LocalDate> bookingDate; // Use LocalDate for better handling
    private final StringProperty bookingTime; // Format: HH:mm

    // Constructor for GUI
    public Booking(String facilityName, LocalDate bookingDate, String bookingTime) {
        this.facilityName = new SimpleStringProperty(facilityName);
        this.bookingDate = new SimpleObjectProperty<>(bookingDate);
        this.bookingTime = new SimpleStringProperty(bookingTime);
    }

    // Constructor for console-based input
    public Booking(String facilityName, String bookingDate, String bookingTime) {
        this.facilityName = new SimpleStringProperty(facilityName);
        this.bookingDate = new SimpleObjectProperty<>(LocalDate.parse(bookingDate, DateTimeFormatter.ISO_DATE));
        this.bookingTime = new SimpleStringProperty(bookingTime);
    }

    // Static list to store all bookings
    public static List<Booking> bookings = new ArrayList<>();

    // Method to make a booking
    public static void makeBooking(String facilityName, String bookingDate, String bookingTime) {
        // Validate date format
        if (!bookingDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }

        // Validate time format
        if (!bookingTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            throw new IllegalArgumentException("Invalid time format. Use HH:mm.");
        }

        // Validate that the date is not in the past
        LocalDate today = LocalDate.now();
        LocalDate enteredDate = LocalDate.parse(bookingDate, DateTimeFormatter.ISO_DATE);
        if (enteredDate.isBefore(today)) {
            throw new IllegalArgumentException("Cannot book for a past date. Please select a valid date.");
        }

        // Add the booking to the list
        bookings.add(new Booking(facilityName, bookingDate, bookingTime));
        System.out.println("Booking made successfully for " + facilityName + " on "
                + bookingDate + " at " + bookingTime + "!");
    }

    // Method to view all bookings (returns a list of formatted booking strings)
    public static List<String> viewBookings() {
        List<String> bookingStrings = new ArrayList<>();
        if (bookings.isEmpty()) {
            bookingStrings.add("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                bookingStrings.add(booking.toString());
            }
        }
        return bookingStrings;
    }

    // Method to cancel a booking
    public static boolean cancelBooking(String facilityName, String bookingDate, String bookingTime) {
        boolean isRemoved = bookings.removeIf(b ->
                b.getFacilityName().equalsIgnoreCase(facilityName) &&
                        b.getBookingDate().equals(LocalDate.parse(bookingDate, DateTimeFormatter.ISO_DATE)) &&
                        b.getBookingTime().equalsIgnoreCase(bookingTime)
        );

        if (isRemoved) {
            System.out.println("Booking canceled successfully for " + facilityName + " on "
                    + bookingDate + " at " + bookingTime + "!");
        } else {
            System.out.println("Booking not found.");
        }
        return isRemoved;
    }

    // Method to check if a facility is available for a specific date and time
    public static boolean isAvailable(String facilityName, String bookingDate, String bookingTime) {
        return bookings.stream().noneMatch(b ->
                b.getFacilityName().equalsIgnoreCase(facilityName) &&
                        b.getBookingDate().equals(LocalDate.parse(bookingDate, DateTimeFormatter.ISO_DATE)) &&
                        b.getBookingTime().equalsIgnoreCase(bookingTime)
        );
    }

    // Method to get the number of booked units for a facility
    public static int getBookedUnits(String facilityName) {
        return (int) bookings.stream()
                .filter(b -> b.getFacilityName().equalsIgnoreCase(facilityName))
                .count();
    }

    // Method to get the number of available units for a facility
    public static int getAvailableUnits(String facilityName) {
        return TOTAL_UNITS - getBookedUnits(facilityName);
    }

    // Getters and properties for GUI binding
    public String getFacilityName() {
        return facilityName.get();
    }

    public StringProperty facilityNameProperty() {
        return facilityName;
    }

    public LocalDate getBookingDate() {
        return bookingDate.get();
    }

    public ObjectProperty<LocalDate> bookingDateProperty() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime.get();
    }

    public StringProperty bookingTimeProperty() {
        return bookingTime;
    }

    public ObservableValue<LocalDate> dateProperty() {
        return bookingDate;
    }

    public LocalDate getDate() {
        return bookingDate.get();
    }

    public String getTime() {
        return bookingTime.get();
    }

    @Override
    public String toString() {
        return facilityName.get() + " - " + bookingDate.get().format(DateTimeFormatter.ISO_DATE) + " - ";
    }
}
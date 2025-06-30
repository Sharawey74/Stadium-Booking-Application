public class SeatingSection extends StadiumFacility {
    private static final String type =null ;
    private final String seatType; // VIP or Regular

    public SeatingSection(String facilityName, int capacity, String seatType) {
        super(facilityName, capacity);
        this.seatType = seatType;
    }

    public String getSeatType() {
        return seatType;
    }

    @Override
    public String toString() {
        return "Seating Section - Facility Name: " + getFacilityName() +
                ", Capacity: " + getCapacity() +
                ", Current Bookings: " + getCurrentBookings() +
                ", Seat Type: " + seatType +
                ", Available: " + (checkAvailability(1) ? "Yes" : "No");
    }
}
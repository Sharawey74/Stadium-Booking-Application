public class StadiumFacility {
    private final String facilityName;
    private final int capacity;
    public String name;
    public String type;
    private int currentBookings; // Tracks how many units are booked

    public StadiumFacility(String facilityName, int capacity) {

        this.facilityName = facilityName;
        this.capacity = capacity;
        this.currentBookings = 0; // Initially, no units are booked
    }

    public String getFacilityName() {
        return facilityName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentBookings() {
        return currentBookings;
    }

    public void bookUnits(int units) {
        if (currentBookings + units <= capacity) {
            currentBookings += units;
        } else

        {
            throw new IllegalArgumentException("Booking exceeds facility capacity!");
        }
    }

    public boolean checkAvailability(int requestedUnits) {
        return currentBookings + requestedUnits <= capacity;
    }

    @Override
    public String toString() {
        return "Facility Name: " + facilityName + ", Capacity: " + capacity +
                ", Current Bookings: " + currentBookings;
    }

    public String getName() {

        return null;
    }
}
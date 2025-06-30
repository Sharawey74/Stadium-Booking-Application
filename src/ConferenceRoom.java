public class ConferenceRoom extends StadiumFacility {
    private final boolean hasProjector;
    public ConferenceRoom(String facilityName, int capacity, boolean hasProjector) {
        super(facilityName, capacity);
        this.hasProjector = hasProjector;
    }
    public boolean isHasProjector() {
        return hasProjector;
    }
    @Override
    public String toString() {
        return "Conference Room - Facility Name: " + getFacilityName() +
                ", Capacity: " + getCapacity() +
                ", Current Bookings: " + getCurrentBookings() +
                ", Has Projector: " + (hasProjector ? "Yes" : "No") +
                ", Available: " + (checkAvailability(1) ? "Yes" : "No");
    }
}
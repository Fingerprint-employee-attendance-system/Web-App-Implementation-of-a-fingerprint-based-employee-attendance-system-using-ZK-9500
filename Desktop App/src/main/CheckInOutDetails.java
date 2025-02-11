package main;


public  class CheckInOutDetails {
    private final String day;
    private final String checkIn;
    private final String checkOut;

    public CheckInOutDetails(String day, String checkIn, String checkOut) {
        this.day = day;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getDay() {
        return day;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
}

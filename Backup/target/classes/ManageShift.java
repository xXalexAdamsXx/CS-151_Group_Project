import java.util.Date;

public class ManageShift {
    String firstName;
    String lastName;
    Date clockedIn;
    Date clockedOut;

    public ManageShift(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        clockedIn = new Date();
    }
    /**
     * Clocks out Cashier
     */
    public void clockOut() {
        if (clockedOut != null) {return;}
        clockedOut = new Date();
    }
    /**
     * Gets the First Name of Cashier
     * @return First Name of Cashier
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Gets the Last Name of Cashier
     * @return Last Name of Cashier
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Gives a String to show when Cashier clocked in
     * @return String of Date Cashier clocked in
     */
    public String getClockedInTime() {
        return "Clocked In At:" + clockedIn.toString();
    }
    /**
     * Gets a date object with information of clocked in time
     * @return Date object at clock in time
     */
    public Date getClockedInDate() {
        return (Date)clockedIn.clone();
    }
    /**
     * Gives a String to show when Cashier clocked out
     * @return String of Date Cashier clocked out
     */
    public String getClockedOutTime() {
        if (clockedOut == null) { return "Has Not Clocked Out!";}
        return "Clocked In At:" + clockedOut.toString();
    }
    /**
     * Gets a date object with information of clocked out time
     * @return Date object at clock out time
     */
    public Date getClockedOutDate() {
        if (clockedOut == null) { return null;}
        return (Date)clockedOut.clone();
    }

}

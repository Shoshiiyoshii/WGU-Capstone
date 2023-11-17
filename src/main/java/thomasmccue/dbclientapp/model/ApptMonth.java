package thomasmccue.dbclientapp.model;

/**
 * This class represents an ApptMonth object, an object consisting of a month of the year and
 * a count of how many appointments occur in that month.
 */
public class ApptMonth {
    private String month;
    private int appt;

    /**
     * This is a constructor to create a ApptMonth object so that an observable list of
     * ApptMonths can be used to display in the appointment report window.
     *
     * @param month one of the 12 months of the year
     * @param appt how many appointments occur in that month
     */
    public ApptMonth(String month, int appt){
        this.month = month;
        this.appt = appt;
    }

    /**
     * Accessor for retrieving this ApptMonths month
     * @return a String representing a month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Accessor for retrieving this ApptMonths appointment count
     * @return integer representing how many appointments occur in this.month
     */
    public int getAppt() {
        return appt;
    }

    /**
     * Mutator for setting this ApptMonths month
     * @param month the name of the month being assigned
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Mutator for setting this ApptMonths appointment count
     *
     * @param \int representing the number of appointments in this month
     */
    public void setAppt(int appt) {
        this.appt = appt;
    }
}

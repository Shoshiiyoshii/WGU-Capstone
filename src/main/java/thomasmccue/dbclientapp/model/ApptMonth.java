package thomasmccue.dbclientapp.model;

public class ApptMonth {
    private String month;
    private int appt;

    public ApptMonth(String month, int appt){
        this.month = month;
        this.appt = appt;
    }

    public String getMonth() {
        return month;
    }
    public int getAppt() {
        return appt;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAppt(int appt) {
        this.appt = appt;
    }
}

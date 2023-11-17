package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * This class represents a ContactAppts object, an object that is essentially a truncated
 * version of an Appointment, with only the variables needed to display in the contact schedule report.
 */
public class ContactAppts {
    private int apptId;
    private String title;
    private String desc;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custId;

    /**
     * Constructor for creating an ContactAppts object.
     *
     * @param title String representing appointment title
     * @param desc String representing appointment description
     * @param type String representing appointment type
     * @param start LocalDateTime representing the appointments start time in the users local/systemDefault zone
     * @param end LocalDateTime representing the appointments end time in the users local/systemDefault zone
     * @param custId integer representing the ID of the customer associated with the appointment
     */
    public ContactAppts(int apptId, String title, String desc, String type, LocalDateTime start, LocalDateTime end, int custId) {
        this.apptId = apptId;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
    }

    /**
     * Accessor for retrieving this appointments ID
     * @return integer this appointments ID
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * Mutator for setting this appointments appointment ID
     * @param apptId the appointment ID to be set
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * Accessor for retrieving this appointments title
     * @return string representing this appointments title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mutator for setting this appointments title
     * @param title the title being assigned
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor for retrieving this appointments description
     * @return string representing this appointments description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Mutator for setting this appointments description
     * @param desc the description being assigned
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Accessor for retrieving this appointments type
     * @return string representing this appointments type
     */
    public String getType() {
        return type;
    }

    /**
     * Mutator for setting this appointments type
     * @param type the type being assigned
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Accessor for retrieving this appointments start time/date
     * @return localDateTime representing the start time for this appointment
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Mutator for setting this appointments start time
     * @param start the start time being assigned
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Accessor for retrieving this appointments end time/date
     * @return localDateTime representing the end time for this appointment
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Mutator for setting this appointments end time
     * @param end the end time being assigned
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Accessor for retrieving the ID of the customer associated with this appointment
     * @return integer representing the customers ID
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Mutator for setting which customer is associated with this appointment
     * @param custId int representing an associated customers ID
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }
}


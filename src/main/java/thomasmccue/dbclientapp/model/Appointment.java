package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * This class represents an appointment.
 */
public class Appointment {
    private int apptId;
    private String title;
    private String desc;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int custId;
    private int userId;
    private int contactId;

    /**
     * Constructor for creating an appointment that has not yet had an ID auto-assigned by the database.
     *
     * @param title String representing appointment title
     * @param desc String representing appointment description
     * @param location String representing appointment location
     * @param type String representing appointment type
     * @param start LocalDateTime representing the appointments start time in the users local/systemDefault zone
     * @param end LocalDateTime representing the appointments end time in the users local/systemDefault zone
     * @param createDate LocalDateTime representing the time that the appointment was created
     *                  in the users local/systemDefault zone
     * @param createdBy String representing which user created the appointment
     * @param lastUpdate LocalDateTime representing the time that the appointment was last updated
     *                   in the users local/systemDefault zone
     * @param lastUpdatedBy String representing which user updated the appointment
     * @param custId integer representing the ID of the customer associated with the appointment
     * @param userId integer representing the ID of the user associated with the appointment
     * @param contactId integer representing the ID of the contact associated with the appointment
     */
    public Appointment(String title, String desc, String location, String type,
                        LocalDateTime start, LocalDateTime end,
                        LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                        String lastUpdatedBy, int custId, int userId, int contactId) {
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.custId = custId;
        this.userId = userId;
        this.contactId = contactId;
    }
    /**
     * Constructor for creating an appointment that has an appointment ID auto-assigned by the database.
     *
     * @param apptId integer representing the appointment ID
     * @param title String representing appointment title
     * @param desc String representing appointment description
     * @param location String representing appointment location
     * @param type String representing appointment type
     * @param start LocalDateTime representing the appointments start time in the users local/systemDefault zone
     * @param end LocalDateTime representing the appointments end time in the users local/systemDefault zone
     * @param createDate LocalDateTime representing the time that the appointment was created
     *                  in the users local/systemDefault zone
     * @param createdBy String representing which user created the appointment
     * @param lastUpdate LocalDateTime representing the time that the appointment was last updated
     *                   in the users local/systemDefault zone
     * @param lastUpdatedBy String representing which user updated the appointment
     * @param custId integer representing the ID of the customer associated with the appointment
     * @param userId integer representing the ID of the user associated with the appointment
     * @param contactId integer representing the ID of the contact associated with the appointment
     */
    public Appointment(int apptId, String title, String desc, String location, String type,
                       LocalDateTime start, LocalDateTime end,
                       LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                       String lastUpdatedBy, int custId, int userId, int contactId) {
        this.apptId = apptId;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.custId = custId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Mutator for setting this appointments appointment ID
     *
     * @param apptId the appointment ID to be set
     */
   public void setApptId(int apptId){this.apptId = apptId;}

    /**
     * Mutator for setting this appointments title
     *
     * @param title the title being assigned
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Mutator for setting this appointments description
     *
     * @param desc the description being assigned
     */
    public void setDesc(String desc){
        this.desc = desc;
    }

    /**
     * Mutator for setting this appointments location
     *
     * @param location the location being assigned
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Mutator for setting this appointments type
     *
     * @param type the type being assigned
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Mutator for setting this appointments start time
     *
     * @param start the start time being assigned
     */
    public void setStart(LocalDateTime start){
        this.start = start;
    }

    /**
     * Mutator for setting this appointments end time
     *
     * @param end the end time being assigned
     */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }

    /**
     * Mutator for setting the time this appointment was created
     *
     * @param createDate the createDate being assigned
     */
    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
    }

    /**
     * Mutator for setting who created this appointment
     *
     * @param createdBy string representing who created the appointment
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * Mutator for setting the time this appointment was last updated
     *
     * @param updateDate the updateDate being assigned
     */
    public void setLastUpdate(LocalDateTime updateDate){
        this.lastUpdate = updateDate;
    }

    /**
     * Mutator for setting who most recently updated this appointment
     *
     * @param lastUpdatedBy string representing who last updated the appointment
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Mutator for setting which customer is associated with this appointment
     *
     * @param custId int representing an associated customers ID
     */
    public void setCustId(int custId){
        this.custId = custId;
    }

    /**
     * Mutator for setting which user is associated with this appointment
     *
     * @param userId int representing an associated users ID
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Mutator for setting which contact is associated with this appointment
     *
     * @param contactId int representing an associated contacts ID
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    /**
     * Accessor for retrieving this appointments ID
     *
     * @return integer this appointments ID
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * Accessor for retrieving this appointments title
     *
     * @return string representing this appointments title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for retrieving this appointments description
     *
     * @return string representing this appointments description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Accessor for retrieving this appointments location
     *
     * @return string representing this appointments location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Accessor for retrieving this appointments type
     *
     * @return string representing this appointments type
     */
    public String getType() {
        return type;
    }

    /**
     * Accessor for retrieving this appointments start time/date
     *
     * @return localDateTime representing the start time for this appointment
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Accessor for retrieving this appointments end time/date
     *
     * @return localDateTime representing the end time for this appointment
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Accessor for retrieving this appointments create time/date
     *
     * @return localDateTime representing the time this appointment was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Accessor for retrieving this who created this appointment
     *
     * @return string representing this appointments creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Accessor for retrieving this appointments last update time/date
     *
     * @return localDateTime representing the time this appointment was most recently updated
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdate;
    }

    /**
     * Accessor for retrieving this who last updated this appointment
     *
     * @return string representing who most recently updated this appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Accessor for retrieving the ID of the customer associated with this appointment
     *
     * @return integer representing the customers ID
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Accessor for retrieving the ID of the user associated with this appointment
     *
     * @return integer representing the users ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Accessor for retrieving the ID of the contact associated with this appointment
     *
     * @return integer representing the contacts ID
     */
    public int getContactId() {
        return contactId;
    }
}


package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

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

    //constructor
    public Appointment(int apptId, String title, String desc, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       LocalDateTime lastUpdate, String lastUpdatedBy, int custId, int userId, int contactId) {
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
    public Appointment(){}

    //setters
    public void setApptId(int apptId){
        this.apptId = apptId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setStart(LocalDateTime start){
        this.start = start;
    }

    public void setEnd(LocalDateTime end){
        this.end = end;
    }

    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public void setLastUpdate(LocalDateTime updateDate){
        this.lastUpdate = updateDate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setCustId(int custId){
        this.custId = custId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    //getters
    public int getApptId() {
        return apptId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCustId() {
        return custId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }
}


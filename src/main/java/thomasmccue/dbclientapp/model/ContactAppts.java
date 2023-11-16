package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

public class ContactAppts {
    private int apptId;
    private String title;
    private String desc;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custId;

    public ContactAppts(int apptId, String title, String desc, String type, LocalDateTime start, LocalDateTime end, int custId) {
        this.apptId = apptId;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
    }

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
}


package thomasmccue.dbclientapp.model;

public class TypeAppt {
    private String type;
    private int appts;

    public TypeAppt(String type, int appts) {
        this.type = type;
        this.appts = appts;
    }

    public String getType() {
        return type;
    }
    public int getAppts() {
        return appts;
    }
    public void setAppts(int appts) {
        this.appts = appts;
    }
    public void setType(String type) {
        this.type = type;
    }
}

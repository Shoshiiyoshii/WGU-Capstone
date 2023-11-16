package thomasmccue.dbclientapp.model;

public class CustomerDivs {
    private String divName;
    private int custs;

    public CustomerDivs(String divName, int custs) {
        this.divName = divName;
        this.custs = custs;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    public int getCusts() {
        return custs;
    }

    public void setCusts(int custs) {
        this.custs = custs;
    }
}

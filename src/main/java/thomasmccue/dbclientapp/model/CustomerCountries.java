package thomasmccue.dbclientapp.model;

public class CustomerCountries {
    private String country;
    private int custs;

    public CustomerCountries(String country, int custs) {
        this.country = country;
        this.custs = custs;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCusts() {
        return custs;
    }

    public void setCusts(int custs) {
        this.custs = custs;
    }
}

package thomasmccue.dbclientapp.model;

/**
 * This class represents an object CustomerCountries, used to count how many customers
 * live in each country where the business operates.
 */
public class CustomerCountries {
    private String country;
    private int custs;

    /**
     * Constructor for creating a CustomerCountries object
     * @param country representing one of the countries in which the business operates
     * @param custs a count of how many customers live in the country associated with this object
     */
    public CustomerCountries(String country, int custs) {
        this.country = country;
        this.custs = custs;
    }

    /**
     * Accessor for retrieving the country associated with this CustomerCountries object
     *
     * @return string representing the country associated with this CustomerCountries object
     */
    public String getCountry() {
        return country;
    }

    /**
     * Mutator for setting the country associated with this CustomerCountries object
     *
     * @param country string representing the country associated with this CustomerCountries object
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Accessor for retrieving the customer count associated with this CustomerCountries object
     *
     * @return integer representing the customer count
     */
    public int getCusts() {
        return custs;
    }

    /**
     * Mutator for setting the customer count associated with this CustomerCountries object
     *
     * @param custs integer representing the customer count
     */
    public void setCusts(int custs) {
        this.custs = custs;
    }
}

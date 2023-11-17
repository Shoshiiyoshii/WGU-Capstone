package thomasmccue.dbclientapp.model;

/**
 * This class represents an object CustomerDivs, used to count how many customers
 * live in each first level division where the business operates.
 */
public class CustomerDivs {
    private String divName;
    private int custs;

    /**
     * Constructor for creating a CustomerDivs object
     * @param divName representing one of the first level divisions in which the business operates
     * @param custs a count of how many customers live in the country associated with this object
     */
    public CustomerDivs(String divName, int custs) {
        this.divName = divName;
        this.custs = custs;
    }

    /**
     * Accessor for retrieving the first level divisions associated with this CustomerDivs object
     *
     * @return string representing the first level divisions associated with this CustomerDivs object
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Mutator for setting the first level division associated with this CustomerDivs object
     *
     * @param divName string representing the first level division associated with this CustomerDivs object
     */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /**
     * Accessor for retrieving the customer count associated with this CustomerDivs object
     *
     * @return integer representing the customer count
     */
    public int getCusts() {
        return custs;
    }

    /**
     * Mutator for setting the customer count associated with this CustomerDivs object
     *
     * @param custs integer representing the customer count
     */
    public void setCusts(int custs) {
        this.custs = custs;
    }
}

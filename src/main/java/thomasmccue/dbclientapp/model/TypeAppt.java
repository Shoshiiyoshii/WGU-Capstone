package thomasmccue.dbclientapp.model;
/**
 * This class represents an object TypeAppt, used to count how many appointments
 * there are of each type
 */
public class TypeAppt {
    private String type;
    private int appts;

    /**
     * Constructor to create a TypeAppt object
     * @param type string representing a type of appointment
     * @param appts integer count of how many appointments there are of a certain type
     */
    public TypeAppt(String type, int appts) {
        this.type = type;
        this.appts = appts;
    }

    /**
     * Accessor for retrieving the type of appointment associated with this TypeAppts object
     *
     * @return string representing the type of appointment associated with this TypeAppts object
     */
    public String getType() {
        return type;
    }

    /**
     * Accessor for retrieving the count of how many appointments there are of the type
     * associated with this TypeAppts object
     *
     * @return integer count of how many appointments there are of a certain type
     */
    public int getAppts() {
        return appts;
    }

    /**
     * Mutator for setting the count of how many appointments there are of the type
     * associated with this TypeAppts object
     *
     * @param appts integer count of how many appointments there are of a certain type
     */
    public void setAppts(int appts) {
        this.appts = appts;
    }

    /**
     * Mutator for setting the type of appointment associated with this TypeAppts object
     *
     * @param type String representing the type of appointment associated with this TypeAppts object
     */
    public void setType(String type) {
        this.type = type;
    }
}

package thomasmccue.dbclientapp.model;

/**
 * This class represents a contact.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor to create a contact object.
     *
     * @param contactID represents the contacts ID
     * @param contactName represents the contacts Name
     * @param contactEmail represents contacts email
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Mutator for setting this contacts ID
     * @param contactID the ID being assigned
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Mutator for setting this contacts name
     * @param contactName the name being assigned
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Mutator for setting this contacts email
     * @param contactEmail the email being assigned
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Accessor for retrieving this contacts email
     * @return string representing this contacts email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Accessor for retrieving this contacts name
     * @return string representing this contacts name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Accessor for retrieving this contacts ID
     * @return string representing this contacts ID
     */
    public int getContactID() {
        return contactID;
    }
}

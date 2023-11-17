package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * This class represents a customer.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String country;

    /**
     * Constructor for creating a Customer object that does not yet exist in the database,
     * i.e. does not yet have an assigned ID
     *
     * @param customerName String representing the customer's name
     * @param address      String representing the customer's address
     * @param postalCode   String representing the customer's postal code
     * @param phone        String representing the customer's phone number
     * @param createDate   LocalDateTime representing the time when the customer was created
     *                     in the user's local/systemDefault zone
     * @param createdBy    String representing who created the customer entry
     * @param lastUpdate   LocalDateTime representing the time when the customer was
     *                     last updated in the user's local/systemDefault zone
     * @param lastUpdatedBy String representing who last updated the customer object
     * @param divisionId   Integer representing the division ID associated with the customer
     * @param country      String representing the country associated with the customer
     */
    public Customer(String customerName, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                    int divisionId, String country) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * Constructor for creating a Customer object that already has a customer ID assigned by the database
     *
     * @param customerId    Integer representing the customer's ID
     * @param customerName String representing the customer's name
     * @param address      String representing the customer's address
     * @param postalCode   String representing the customer's postal code
     * @param phone        String representing the customer's phone number
     * @param createDate   LocalDateTime representing the time when the customer was created
     *                     in the user's local/systemDefault zone
     * @param createdBy    String representing who created the customer entry
     * @param lastUpdate   LocalDateTime representing the time when the customer was
     *                     last updated in the user's local/systemDefault zone
     * @param lastUpdatedBy String representing who last updated the customer object
     * @param divisionId   Integer representing the division ID associated with the customer
     * @param country      String representing the country associated with the customer
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                    int divisionId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.country = country;
    }
    // Setters

    /**
     * Mutator for setting this customer's ID
     *
     * @param customerId Integer representing the customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Mutator for setting this customer's name
     *
     * @param customerName String representing the name of the customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Mutator for setting this customer's address
     *
     * @param address String representing the address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Mutator for setting this customer's postal code
     *
     * @param postalCode String representing the postal code of the customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Mutator for setting this customer's phone number
     *
     * @param phone String representing the phone number of the customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Mutator for setting this customer's creation date
     *
     * @param createDate LocalDateTime representing the create date of the customer
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Mutator for setting the creator of this customer
     *
     * @param createdBy String representing who created the customer object
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Mutator for setting the last update time of this customer
     *
     * @param lastUpdate LocalDateTime representing the last update time of this customer
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Mutator for setting who last updated this customer
     *
     * @param lastUpdatedBy String representing who last updated the customer
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Mutator for setting the division ID associated with this customer
     *
     * @param divisionId Integer representing the division ID to be associated with the customer
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Mutator for setting this customers associated country
     *
     * @param country String representing the country associated with this customer
     */
    public void setCountry(String country) {
        this.country = country;
    }

    // Getters

    /**
     * Accessor for retrieving this customer's ID
     *
     * @return Integer representing this customer's ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Accessor for retrieving this customer's name
     *
     * @return String representing this customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Accessor for retrieving this customer's address
     *
     * @return String representing this customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Accessor for retrieving this customer's postal code
     *
     * @return String representing this customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Accessor for retrieving this customer's phone number
     *
     * @return String representing this customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Accessor for retrieving this customer's creation date and time
     *
     * @return LocalDateTime representing the date and time this customer was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Accessor for retrieving the name of who created this customer
     *
     * @return String representing who created this customer
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Accessor for retrieving the time this customer was last updated
     *
     * @return LocalDateTime representing the time this customer was most recently updated
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Accessor for retrieving who last updated this customer
     *
     * @return String representing who last updated this customer
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * Accessor for retrieving the division ID associated with this customer
     *
     * @return integer representing the division ID associated with this customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Accessor for retrieving the country associated with this customer
     *
     * @return String representing the country associated with this customer
     */
    public String getCountry() {
        return country;
    }
}


package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * The ActiveCustomer class represents a customer who is considered active, meaning they have had an appointment
 * with the company within the last 6 months or have an upcoming appointment. This class inherits from the abstract
 * Customer class and provides specific attributes and behaviors for an active customer.
 */
public class ActiveCustomer extends Customer {

    /**
     * The status of the customer, which is fixed as "Active" for all instances of ActiveCustomer.
     */
    private final String status = "Active";

    /**
     * Constructs a new ActiveCustomer object with the provided parameters.
     * This constructor is typically used for adding a new customer to the system when the customerId has not yet been
     * set by the MySQL database.
     *
     * @param customerName  The name of the customer.
     * @param address       The address of the customer.
     * @param postalCode    The postal code of the customer.
     * @param phone         The phone number of the customer.
     * @param createDate    The date and time the customer record was created.
     * @param createdBy     The name of the user who created the customer record.
     * @param lastUpdate    The date and time the customer record was last updated.
     * @param lastUpdatedBy The name of the user who last updated the customer record.
     * @param divisionId    The division ID where the customer resides.
     * @param country       The country where the customer resides.
     */
    public ActiveCustomer(String customerName, String address, String postalCode, String phone,
                          LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                          int divisionId, String country) {
        super(customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId,
                country);
    }

    /**
     * Constructs a new ActiveCustomer object with the provided parameters, including the customer ID.
     * This constructor is typically used when the customer ID is known, such as when retrieving customer information
     * from the database.
     *
     * @param customerId    The unique ID of the customer.
     * @param customerName  The name of the customer.
     * @param address       The address of the customer.
     * @param postalCode    The postal code of the customer.
     * @param phone         The phone number of the customer.
     * @param createDate    The date and time the customer record was created.
     * @param createdBy     The name of the user who created the customer record.
     * @param lastUpdate    The date and time the customer record was last updated.
     * @param lastUpdatedBy The name of the user who last updated the customer record.
     * @param divisionId    The division ID where the customer resides.
     * @param country       The country where the customer resides.
     */
    public ActiveCustomer(int customerId, String customerName, String address, String postalCode, String phone,
                          LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                          int divisionId, String country) {
        super(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy,
                divisionId, country);
    }

    /**
     * Returns the status of the customer, which is always "Active" for this class.
     *
     * @return The status of the customer as a String.
     */
    @Override
    public String getStatus() {
        return status;
    }
}

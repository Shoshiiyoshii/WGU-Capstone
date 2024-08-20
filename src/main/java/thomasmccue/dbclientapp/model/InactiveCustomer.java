package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

public class InactiveCustomer extends Customer{
    public InactiveCustomer(String customerName, String address, String postalCode, String phone,
                            LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                            int divisionId, String country) {
        super(customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, country);
    }

    public InactiveCustomer(int customerId, String customerName, String address, String postalCode, String phone,
                            LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                            int divisionId, String country) {
        super(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy,
                divisionId, country);
    }


}

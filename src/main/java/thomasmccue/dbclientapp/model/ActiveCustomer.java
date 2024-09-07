package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

public class ActiveCustomer extends Customer {
    private final String status = "Active";
    public ActiveCustomer(String customerName, String address, String postalCode, String phone,
                          LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                          int divisionId, String country) {
        super(customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId,
                country);
    }

    public ActiveCustomer(int customerId, String customerName, String address, String postalCode, String phone,
                          LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                          int divisionId, String country) {
        super(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy,
                divisionId, country);
    }

    @Override
    public String getStatus() {
        return status;
    }
}

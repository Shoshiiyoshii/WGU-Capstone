package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * This class represents a country.
 */
public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor for creating a Country object.
     *
     * @param countryId     Integer representing the country ID
     * @param country       String representing the name of the country
     * @param createDate    LocalDateTime representing the countries create time
     *                      in the user's local/systemDefault zone
     * @param createdBy     String representing who created the country
     * @param update        LocalDateTime representing the time when the country was
     *                      last updated, in the user's local/systemDefault zone
     * @param lastUpdatedBy String representing who last updated the country object
     */
    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime update, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        lastUpdate = update;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Mutator for setting this country's ID
     *
     * @param countryId Integer representing the country ID to be set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Mutator for setting this country's name
     *
     * @param country String representing the name of the country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Mutator for setting this country's creation date
     *
     * @param createDate LocalDateTime representing the creation date of the country
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Mutator for setting the creator of this country
     *
     * @param createdBy String representing the creator of the country
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Mutator for setting the last update time of this country
     *
     * @param lastUpdate LocalDateTime representing the last update time of the country
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Accessor for retrieving this country's ID
     *
     * @return Integer representing this country's ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Accessor for retrieving this country's name
     *
     * @return String representing this country's name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Accessor for retrieving this country's creation date
     *
     * @return LocalDateTime representing the time this country was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Accessor for retrieving the name of the creator of this country
     *
     * @return String representing who created this country object
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Accessor for retrieving the last update time of this country
     *
     * @return LocalDateTime representing the time this country was most recently updated
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Accessor for retrieving who last updated this country object
     *
     * @return String representing who last updated this country entry
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
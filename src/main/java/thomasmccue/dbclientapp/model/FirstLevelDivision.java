package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * This class represents a first level division.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Constructor for creating a first level division object.
     *
     * @param divisionId integer representing the Division ID
     * @param division String representing the name of the division
     * @param createDate LocalDateTime representing the create date and time
     *                  for this object in the users systemDefault time
     * @param createdBy String representing who created this first level division object
     * @param lastUpdate LocalDateTime representing the last update date and time
     *                   for this object in the users systemDefault time
     * @param lastUpdatedBy String representing who last updated this first level division object
     * @param countryId integer representing the ID of the country associated with
     *                 this first level division
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate, String createdBy,
                              LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * Mutator for setting the first level division ID associated with this FirstLevelDivision object
     *
     * @param divisionId integer representing the division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Mutator for setting the name of the division associated with this FirstLevelDivision object
     *
     * @param division String representing the name of the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Mutator for setting the create date and time associated with this FirstLevelDivision object
     *
     * @param createDate localDateTime representing when this object was created
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Mutator for setting who created this FirstLevelDivision object
     *
     * @param createdBy string representing who created this FirstLevelDivision object
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Mutator for setting the last update date and time associated with this FirstLevelDivision object
     *
     * @param lastUpdate localDateTime representing when this object was last updated
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Mutator for setting who last updated this FirstLevelDivision object
     *
     * @param lastUpdatedBy string representing who last updated this FirstLevelDivision object
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Mutator for setting the country ID associated with this FirstLevelDivision object
     *
     * @param countryId integer representing the country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Accessor for retrieving the first level division ID associated with this FirstLevelDivision object
     *
     * @return integer representing the first level division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Accessor for retrieving the first level division associated with this FirstLevelDivision object
     *
     * @return String representing the first level division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Accessor for retrieving the create date and time associated with this FirstLevelDivision object
     *
     * @return localDateTime representing the create date and time associated with this FirstLevelDivision object
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Accessor for retrieving who created this FirstLevelDivision object
     *
     * @return String representing who created this FirstLevelDivision object
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Accessor for retrieving the last update date and time associated with this FirstLevelDivision object
     *
     * @return localDateTime representing the last update date and time associated with this FirstLevelDivision object
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Accessor for retrieving who last updated this FirstLevelDivision object
     *
     * @return String representing who last updated this FirstLevelDivision object
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Accessor for retrieving the country ID associated with this FirstLevelDivision object
     *
     * @return integer representing the country ID
     */
    public int getCountryId() {
        return countryId;
    }
}


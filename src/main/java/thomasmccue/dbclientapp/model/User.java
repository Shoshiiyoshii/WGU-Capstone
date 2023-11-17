package thomasmccue.dbclientapp.model;

import java.time.LocalDateTime;

/**
 * Objects of this class represent a user and is used to validate log in attempts
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor to create a User object
     * @param userId integer representing the users ID associated with this User object
     * @param userName String representing the username associated with this User object
     * @param password String representing the password associated with this User object
     * @param createDate LocalDateTime representing the time that this User object was created
     *                   in the users local/systemDefault zone
     * @param createdBy String representing who created this User object
     * @param lastUpdate LocalDateTime representing the last time that this User object was updated
     *                   in the users local/systemDefault zone
     * @param lastUpdatedBy String representing who last updated this User object
     */
    public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy,
                LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Mutator for setting this Users ID
     *
     * @param userId the  ID to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Mutator for setting this Users name
     *
     * @param userName the name to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Mutator for setting this Users password
     *
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Mutator for setting this Users create date and time
     *
     * @param createDate the time at which the user object was created
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Mutator for setting who created this User object
     *
     * @param createdBy String representing who created this User object
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Mutator for setting this Users last update date and time
     *
     * @param lastUpdate the time at which the user object was last updated
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Mutator for setting who last updated this User object
     *
     * @param lastUpdatedBy String representing who last updated this User object
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Accessor for retrieving the ID associated with this User object
     *
     * @return integer representing the user ID associated with this User object
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Accessor for retrieving the username associated with this User object
     *
     * @return string representing the username associated with this User object
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Accessor for retrieving the password associated with this User object
     *
     * @return string representing the password associated with this User object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Accessor for retrieving the time and date this User object was created
     *
     * @return localDateTime representing time and date this User object was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Accessor for retrieving who created this User object
     *
     * @return string representing who created this User object
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Accessor for retrieving the time and date this User object was last updated
     *
     * @return localDateTime representing time and date this User object was last updated
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Accessor for retrieving who last updated this User object
     *
     * @return string representing who last updated this User object
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}


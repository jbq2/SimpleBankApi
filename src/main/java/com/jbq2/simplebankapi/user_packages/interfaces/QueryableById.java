package com.jbq2.simplebankapi.user_packages.interfaces;

/**
 * Interface for classifying Java objects that are can be inserted into the database.
 * @author Joshua Quizon
 * @version 0.1
 */
public interface QueryableById {

    /**
     * Gets the ID of the queryable object.
     * @return Returns a Long that is the queryable object's ID.
     */
    Long getId();

    /**
     * Sets the ID of the queryable object.
     * @param id ID of the queryable object
     */
    void setId(Long id);
}

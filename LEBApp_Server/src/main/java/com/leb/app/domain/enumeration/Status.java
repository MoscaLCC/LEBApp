package com.leb.app.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    OPENED,
    WAITING_COLLECTION, 
    WAITING_COLLECTION_ACCEPTED, 
    IN_COLLECTION, 
    WAITING_TRANSIT, 
    WAITING_TRANSIT_ACCEPTED, 
    IN_TRANSIT, 
    WAITING_DISTRIBUTION, 
    WAITING_DISTRIBUTION_ACCEPTED, 
    IN_DISTRIBUTION,
    CLOSED,
    REJECTED, 
    CANCELED, 
    FINISHED
}

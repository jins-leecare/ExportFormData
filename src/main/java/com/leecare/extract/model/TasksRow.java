package com.leecare.extract.model;

import java.io.Serializable;

public class TasksRow implements Serializable {
    private String date;
    private String time;
    private String name;
    private String description;
    private String duration;
    private String dateToBeCompleted;
    private String createdBy;
    private String performedBy;
    private String residentName;
    private Integer residentID;
    private String status;
    private String location;
    private String comments;
    private String chargeElement;
    private String categories;
    private String dismissedBy;
    private String dismissedOn;
    private String supplier;

    /** @return the Start Date (not null) */
    public String getDate() {
        return date;
    }

    /** @param aDate a Start Date to set (not null) */
    public void setDate(String aDate) {
        date = aDate;
    }

    /** @return the Time Period (not null) */
    public String getTime() {
        return time;
    }

    /** @param aTime a Time Period to set (not null) */
    public void setTime(String aTime) {
        time = aTime;
    }

    /** @return the Name of the Event (not null) */
    public String getName() {
        return name;
    }

    /** @param aName a Name of Event to set (not null) */
    public void setName(String aName) {
        name = aName;
    }

    /** @return the Event Description (not null) */
    public String getDescription() {
        return description;
    }

    /** @param aDescription a Event Description to set (not null) */
    public void setDescription(String aDescription) {
        description = aDescription;
    }

    /** @return the Duration Of Event (not null) */
    public String getDuration() {
        return duration;
    }

    /** @param aDuration a Duration Of Event to set (not null) */
    public void setDuration(String aDuration) {
        duration = aDuration;
    }

    /** @return the Date to be Completed (not null) */
    public String getDateToBeCompleted() {
        return dateToBeCompleted;
    }

    /** @param aDateToBeCompleted a Date to be Completed to set (not null) */
    public void setDateToBeCompleted(String aDateToBeCompleted) {
        dateToBeCompleted = aDateToBeCompleted;
    }

    /** @return the Event Creator (not null) */
    public String getCreatedBy() {
        return createdBy;
    }

    /** @param aCreatedBy a Event Creator to set (not null) */
    public void setCreatedBy(String aCreatedBy) {
        createdBy = aCreatedBy;
    }

    /** @return the Event Performed Person (not null) */
    public String getPerformedBy() {
        return performedBy;
    }

    /** @param aPerformedBy a Event Performed Person to set (not null) */
    public void setPerformedBy(String aPerformedBy) {
        performedBy = aPerformedBy;
    }

    /** @return the Resident Name (not null) */
    public String getResidentName() {
        return residentName;
    }

    /** @param aResidentName a Resident Name to set (not null) */
    public void setResidentName(String aResidentName) {
        residentName = aResidentName;
    }

    public Integer getResidentID() {
        return residentID;
    }

    public void setResidentID(Integer residentID) {
        this.residentID = residentID;
    }

    /** @return the Event Status (not null) */
    public String getStatus() {
        return status;
    }

    /** @param aStatus a Event Status to set (not null) */
    public void setStatus(String aStatus) {
        status = aStatus;
    }

    /** @return the Location (not null) */
    public String getLocation() {
        return location;
    }

    /** @param aLocation a Location to set (not null) */
    public void setLocation(String aLocation) {
        location = aLocation;
    }

    /** @return the Event Comments (not null) */
    public String getComments() {
        return comments;
    }

    /** @param aComments a Event Comments to set (not null) */
    public void setComments(String aComments) {
        comments = aComments;
    }

    /** @return the Charge Element (not null) */
    public String getChargeElement() {
        return chargeElement;
    }

    /** @param aChargeElement a Charge Element to set (not null) */
    public void setChargeElement(String aChargeElement) {
        chargeElement = aChargeElement;
    }

    /** @return the Categories Comma Separated (not null) */
    public String getCategories() {
        return categories;
    }

    /** @param aCategories a Categories Comma Separated to set (not null) */
    public void setCategories(String aCategories) {
        categories = aCategories;
    }

    /** @return the Dismissed By (not null) */
    public String getDismissedBy() {
        return dismissedBy;
    }

    /** @param aDismissedBy a Dismissed By to set (not null) */
    public void setDismissedBy(String aDismissedBy) {
        dismissedBy = aDismissedBy;
    }

    /** @return the Dismissed Date (not null) */
    public String getDismissedOn() {
        return dismissedOn;
    }

    /** @param aDismissedOn a Dismissed Date to set (not null) */
    public void setDismissedOn(String aDismissedOn) {
        dismissedOn = aDismissedOn;
    }

    /** @return the supplier (can be null). */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier.
     *
     * @param aSupplier the supplier to set (can be null).
     */
    public void setSupplier(String aSupplier) {
        supplier = aSupplier;
    }
}


/*
 * TotalBedMovement.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;

/**
 * This is used for a TotalBedMovement.
 *
 * @author jjoy
 */
public class TotalBedMovement implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int residentID;
    private String residentName;
    private String dateOfBirth;
    private String govNumber;
    private String gender;
    private String admindate;
    private String newadmission;
    private String leavetype;
    private String typeOfCare;
    private String leavetypereason;
    private String plannedindicatior;
    private String leavereason;
    private String leaveStart;
    private String plannedLeaveEnd;
    private String leaveEndDate;
    private String dischargetype;
    private String dischargedate;
    private String dischargetypereason;
    private String deathdate;
    private String dischargereason;
    private String facilityname;

    /**
     * Constructs a TotalBedMovement.
     *
     */
    public TotalBedMovement() {}

    /**
     * @return the action
     */
    public String getAction() {
        return action;}

    /**
     * Sets the action.
     *
     * @param aAction the action to set
     */
    public void setAction(String aAction) {
        action = aAction;}

    /**
     * @return the residentID
     */
    public int getResidentID() {
        return residentID;}

    /**
     * Sets the residentID.
     *
     * @param aResidentID the residentID to set
     */
    public void setResidentID(int aResidentID) {
        residentID = aResidentID;}

    /**
     * @return the residentName
     */
    public String getResidentName() {
        return residentName;}

    /**
     * Sets the residentName.
     *
     * @param aResidentName the residentName to set
     */
    public void setResidentName(String aResidentName) {
        residentName = aResidentName;}

    /**
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;}

    /**
     * Sets the dateOfBirth.
     *
     * @param aDateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(String aDateOfBirth) {
        dateOfBirth = aDateOfBirth;}

    /**
     * @return the govNumber
     */
    public String getGovNumber() {
        return govNumber;}

    /**
     * Sets the govNumber.
     *
     * @param aGovNumber the govNumber to set
     */
    public void setGovNumber(String aGovNumber) {
        govNumber = aGovNumber;}

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;}

    /**
     * Sets the gender.
     *
     * @param aGender the gender to set
     */
    public void setGender(String aGender) {
        gender = aGender;}

    /**
     * @return the admindate
     */
    public String getAdmindate() {
        return admindate;}

    /**
     * Sets the admindate.
     *
     * @param aAdmindate the admindate to set
     */
    public void setAdmindate(String aAdmindate) {
        admindate = aAdmindate;}

    /**
     * @return the newadmission
     */
    public String getNewadmission() {
        return newadmission;}

    /**
     * Sets the newadmission.
     *
     * @param aNewadmission the newadmission to set
     */
    public void setNewadmission(String aNewadmission) {
        newadmission = aNewadmission;}

    /**
     * @return the leavetype
     */
    public String getLeavetype() {
        return leavetype;}

    /**
     * Sets the leavetype.
     *
     * @param aLeavetype the leavetype to set
     */
    public void setLeavetype(String aLeavetype) {
        leavetype = aLeavetype;}

    /**
     * @return the typeOfCare
     */
    public String getTypeOfCare() {
        return typeOfCare;}

    /**
     * Sets the typeOfCare.
     *
     * @param aTypeOfCare the typeOfCare to set
     */
    public void setTypeOfCare(String aTypeOfCare) {
        typeOfCare = aTypeOfCare;}

    /**
     * @return the leavetypereason
     */
    public String getLeavetypereason() {
        return leavetypereason;}

    /**
     * Sets the leavetypereason.
     *
     * @param aLeavetypereason the leavetypereason to set
     */
    public void setLeavetypereason(String aLeavetypereason) {
        leavetypereason = aLeavetypereason;}

    /**
     * @return the plannedindicatior
     */
    public String getPlannedindicatior() {
        return plannedindicatior;}

    /**
     * Sets the plannedindicatior.
     *
     * @param aPlannedindicatior the plannedindicatior to set
     */
    public void setPlannedindicatior(String aPlannedindicatior) {
        plannedindicatior = aPlannedindicatior;}

    /**
     * @return the leavereason
     */
    public String getLeavereason() {
        return leavereason;}

    /**
     * Sets the leavereason.
     *
     * @param aLeavereason the leavereason to set
     */
    public void setLeavereason(String aLeavereason) {
        leavereason = aLeavereason;}

    /**
     * @return the leaveStart
     */
    public String getLeaveStart() {
        return leaveStart;}

    /**
     * Sets the leaveStart.
     *
     * @param aLeaveStart the leaveStart to set
     */
    public void setLeaveStart(String aLeaveStart) {
        leaveStart = aLeaveStart;}

    /**
     * @return the plannedLeaveEnd
     */
    public String getPlannedLeaveEnd() {
        return plannedLeaveEnd;}

    /**
     * Sets the plannedLeaveEnd.
     *
     * @param aPlannedLeaveEnd the plannedLeaveEnd to set
     */
    public void setPlannedLeaveEnd(String aPlannedLeaveEnd) {
        plannedLeaveEnd = aPlannedLeaveEnd;}

    /**
     * @return the leaveEndDate
     */
    public String getLeaveEndDate() {
        return leaveEndDate;}

    /**
     * Sets the leaveEndDate.
     *
     * @param aLeaveEndDate the leaveEndDate to set
     */
    public void setLeaveEndDate(String aLeaveEndDate) {
        leaveEndDate = aLeaveEndDate;}

    /**
     * @return the dischargetype
     */
    public String getDischargetype() {
        return dischargetype;}

    /**
     * Sets the dischargetype.
     *
     * @param aDischargetype the dischargetype to set
     */
    public void setDischargetype(String aDischargetype) {
        dischargetype = aDischargetype;}

    /**
     * @return the dischargedate
     */
    public String getDischargedate() {
        return dischargedate;}

    /**
     * Sets the dischargedate.
     *
     * @param aDischargedate the dischargedate to set
     */
    public void setDischargedate(String aDischargedate) {
        dischargedate = aDischargedate;}

    /**
     * @return the dischargetypereason
     */
    public String getDischargetypereason() {
        return dischargetypereason;}

    /**
     * Sets the dischargetypereason.
     *
     * @param aDischargetypereason the dischargetypereason to set
     */
    public void setDischargetypereason(String aDischargetypereason) {
        dischargetypereason = aDischargetypereason;}

    /**
     * @return the deathdate
     */
    public String getDeathdate() {
        return deathdate;}

    /**
     * Sets the deathdate.
     *
     * @param aDeathdate the deathdate to set
     */
    public void setDeathdate(String aDeathdate) {
        deathdate = aDeathdate;}

    /**
     * @return the dischargereason
     */
    public String getDischargereason() {
        return dischargereason;}

    /**
     * Sets the dischargereason.
     *
     * @param aDischargereason the dischargereason to set
     */
    public void setDischargereason(String aDischargereason) {
        dischargereason = aDischargereason;}

    /**
     * @return the facilityname
     */
    public String getFacilityname() {
        return facilityname;}

    /**
     * Sets the facilityname.
     *
     * @param aFacilityname the facilityname to set
     */
    public void setFacilityname(String aFacilityname) {
        facilityname = aFacilityname;}

}


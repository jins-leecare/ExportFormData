package com.leecare.extract.model;

public class BedMovement {
    private Integer createdByUserID;

    private String effectiveDateAndTime;

    private String createdTimestamp;

    private Integer id;

    private String bedMovementType;

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    private String leaveType;

    public Integer getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(Integer createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public String getEffectiveDateAndTime() {
        return effectiveDateAndTime;
    }

    public void setEffectiveDateAndTime(String effectiveDateAndTime) {
        this.effectiveDateAndTime = effectiveDateAndTime;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBedMovementType() {
        return bedMovementType;
    }

    public void setBedMovementType(String bedMovementType) {
        this.bedMovementType = bedMovementType;
    }
}

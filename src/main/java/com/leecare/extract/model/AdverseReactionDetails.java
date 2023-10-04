package com.leecare.extract.model;

import java.util.Date;

@SuppressWarnings("serial")
public class AdverseReactionDetails extends AdverseReaction {

    private String manifestationStringForReports;
    private Date createdOnForReport;
    private String createdUserName;
    private String subReportDirectory;
    private String residentName;

    public void setManifestationStringForReports(String manifestationStringForReports) {
        this.manifestationStringForReports = manifestationStringForReports;
    }

    public Date getCreatedOnForReport() {
        return createdOnForReport;
    }

    public void setCreatedOnForReport(Date createdOnForReport) {
        this.createdOnForReport = createdOnForReport;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getSubReportDirectory() {
        return subReportDirectory;
    }

    public void setSubReportDirectory(String subReportDirectory) {
        this.subReportDirectory = subReportDirectory;
    }

    public String getManifestationStringForReports() {
        return manifestationStringForReports;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }
}

/*
 * AdverseReactionDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.Date;

/**
 * This is used for a AdverseReactionDetails.
 *
 * @author jjoy
 */
@SuppressWarnings("serial")
public class AdverseReactionDetails extends AdverseReaction {

  private String manifestationStringForReports;
  private Date createdOnForReport;
  private String createdUserName;
  private String subReportDirectory;
  private String residentName;

  /** @return the manifestationStringForReports */
  public String getManifestationStringForReports() {
    return manifestationStringForReports;
  }
  /**
   * Sets the manifestationStringForReports.
   *
   * @param aManifestationStringForReports the manifestationStringForReports to set
   */
  public void setManifestationStringForReports(String aManifestationStringForReports) {
    manifestationStringForReports = aManifestationStringForReports;
  }
  /** @return the createdOnForReport */
  public Date getCreatedOnForReport() {
    return createdOnForReport;
  }
  /**
   * Sets the createdOnForReport.
   *
   * @param aCreatedOnForReport the createdOnForReport to set
   */
  public void setCreatedOnForReport(Date aCreatedOnForReport) {
    createdOnForReport = aCreatedOnForReport;
  }
  /** @return the createdUserName */
  public String getCreatedUserName() {
    return createdUserName;
  }
  /**
   * Sets the createdUserName.
   *
   * @param aCreatedUserName the createdUserName to set
   */
  public void setCreatedUserName(String aCreatedUserName) {
    createdUserName = aCreatedUserName;
  }
  /** @return the subReportDirectory */
  public String getSubReportDirectory() {
    return subReportDirectory;
  }
  /**
   * Sets the subReportDirectory.
   *
   * @param aSubReportDirectory the subReportDirectory to set
   */
  public void setSubReportDirectory(String aSubReportDirectory) {
    subReportDirectory = aSubReportDirectory;
  }
  /** @return the residentName */
  public String getResidentName() {
    return residentName;
  }
  /**
   * Sets the residentName.
   *
   * @param aResidentName the residentName to set
   */
  public void setResidentName(String aResidentName) {
    residentName = aResidentName;
  }
}

/*
 * InputParameters.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

/**
 * This is used for a InputParameters.
 *
 * @author jjoy
 */
public class InputParameters {
  private String formName;
  private String facilityId;
  private String configFile;
  private Boolean regularForm = false;
  private Boolean subForm = false;
  private Boolean gridForm = false;
  private Boolean customGridForm = false;
  private Boolean downloadAttachments = false;
  private Boolean bedMovement = false;
  private Boolean prescriptions = false;
  private Boolean medications = false;
  private Boolean sddMedications = false;
  private Boolean pdfExtract = false;
  private String fromDate;
  private String toDate;
  private String folderPath;
  private Boolean tasks = false;
  private Boolean progressNotes = false;
  private Boolean adverseReaction = false;
  private Boolean excludeUnadmittedResidentsFlag = false;
  private Boolean excludeArchivedResidentsFlag = false;
  private Boolean excludeReservedResidentsFlag = false;
  private Boolean unReadOnly = false;
  private ConfigProperties configProperties;
  /** @return the formName */
  public String getFormName() {
    return formName;
  }
  /**
   * Sets the formName.
   *
   * @param aFormName the formName to set
   */
  public void setFormName(String aFormName) {
    formName = aFormName;
  }
  /** @return the facilityId */
  public String getFacilityId() {
    return facilityId;
  }
  /**
   * Sets the facilityId.
   *
   * @param aFacilityId the facilityId to set
   */
  public void setFacilityId(String aFacilityId) {
    facilityId = aFacilityId;
  }
  /** @return the configFile */
  public String getConfigFile() {
    return configFile;
  }
  /**
   * Sets the configFile.
   *
   * @param aConfigFile the configFile to set
   */
  public void setConfigFile(String aConfigFile) {
    configFile = aConfigFile;
  }
  /** @return the regularForm */
  public Boolean getRegularForm() {
    return regularForm;
  }
  /**
   * Sets the regularForm.
   *
   * @param aRegularForm the regularForm to set
   */
  public void setRegularForm(Boolean aRegularForm) {
    regularForm = aRegularForm;
  }
  /** @return the subForm */
  public Boolean getSubForm() {
    return subForm;
  }
  /**
   * Sets the subForm.
   *
   * @param aSubForm the subForm to set
   */
  public void setSubForm(Boolean aSubForm) {
    subForm = aSubForm;
  }
  /** @return the gridForm */
  public Boolean getGridForm() {
    return gridForm;
  }
  /**
   * Sets the gridForm.
   *
   * @param aGridForm the gridForm to set
   */
  public void setGridForm(Boolean aGridForm) {
    gridForm = aGridForm;
  }
  /** @return the customGridForm */
  public Boolean getCustomGridForm() {
    return customGridForm;
  }
  /**
   * Sets the customGridForm.
   *
   * @param aCustomGridForm the customGridForm to set
   */
  public void setCustomGridForm(Boolean aCustomGridForm) {
    customGridForm = aCustomGridForm;
  }
  /** @return the downloadAttachments */
  public Boolean getDownloadAttachments() {
    return downloadAttachments;
  }
  /**
   * Sets the downloadAttachments.
   *
   * @param aDownloadAttachments the downloadAttachments to set
   */
  public void setDownloadAttachments(Boolean aDownloadAttachments) {
    downloadAttachments = aDownloadAttachments;
  }
  /** @return the bedMovement */
  public Boolean getBedMovement() {
    return bedMovement;
  }
  /**
   * Sets the bedMovement.
   *
   * @param aBedMovement the bedMovement to set
   */
  public void setBedMovement(Boolean aBedMovement) {
    bedMovement = aBedMovement;
  }
  /** @return the prescriptions */
  public Boolean getPrescriptions() {
    return prescriptions;
  }
  /**
   * Sets the prescriptions.
   *
   * @param aPrescriptions the prescriptions to set
   */
  public void setPrescriptions(Boolean aPrescriptions) {
    prescriptions = aPrescriptions;
  }
  /** @return the medications */
  public Boolean getMedications() {
    return medications;
  }
  /**
   * Sets the medications.
   *
   * @param aMedications the medications to set
   */
  public void setMedications(Boolean aMedications) {
    medications = aMedications;
  }
  /** @return the sddMedications */
  public Boolean getSddMedications() {
    return sddMedications;
  }
  /**
   * Sets the sddMedications.
   *
   * @param aSddMedications the sddMedications to set
   */
  public void setSddMedications(Boolean aSddMedications) {
    sddMedications = aSddMedications;
  }
  /** @return the pdfExtract */
  public Boolean getPdfExtract() {
    return pdfExtract;
  }
  /**
   * Sets the pdfExtract.
   *
   * @param aPdfExtract the pdfExtract to set
   */
  public void setPdfExtract(Boolean aPdfExtract) {
    pdfExtract = aPdfExtract;
  }
  /** @return the fromDate */
  public String getFromDate() {
    return fromDate;
  }
  /**
   * Sets the fromDate.
   *
   * @param aFromDate the fromDate to set
   */
  public void setFromDate(String aFromDate) {
    fromDate = aFromDate;
  }
  /** @return the toDate */
  public String getToDate() {
    return toDate;
  }
  /**
   * Sets the toDate.
   *
   * @param aToDate the toDate to set
   */
  public void setToDate(String aToDate) {
    toDate = aToDate;
  }
  /** @return the folderPath */
  public String getFolderPath() {
    return folderPath;
  }
  /**
   * Sets the folderPath.
   *
   * @param aFolderPath the folderPath to set
   */
  public void setFolderPath(String aFolderPath) {
    folderPath = aFolderPath;
  }
  /** @return the tasks */
  public Boolean getTasks() {
    return tasks;
  }
  /**
   * Sets the tasks.
   *
   * @param aTasks the tasks to set
   */
  public void setTasks(Boolean aTasks) {
    tasks = aTasks;
  }
  /** @return the progressNotes */
  public Boolean getProgressNotes() {
    return progressNotes;
  }
  /**
   * Sets the progressNotes.
   *
   * @param aProgressNotes the progressNotes to set
   */
  public void setProgressNotes(Boolean aProgressNotes) {
    progressNotes = aProgressNotes;
  }
  /** @return the adverseReaction */
  public Boolean getAdverseReaction() {
    return adverseReaction;
  }
  /**
   * Sets the adverseReaction.
   *
   * @param aAdverseReaction the adverseReaction to set
   */
  public void setAdverseReaction(Boolean aAdverseReaction) {
    adverseReaction = aAdverseReaction;
  }
  /** @return the excludeUnadmittedResidentsFlag */
  public Boolean getExcludeUnadmittedResidentsFlag() {
    return excludeUnadmittedResidentsFlag;
  }
  /**
   * Sets the excludeUnadmittedResidentsFlag.
   *
   * @param aExcludeUnadmittedResidentsFlag the excludeUnadmittedResidentsFlag to set
   */
  public void setExcludeUnadmittedResidentsFlag(Boolean aExcludeUnadmittedResidentsFlag) {
    excludeUnadmittedResidentsFlag = aExcludeUnadmittedResidentsFlag;
  }
  /** @return the excludeArchivedResidentsFlag */
  public Boolean getExcludeArchivedResidentsFlag() {
    return excludeArchivedResidentsFlag;
  }
  /**
   * Sets the excludeArchivedResidentsFlag.
   *
   * @param aExcludeArchivedResidentsFlag the excludeArchivedResidentsFlag to set
   */
  public void setExcludeArchivedResidentsFlag(Boolean aExcludeArchivedResidentsFlag) {
    excludeArchivedResidentsFlag = aExcludeArchivedResidentsFlag;
  }
  /** @return the excludeReservedResidentsFlag */
  public Boolean getExcludeReservedResidentsFlag() {
    return excludeReservedResidentsFlag;
  }
  /**
   * Sets the excludeReservedResidentsFlag.
   *
   * @param aExcludeReservedResidentsFlag the excludeReservedResidentsFlag to set
   */
  public void setExcludeReservedResidentsFlag(Boolean aExcludeReservedResidentsFlag) {
    excludeReservedResidentsFlag = aExcludeReservedResidentsFlag;
  }
  /** @return the unReadOnly */
  public Boolean getUnReadOnly() {
    return unReadOnly;
  }
  /**
   * Sets the unReadOnly.
   *
   * @param aUnReadOnly the unReadOnly to set
   */
  public void setUnReadOnly(Boolean aUnReadOnly) {
    unReadOnly = aUnReadOnly;
  }
  /** @return the configProperties */
  public ConfigProperties getConfigProperties() {
    return configProperties;
  }
  /**
   * Sets the configProperties.
   *
   * @param aConfigProperties the configProperties to set
   */
  public void setConfigProperties(ConfigProperties aConfigProperties) {
    configProperties = aConfigProperties;
  }
}

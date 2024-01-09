/*
 * MedicationData.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.Date;

/**
 * This is used for a MedicationData.
 *
 * @author jjoy
 */
public class MedicationData {
  private Long id;
  private Long medicationID;
  private Boolean active;
  private String genericName;
  private String brandName;
  private String summaryPackDetails;
  private String packDetails;
  private Long imageID;
  private Long monographID;
  private Boolean controlledDrug;
  private Boolean restriction;
  private Integer maximumDoses;
  private Integer maximumRepeats;
  private Boolean nurseInitiated;
  private Long categoryID;
  private String categoryName;
  private String prnMedicationForm;
  private Double prnDoseAmount;
  private Double prnDoseWeight;
  private String prnDoseUnitOfMeasure;
  private String prnRoute;
  private Long prnFrequencySettingID;
  private Integer prnTimesPerDay;
  private String prnFrequency;
  private Integer prnCycleSize;
  private String prnCycleUnit;
  private Integer prnTimeBetweenDosesSize;
  private String prnTimeBetweenDosesUnit;
  private Integer prnDurationSize;
  private String prnDurationUnit;
  private String regularMedicationForm;
  private Double regularDoseAmount;
  private Double regularDoseWeight;
  private String regularDoseUnitOfMeasure;
  private String regularRoute;
  private Long regularFrequencySettingID;
  private Integer regularTimesPerDay;
  private String regularFrequency;
  private Integer regularCycleSize;
  private String regularCycleUnit;
  private Integer regularDurationSize;
  private String regularDurationUnit;
  private Integer sdlType;
  private String sddCode;
  private String sddVersion;
  private String sddDescription;
  private String sddLevel;
  private String sddConceptID;
  private String regulatoryClassification;
  private String createdByUserDisplayName;
  private Date createdDateTime;
  private String prnComments;
  private String regularComments;
  /** @return the id */
  public Long getId() {
    return id;
  }
  /**
   * Sets the id.
   *
   * @param aId the id to set
   */
  public void setId(Long aId) {
    id = aId;
  }
  /** @return the medicationID */
  public Long getMedicationID() {
    return medicationID;
  }
  /**
   * Sets the medicationID.
   *
   * @param aMedicationID the medicationID to set
   */
  public void setMedicationID(Long aMedicationID) {
    medicationID = aMedicationID;
  }
  /** @return the active */
  public Boolean getActive() {
    return active;
  }
  /**
   * Sets the active.
   *
   * @param aActive the active to set
   */
  public void setActive(Boolean aActive) {
    active = aActive;
  }
  /** @return the genericName */
  public String getGenericName() {
    return genericName;
  }
  /**
   * Sets the genericName.
   *
   * @param aGenericName the genericName to set
   */
  public void setGenericName(String aGenericName) {
    genericName = aGenericName;
  }
  /** @return the brandName */
  public String getBrandName() {
    return brandName;
  }
  /**
   * Sets the brandName.
   *
   * @param aBrandName the brandName to set
   */
  public void setBrandName(String aBrandName) {
    brandName = aBrandName;
  }
  /** @return the summaryPackDetails */
  public String getSummaryPackDetails() {
    return summaryPackDetails;
  }
  /**
   * Sets the summaryPackDetails.
   *
   * @param aSummaryPackDetails the summaryPackDetails to set
   */
  public void setSummaryPackDetails(String aSummaryPackDetails) {
    summaryPackDetails = aSummaryPackDetails;
  }
  /** @return the packDetails */
  public String getPackDetails() {
    return packDetails;
  }
  /**
   * Sets the packDetails.
   *
   * @param aPackDetails the packDetails to set
   */
  public void setPackDetails(String aPackDetails) {
    packDetails = aPackDetails;
  }
  /** @return the imageID */
  public Long getImageID() {
    return imageID;
  }
  /**
   * Sets the imageID.
   *
   * @param aImageID the imageID to set
   */
  public void setImageID(Long aImageID) {
    imageID = aImageID;
  }
  /** @return the monographID */
  public Long getMonographID() {
    return monographID;
  }
  /**
   * Sets the monographID.
   *
   * @param aMonographID the monographID to set
   */
  public void setMonographID(Long aMonographID) {
    monographID = aMonographID;
  }
  /** @return the controlledDrug */
  public Boolean getControlledDrug() {
    return controlledDrug;
  }
  /**
   * Sets the controlledDrug.
   *
   * @param aControlledDrug the controlledDrug to set
   */
  public void setControlledDrug(Boolean aControlledDrug) {
    controlledDrug = aControlledDrug;
  }
  /** @return the restriction */
  public Boolean getRestriction() {
    return restriction;
  }
  /**
   * Sets the restriction.
   *
   * @param aRestriction the restriction to set
   */
  public void setRestriction(Boolean aRestriction) {
    restriction = aRestriction;
  }
  /** @return the maximumDoses */
  public Integer getMaximumDoses() {
    return maximumDoses;
  }
  /**
   * Sets the maximumDoses.
   *
   * @param aMaximumDoses the maximumDoses to set
   */
  public void setMaximumDoses(Integer aMaximumDoses) {
    maximumDoses = aMaximumDoses;
  }
  /** @return the maximumRepeats */
  public Integer getMaximumRepeats() {
    return maximumRepeats;
  }
  /**
   * Sets the maximumRepeats.
   *
   * @param aMaximumRepeats the maximumRepeats to set
   */
  public void setMaximumRepeats(Integer aMaximumRepeats) {
    maximumRepeats = aMaximumRepeats;
  }
  /** @return the nurseInitiated */
  public Boolean getNurseInitiated() {
    return nurseInitiated;
  }
  /**
   * Sets the nurseInitiated.
   *
   * @param aNurseInitiated the nurseInitiated to set
   */
  public void setNurseInitiated(Boolean aNurseInitiated) {
    nurseInitiated = aNurseInitiated;
  }
  /** @return the categoryID */
  public Long getCategoryID() {
    return categoryID;
  }
  /**
   * Sets the categoryID.
   *
   * @param aCategoryID the categoryID to set
   */
  public void setCategoryID(Long aCategoryID) {
    categoryID = aCategoryID;
  }
  /** @return the categoryName */
  public String getCategoryName() {
    return categoryName;
  }
  /**
   * Sets the categoryName.
   *
   * @param aCategoryName the categoryName to set
   */
  public void setCategoryName(String aCategoryName) {
    categoryName = aCategoryName;
  }
  /** @return the prnMedicationForm */
  public String getPrnMedicationForm() {
    return prnMedicationForm;
  }
  /**
   * Sets the prnMedicationForm.
   *
   * @param aPrnMedicationForm the prnMedicationForm to set
   */
  public void setPrnMedicationForm(String aPrnMedicationForm) {
    prnMedicationForm = aPrnMedicationForm;
  }
  /** @return the prnDoseAmount */
  public Double getPrnDoseAmount() {
    return prnDoseAmount;
  }
  /**
   * Sets the prnDoseAmount.
   *
   * @param aPrnDoseAmount the prnDoseAmount to set
   */
  public void setPrnDoseAmount(Double aPrnDoseAmount) {
    prnDoseAmount = aPrnDoseAmount;
  }
  /** @return the prnDoseWeight */
  public Double getPrnDoseWeight() {
    return prnDoseWeight;
  }
  /**
   * Sets the prnDoseWeight.
   *
   * @param aPrnDoseWeight the prnDoseWeight to set
   */
  public void setPrnDoseWeight(Double aPrnDoseWeight) {
    prnDoseWeight = aPrnDoseWeight;
  }
  /** @return the prnDoseUnitOfMeasure */
  public String getPrnDoseUnitOfMeasure() {
    return prnDoseUnitOfMeasure;
  }
  /**
   * Sets the prnDoseUnitOfMeasure.
   *
   * @param aPrnDoseUnitOfMeasure the prnDoseUnitOfMeasure to set
   */
  public void setPrnDoseUnitOfMeasure(String aPrnDoseUnitOfMeasure) {
    prnDoseUnitOfMeasure = aPrnDoseUnitOfMeasure;
  }
  /** @return the prnRoute */
  public String getPrnRoute() {
    return prnRoute;
  }
  /**
   * Sets the prnRoute.
   *
   * @param aPrnRoute the prnRoute to set
   */
  public void setPrnRoute(String aPrnRoute) {
    prnRoute = aPrnRoute;
  }
  /** @return the prnFrequencySettingID */
  public Long getPrnFrequencySettingID() {
    return prnFrequencySettingID;
  }
  /**
   * Sets the prnFrequencySettingID.
   *
   * @param aPrnFrequencySettingID the prnFrequencySettingID to set
   */
  public void setPrnFrequencySettingID(Long aPrnFrequencySettingID) {
    prnFrequencySettingID = aPrnFrequencySettingID;
  }
  /** @return the prnTimesPerDay */
  public Integer getPrnTimesPerDay() {
    return prnTimesPerDay;
  }
  /**
   * Sets the prnTimesPerDay.
   *
   * @param aPrnTimesPerDay the prnTimesPerDay to set
   */
  public void setPrnTimesPerDay(Integer aPrnTimesPerDay) {
    prnTimesPerDay = aPrnTimesPerDay;
  }
  /** @return the prnFrequency */
  public String getPrnFrequency() {
    return prnFrequency;
  }
  /**
   * Sets the prnFrequency.
   *
   * @param aPrnFrequency the prnFrequency to set
   */
  public void setPrnFrequency(String aPrnFrequency) {
    prnFrequency = aPrnFrequency;
  }
  /** @return the prnCycleSize */
  public Integer getPrnCycleSize() {
    return prnCycleSize;
  }
  /**
   * Sets the prnCycleSize.
   *
   * @param aPrnCycleSize the prnCycleSize to set
   */
  public void setPrnCycleSize(Integer aPrnCycleSize) {
    prnCycleSize = aPrnCycleSize;
  }
  /** @return the prnCycleUnit */
  public String getPrnCycleUnit() {
    return prnCycleUnit;
  }
  /**
   * Sets the prnCycleUnit.
   *
   * @param aPrnCycleUnit the prnCycleUnit to set
   */
  public void setPrnCycleUnit(String aPrnCycleUnit) {
    prnCycleUnit = aPrnCycleUnit;
  }
  /** @return the prnTimeBetweenDosesSize */
  public Integer getPrnTimeBetweenDosesSize() {
    return prnTimeBetweenDosesSize;
  }
  /**
   * Sets the prnTimeBetweenDosesSize.
   *
   * @param aPrnTimeBetweenDosesSize the prnTimeBetweenDosesSize to set
   */
  public void setPrnTimeBetweenDosesSize(Integer aPrnTimeBetweenDosesSize) {
    prnTimeBetweenDosesSize = aPrnTimeBetweenDosesSize;
  }
  /** @return the prnTimeBetweenDosesUnit */
  public String getPrnTimeBetweenDosesUnit() {
    return prnTimeBetweenDosesUnit;
  }
  /**
   * Sets the prnTimeBetweenDosesUnit.
   *
   * @param aPrnTimeBetweenDosesUnit the prnTimeBetweenDosesUnit to set
   */
  public void setPrnTimeBetweenDosesUnit(String aPrnTimeBetweenDosesUnit) {
    prnTimeBetweenDosesUnit = aPrnTimeBetweenDosesUnit;
  }
  /** @return the prnDurationSize */
  public Integer getPrnDurationSize() {
    return prnDurationSize;
  }
  /**
   * Sets the prnDurationSize.
   *
   * @param aPrnDurationSize the prnDurationSize to set
   */
  public void setPrnDurationSize(Integer aPrnDurationSize) {
    prnDurationSize = aPrnDurationSize;
  }
  /** @return the prnDurationUnit */
  public String getPrnDurationUnit() {
    return prnDurationUnit;
  }
  /**
   * Sets the prnDurationUnit.
   *
   * @param aPrnDurationUnit the prnDurationUnit to set
   */
  public void setPrnDurationUnit(String aPrnDurationUnit) {
    prnDurationUnit = aPrnDurationUnit;
  }
  /** @return the regularMedicationForm */
  public String getRegularMedicationForm() {
    return regularMedicationForm;
  }
  /**
   * Sets the regularMedicationForm.
   *
   * @param aRegularMedicationForm the regularMedicationForm to set
   */
  public void setRegularMedicationForm(String aRegularMedicationForm) {
    regularMedicationForm = aRegularMedicationForm;
  }
  /** @return the regularDoseAmount */
  public Double getRegularDoseAmount() {
    return regularDoseAmount;
  }
  /**
   * Sets the regularDoseAmount.
   *
   * @param aRegularDoseAmount the regularDoseAmount to set
   */
  public void setRegularDoseAmount(Double aRegularDoseAmount) {
    regularDoseAmount = aRegularDoseAmount;
  }
  /** @return the regularDoseWeight */
  public Double getRegularDoseWeight() {
    return regularDoseWeight;
  }
  /**
   * Sets the regularDoseWeight.
   *
   * @param aRegularDoseWeight the regularDoseWeight to set
   */
  public void setRegularDoseWeight(Double aRegularDoseWeight) {
    regularDoseWeight = aRegularDoseWeight;
  }
  /** @return the regularDoseUnitOfMeasure */
  public String getRegularDoseUnitOfMeasure() {
    return regularDoseUnitOfMeasure;
  }
  /**
   * Sets the regularDoseUnitOfMeasure.
   *
   * @param aRegularDoseUnitOfMeasure the regularDoseUnitOfMeasure to set
   */
  public void setRegularDoseUnitOfMeasure(String aRegularDoseUnitOfMeasure) {
    regularDoseUnitOfMeasure = aRegularDoseUnitOfMeasure;
  }
  /** @return the regularRoute */
  public String getRegularRoute() {
    return regularRoute;
  }
  /**
   * Sets the regularRoute.
   *
   * @param aRegularRoute the regularRoute to set
   */
  public void setRegularRoute(String aRegularRoute) {
    regularRoute = aRegularRoute;
  }
  /** @return the regularFrequencySettingID */
  public Long getRegularFrequencySettingID() {
    return regularFrequencySettingID;
  }
  /**
   * Sets the regularFrequencySettingID.
   *
   * @param aRegularFrequencySettingID the regularFrequencySettingID to set
   */
  public void setRegularFrequencySettingID(Long aRegularFrequencySettingID) {
    regularFrequencySettingID = aRegularFrequencySettingID;
  }
  /** @return the regularTimesPerDay */
  public Integer getRegularTimesPerDay() {
    return regularTimesPerDay;
  }
  /**
   * Sets the regularTimesPerDay.
   *
   * @param aRegularTimesPerDay the regularTimesPerDay to set
   */
  public void setRegularTimesPerDay(Integer aRegularTimesPerDay) {
    regularTimesPerDay = aRegularTimesPerDay;
  }
  /** @return the regularFrequency */
  public String getRegularFrequency() {
    return regularFrequency;
  }
  /**
   * Sets the regularFrequency.
   *
   * @param aRegularFrequency the regularFrequency to set
   */
  public void setRegularFrequency(String aRegularFrequency) {
    regularFrequency = aRegularFrequency;
  }
  /** @return the regularCycleSize */
  public Integer getRegularCycleSize() {
    return regularCycleSize;
  }
  /**
   * Sets the regularCycleSize.
   *
   * @param aRegularCycleSize the regularCycleSize to set
   */
  public void setRegularCycleSize(Integer aRegularCycleSize) {
    regularCycleSize = aRegularCycleSize;
  }
  /** @return the regularCycleUnit */
  public String getRegularCycleUnit() {
    return regularCycleUnit;
  }
  /**
   * Sets the regularCycleUnit.
   *
   * @param aRegularCycleUnit the regularCycleUnit to set
   */
  public void setRegularCycleUnit(String aRegularCycleUnit) {
    regularCycleUnit = aRegularCycleUnit;
  }
  /** @return the regularDurationSize */
  public Integer getRegularDurationSize() {
    return regularDurationSize;
  }
  /**
   * Sets the regularDurationSize.
   *
   * @param aRegularDurationSize the regularDurationSize to set
   */
  public void setRegularDurationSize(Integer aRegularDurationSize) {
    regularDurationSize = aRegularDurationSize;
  }
  /** @return the regularDurationUnit */
  public String getRegularDurationUnit() {
    return regularDurationUnit;
  }
  /**
   * Sets the regularDurationUnit.
   *
   * @param aRegularDurationUnit the regularDurationUnit to set
   */
  public void setRegularDurationUnit(String aRegularDurationUnit) {
    regularDurationUnit = aRegularDurationUnit;
  }
  /** @return the sdlType */
  public Integer getSdlType() {
    return sdlType;
  }
  /**
   * Sets the sdlType.
   *
   * @param aSdlType the sdlType to set
   */
  public void setSdlType(Integer aSdlType) {
    sdlType = aSdlType;
  }
  /** @return the sddCode */
  public String getSddCode() {
    return sddCode;
  }
  /**
   * Sets the sddCode.
   *
   * @param aSddCode the sddCode to set
   */
  public void setSddCode(String aSddCode) {
    sddCode = aSddCode;
  }
  /** @return the sddVersion */
  public String getSddVersion() {
    return sddVersion;
  }
  /**
   * Sets the sddVersion.
   *
   * @param aSddVersion the sddVersion to set
   */
  public void setSddVersion(String aSddVersion) {
    sddVersion = aSddVersion;
  }
  /** @return the sddDescription */
  public String getSddDescription() {
    return sddDescription;
  }
  /**
   * Sets the sddDescription.
   *
   * @param aSddDescription the sddDescription to set
   */
  public void setSddDescription(String aSddDescription) {
    sddDescription = aSddDescription;
  }
  /** @return the sddLevel */
  public String getSddLevel() {
    return sddLevel;
  }
  /**
   * Sets the sddLevel.
   *
   * @param aSddLevel the sddLevel to set
   */
  public void setSddLevel(String aSddLevel) {
    sddLevel = aSddLevel;
  }
  /** @return the sddConceptID */
  public String getSddConceptID() {
    return sddConceptID;
  }
  /**
   * Sets the sddConceptID.
   *
   * @param aSddConceptID the sddConceptID to set
   */
  public void setSddConceptID(String aSddConceptID) {
    sddConceptID = aSddConceptID;
  }
  /** @return the regulatoryClassification */
  public String getRegulatoryClassification() {
    return regulatoryClassification;
  }
  /**
   * Sets the regulatoryClassification.
   *
   * @param aRegulatoryClassification the regulatoryClassification to set
   */
  public void setRegulatoryClassification(String aRegulatoryClassification) {
    regulatoryClassification = aRegulatoryClassification;
  }
  /** @return the createdByUserDisplayName */
  public String getCreatedByUserDisplayName() {
    return createdByUserDisplayName;
  }
  /**
   * Sets the createdByUserDisplayName.
   *
   * @param aCreatedByUserDisplayName the createdByUserDisplayName to set
   */
  public void setCreatedByUserDisplayName(String aCreatedByUserDisplayName) {
    createdByUserDisplayName = aCreatedByUserDisplayName;
  }
  /** @return the createdDateTime */
  public Date getCreatedDateTime() {
    return createdDateTime;
  }
  /**
   * Sets the createdDateTime.
   *
   * @param aCreatedDateTime the createdDateTime to set
   */
  public void setCreatedDateTime(Date aCreatedDateTime) {
    createdDateTime = aCreatedDateTime;
  }
  /** @return the prnComments */
  public String getPrnComments() {
    return prnComments;
  }
  /**
   * Sets the prnComments.
   *
   * @param aPrnComments the prnComments to set
   */
  public void setPrnComments(String aPrnComments) {
    prnComments = aPrnComments;
  }
  /** @return the regularComments */
  public String getRegularComments() {
    return regularComments;
  }
  /**
   * Sets the regularComments.
   *
   * @param aRegularComments the regularComments to set
   */
  public void setRegularComments(String aRegularComments) {
    regularComments = aRegularComments;
  }
}

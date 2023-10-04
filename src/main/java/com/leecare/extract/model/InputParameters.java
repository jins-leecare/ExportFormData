package com.leecare.extract.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

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
    private Boolean pdfExtract = false;
    private String fromDate;
    private String toDate;
    private String folderPath;
    private Boolean tasks = false;
    private Boolean progressNotes = false;
    private Boolean adverseReaction = false;
    private Boolean excludeUnadmittedResidentsFlag = false;
    private Boolean excludeArchivedResidentsFlag =false;
    private Boolean excludeReservedResidentsFlag = false;
    private Boolean unReadOnly = false;
    private ConfigProperties configProperties;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public Boolean getRegularForm() {
        return regularForm;
    }

    public void setRegularForm(Boolean regularForm) {
        this.regularForm = regularForm;
    }

    public Boolean getSubForm() {
        return subForm;
    }

    public void setSubForm(Boolean subForm) {
        this.subForm = subForm;
    }

    public Boolean getGridForm() {
        return gridForm;
    }

    public void setGridForm(Boolean gridForm) {
        this.gridForm = gridForm;
    }

    public Boolean getCustomGridForm() {
        return customGridForm;
    }

    public void setCustomGridForm(Boolean customGridForm) {
        this.customGridForm = customGridForm;
    }

    public Boolean getBedMovement() {
        return bedMovement;
    }

    public void setBedMovement(Boolean bedMovement) {
        this.bedMovement = bedMovement;
    }

    public Boolean getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Boolean prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Boolean getMedications() {
        return medications;
    }

    public void setMedications(Boolean medications) {
        this.medications = medications;
    }

    public Boolean getDownloadAttachments() {
        return downloadAttachments;
    }

    public void setDownloadAttachments(Boolean downloadAttachments) {
        this.downloadAttachments = downloadAttachments;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Boolean getTasks() {
        return tasks;
    }

    public void setTasks(Boolean tasks) {
        this.tasks = tasks;
    }

    public Boolean getProgressNotes() {
        return progressNotes;
    }
    public void setProgressNotes(Boolean progressNotes) {
        this.progressNotes = progressNotes;
    }
    public Boolean getAdverseReaction() {
        return adverseReaction;
    }
    public void setAdverseReaction(Boolean adverseReaction) {
        this.adverseReaction = adverseReaction;
    }
    public Boolean getPdfExtract() {
        return pdfExtract;
    }

    public void setPdfExtract(Boolean pdfExtract) {
        this.pdfExtract = pdfExtract;
    }

    public Boolean getExcludeUnadmittedResidentsFlag() {
        return excludeUnadmittedResidentsFlag;
    }

    public void setExcludeUnadmittedResidentsFlag(Boolean excludeUnadmittedResidentsFlag) {
        this.excludeUnadmittedResidentsFlag = excludeUnadmittedResidentsFlag;
    }

    public Boolean getExcludeArchivedResidentsFlag() {
        return excludeArchivedResidentsFlag;
    }

    public void setExcludeArchivedResidentsFlag(Boolean excludeArchivedResidentsFlag) {
        this.excludeArchivedResidentsFlag = excludeArchivedResidentsFlag;
    }

    public Boolean getExcludeReservedResidentsFlag() {
        return excludeReservedResidentsFlag;
    }

    public void setExcludeReservedResidentsFlag(Boolean excludeReservedResidentsFlag) {
        this.excludeReservedResidentsFlag = excludeReservedResidentsFlag;
    }

    public Boolean getUnReadOnly() {
        return unReadOnly;
    }

    public void setUnReadOnly(Boolean unReadOnly) {
        this.unReadOnly = unReadOnly;
    }

    public ConfigProperties getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

}

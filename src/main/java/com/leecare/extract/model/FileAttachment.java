package com.leecare.extract.model;

import java.sql.Blob;

public class FileAttachment {
        private Integer residentID;
        private Integer formID;
        private Integer recordID;
        private Integer fileBatchID;
        private Integer schemaID;
        private String fieldName;
        private Integer fileID;
        private String fileName;
        private String mimeType;
        private String title;
        private String fileData;
        private String date;

        /**
         * @return the residentID
         */
        public Integer getResidentID() {
            return residentID;
        }

        /**
         * Sets the residentID.
         *
         * @param aResidentID the residentID to set
         */
        public void setResidentID(Integer aResidentID) {
            residentID = aResidentID;
        }

        /**
         * @return the recordID
         */
        public Integer getRecordID() {
            return recordID;
        }

        /**
         * Sets the recordID.
         *
         * @param aRecordID the recordID to set
         */
        public void setRecordID(Integer aRecordID) {
            recordID = aRecordID;
        }

        /**
         * @return the formID
         */
        public Integer getFormID() {
            return formID;
        }

        /**
         * Sets the formID.
         *
         * @param aFormID the formID to set
         */
        public void setFormID(Integer aFormID) {
            formID = aFormID;
        }

        /**
         * @return the fileBatchID
         */
        public Integer getFileBatchID() {
            return fileBatchID;
        }

        /**
         * Sets the fileBatchID.
         *
         * @param aFileBatchID the fileBatchID to set
         */
        public void setFileBatchID(Integer aFileBatchID) {
            fileBatchID = aFileBatchID;
        }

        /**
         * @return the schemaID
         */
        public Integer getSchemaID() {
            return schemaID;
        }

        /**
         * Sets the schemaID.
         *
         * @param aSchemaID the schemaID to set
         */
        public void setSchemaID(Integer aSchemaID) {
            schemaID = aSchemaID;
        }

        /**
         * @return the fieldName
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Sets the fieldName.
         *
         * @param aFieldName the fieldName to set
         */
        public void setFieldName(String aFieldName) {
            fieldName = aFieldName;
        }

        /**
         * @return the fileID
         */
        public Integer getFileID() {
            return fileID;
        }

        /**
         * Sets the fileID.
         *
         * @param aFileID the fileID to set
         */
        public void setFileID(Integer aFileID) {
            fileID = aFileID;
        }

        /**
         * @return the fileName
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Sets the fileName.
         *
         * @param aFileName the fileName to set
         */
        public void setFileName(String aFileName) {
            fileName = aFileName;
        }

        /**
         * @return the mimeType
         */
        public String getMimeType() {
            return mimeType;
        }

        /**
         * Sets the mimeType.
         *
         * @param aMimeType the mimeType to set
         */
        public void setMimeType(String aMimeType) {
            mimeType = aMimeType;
        }

        /**
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the title.
         *
         * @param aTitle the title to set
         */
        public void setTitle(String aTitle) {
            title = aTitle;
        }

        /**
         * @return
         */
        public String getFileData() {
            return fileData;
        }

        /**
         * @param aFileData
         */
        public void setFileData(String aFileData) {
            fileData = aFileData;
        }

        /**
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the date.
         *
         * @param aDate the date to set
         */
        public void setDate(String aDate) {
            date = aDate;
        }

}


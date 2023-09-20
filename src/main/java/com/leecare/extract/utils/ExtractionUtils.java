package com.leecare.extract.utils;

import com.leecare.extract.model.ConfigProperties;
import com.leecare.extract.model.InputParameters;

import java.io.FileInputStream;
import java.util.Properties;

public class ExtractionUtils {

    public static InputParameters parseCommandLineArgs(String[] args) {
        InputParameters params = new InputParameters();
        for (String arg : args) {
            String[] keyValue = arg.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                if ("-formName".equals(key)) {
                    params.setFormName(value);
                } else if ("-facilityId".equals(key)) {
                    params.setFacilityId(value);
                } else if ("-configFile".equals(key)) {
                    params.setConfigFile(value);
                } else if ("-regularForm".equals(key)) {
                    params.setRegularForm(Boolean.valueOf(value));
                } else if ("-subForm".equals(key)) {
                    params.setSubForm(Boolean.valueOf(value));
                } else if ("-gridForm".equals(key)) {
                    params.setGridForm(Boolean.valueOf(value));
                } else if ("-customGridForm".equals(key)) {
                    params.setCustomGridForm(Boolean.valueOf(value));
                } else if ("-downloadAttachments".equals(key)) {
                    params.setDownloadAttachments(Boolean.valueOf(value));
                } else if ("-bedMovement".equals(key)) {
                    params.setBedMovement(Boolean.valueOf(value));
                } else if ("-prescriptions".equals(key)) {
                    params.setPrescriptions(Boolean.valueOf(value));
                } else if ("-medications".equals(key)) {
                    params.setMedications(Boolean.valueOf(value));
                } else if ("-fromDate".equals(key)) {
                    params.setFromDate(value);
                } else if ("-toDate".equals(key)) {
                    params.setToDate(value);
                } else if ("-tasks".equals(key)) {
                    params.setTasks(Boolean.valueOf(value));
                } else if ("-progressNotes".equals(key)) {
                    params.setProgressNotes(Boolean.valueOf(value));
                }else {
                    System.out.println("Unknown params. Please check usage");
                }
            }
        }
        return params;
    }

    public static ConfigProperties readConfigFile(String configFile) {
        ConfigProperties configProperties = new ConfigProperties();

        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            configProperties.setFilePath(properties.getProperty("env.filePath"));
            configProperties.setUrl(properties.getProperty("env.url"));
            configProperties.setUserName(properties.getProperty("env.username"));
            configProperties.setPassWord(properties.getProperty("env.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configProperties;
    }
}

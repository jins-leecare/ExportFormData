package com.leecare.extract.model;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigProperties {

    private static String url = "";
    private static String userName = "";
    private static String passWord = "";
    private static String filePath = "";

    public static String getUrl() {
        return url;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static void setUrl(String url) {
        ConfigProperties.url = url;
    }

    public static void setUserName(String userName) {
        ConfigProperties.userName = userName;
    }

    public static void setPassWord(String passWord) {
        ConfigProperties.passWord = passWord;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        ConfigProperties.filePath = filePath;
    }

    public static void readConfigFile(String configFile) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            setFilePath(properties.getProperty("env.filePath"));
            setUrl(properties.getProperty("env.url"));
            setUserName(properties.getProperty("env.username"));
            setPassWord(properties.getProperty("env.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

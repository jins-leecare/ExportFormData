/*
 * ConfigProperties.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

/**
 * This is used for a ConfigProperties.
 *
 * @author jjoy
 */
public class ConfigProperties {

  private String url;
  private String userName;
  private String passWord;
  private String filePath;
  /** @return the url */
  public String getUrl() {
    return url;
  }
  /**
   * Sets the url.
   *
   * @param aUrl the url to set
   */
  public void setUrl(String aUrl) {
    url = aUrl;
  }
  /** @return the userName */
  public String getUserName() {
    return userName;
  }
  /**
   * Sets the userName.
   *
   * @param aUserName the userName to set
   */
  public void setUserName(String aUserName) {
    userName = aUserName;
  }
  /** @return the passWord */
  public String getPassWord() {
    return passWord;
  }
  /**
   * Sets the passWord.
   *
   * @param aPassWord the passWord to set
   */
  public void setPassWord(String aPassWord) {
    passWord = aPassWord;
  }
  /** @return the filePath */
  public String getFilePath() {
    return filePath;
  }
  /**
   * Sets the filePath.
   *
   * @param aFilePath the filePath to set
   */
  public void setFilePath(String aFilePath) {
    filePath = aFilePath;
  }
}

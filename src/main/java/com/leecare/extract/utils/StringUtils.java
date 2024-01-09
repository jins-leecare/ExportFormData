/*
 * StringUtils.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.utils;

/**
 * This is used to do common things with Strings.
 *
 * @author Jins Joy
 */
public class StringUtils {

  /**
   * Checks if is not blank.
   *
   * @param aValue the value
   * @return true, if is not blank
   */
  public static boolean isNotBlank(String aValue) {
    return aValue != null && !aValue.isEmpty();
  }

  /**
   * Checks if is blank.
   *
   * @param aValue the value
   * @return true, if is blank
   */
  public static boolean isBlank(String aValue) {
    return aValue == null || aValue.isEmpty();
  }

  /**
   * Default if empty.
   *
   * @param aValue the value
   * @param aDefault the default
   * @return the string
   */
  public static String defaultIfEmpty(String aValue, String aDefault) {
    return aValue == null || aValue.isEmpty() ? aDefault : aValue;
  }

  /**
   * Return the value when it is not null, or an empty string when it is null.
   *
   * @param aValue a value (can be null)
   * @return the value when it is not null, or an empty string when it is null (not null)
   */
  public static String nullToEmpty(String aValue) {
    return aValue == null ? "" : aValue;
  }

  /**
   * @param aString string to get required value
   * @param aLength length of the required string
   * @return aString value substring if aString is greater than length
   */
  public static String getRequiredValue(String aString, int aLength) {
    if (aString != null && aString.length() > aLength) {
      return aString.substring(0, aLength);
    }
    return aString;
  }

  /**
   * Trim a given string.
   *
   * @param aValue the value
   * @return the string
   */
  public static String trim(String aValue) {
    return aValue == null ? null : aValue.trim();
  }

  /**
   * Checking if String is null or empty
   *
   * @param aValue the string to be checked
   * @return true if the string is null or empty otherwise false
   */
  public static boolean isNullOrEmpty(String aValue) {
    return aValue == null || aValue.trim().isEmpty();
  }
  
  /**
   * Return true if a value is not null and is not empty.
   *
   * @param aValue a value (can be null)
   * @return true if a value is not null and is not empty
   */
  public static boolean isNotEmpty(String aValue) {
      return aValue != null && !aValue.isEmpty();
  }
}

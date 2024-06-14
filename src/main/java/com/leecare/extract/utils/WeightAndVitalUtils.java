/*
 * WeightAndVitalUtils.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.utils;

import com.leecare.extract.model.FieldValueDetails;

import java.util.Map;

/**
 * This is used for a WeightAndVitalUtils.
 *
 * @author jjoy
 */
public class WeightAndVitalUtils {

  //Neurological Observations
  public static String NEURO_OBS_EYE_RESP = "fld_neuro_obs_eye_resp";
  public static String NEURO_OBS_VERB_RESP = "fld_neuro_obs_verb_resp";
  public static String NEURO_OBS_MOTOR_RESP = "fld_neuro_obs_motor_resp";
  /**
   * Method to generate GCS score.
   *
   * @param aEyeOpening eye opening (can be null)
   * @param aVerbalResponse verbal response (can be null)
   * @param aMotorResponse motor response (can be null)
   * @return gcs score 
   */
  public static int generateGcsScore(
      String aEyeOpening, String aVerbalResponse, String aMotorResponse) {
    int gcsScore = 0;
    try {
      if (aEyeOpening != null) gcsScore += Integer.valueOf(aEyeOpening);
      if (aVerbalResponse != null) gcsScore += Integer.valueOf(aVerbalResponse);
      if (aMotorResponse != null && !aMotorResponse.equals(4))
        gcsScore += Integer.valueOf(aMotorResponse);
    } catch (Exception ex) {
    }
    return gcsScore;
  }
  /**
   * Method to get field value from hash.
   *
   * @param fieldValues field values (not null)
   * @param fieldName field name (not null)
   * @return field value.
   */
  public static Object getFieldValueFromHash(
      Map<String, FieldValueDetails> fieldValues, String fieldName) {
    if (fieldValues.containsKey(fieldName)) {
      return fieldValues.get(fieldName).getFieldValue();
    }
    return null;
  }
}

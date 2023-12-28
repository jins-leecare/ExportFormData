package com.leecare.extract.utils;

import com.leecare.extract.model.FieldValueDetails;

import java.util.Map;

public class WeightAndVitalUtils {

    //Neurological Observations
    public static String NEURO_OBS_EYE_RESP = "fld_neuro_obs_eye_resp";
    public static String NEURO_OBS_VERB_RESP = "fld_neuro_obs_verb_resp";
    public static String NEURO_OBS_MOTOR_RESP = "fld_neuro_obs_motor_resp";
    public static int generateGcsScore(
            String aEyeOpening, String aVerbalResponse, String aMotorResponse) {
        int gcsScore = 0;
        if (aEyeOpening != null) gcsScore += Integer.valueOf(aEyeOpening);
        if (aVerbalResponse != null) gcsScore += Integer.valueOf(aVerbalResponse);
        if (aMotorResponse != null && !aMotorResponse.equals(4))
            gcsScore += Integer.valueOf(aMotorResponse);
        return gcsScore;
    }
    public static Object getFieldValueFromHash(
            Map<String, FieldValueDetails> fieldValues, String fieldName) {
        if (fieldValues.containsKey(fieldName)) {
            return fieldValues.get(fieldName).getFieldValue();
        }
        return null;
    }
}

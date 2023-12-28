package com.leecare.extract.model;

import com.leecare.extract.utils.StringUtils;

import java.io.Serializable;

public class AdverseReaction implements Serializable {
    public static final int STRING_LENGTH_MAX = 500;

    private Long id;
    private int residentId;
    private String substance;
    private String substanceGroup;
    private String drugAllergy;
    private String drugAllergyReaction;
    private String foodAllergy;
    private String foodAllergyReaction;
    private String otherAllergy;
    private String otherAllergyReaction;
    private String allergyType;
    private String allergyOrSensitivity;
    private String reactionDescription;
    private String comment;
    private String isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdverseReaction() {
        //
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = StringUtils.getRequiredValue(substance, STRING_LENGTH_MAX);
    }

    /**
     * Gets the substance or agent group.
     *
     * @return the group.
     */
    public String getSubstanceGroup() {
        return substanceGroup;
    }

    /**
     * Sets the substance or agent group.
     *
     * @param aSubstanceGroup the group.
     */
    public void setSubstanceGroup(final String aSubstanceGroup) {
        substanceGroup = StringUtils.getRequiredValue(aSubstanceGroup, STRING_LENGTH_MAX);
    }

    public String getDrugAllergy() {
        return drugAllergy;
    }

    public void setDrugAllergy(String drugAllergy) {
        this.drugAllergy = drugAllergy;
    }

    public String getDrugAllergyReaction() {
        return drugAllergyReaction;
    }

    public void setDrugAllergyReaction(String drugAllergyReaction) {
        this.drugAllergyReaction = drugAllergyReaction;
    }

    public String getFoodAllergy() {
        return foodAllergy;
    }

    public void setFoodAllergy(String foodAllergy) {
        this.foodAllergy = foodAllergy;
    }

    public String getFoodAllergyReaction() {
        return foodAllergyReaction;
    }

    public void setFoodAlleryReaction(String foodAllergyReaction) {
        this.foodAllergyReaction = foodAllergyReaction;
    }

    public String getOtherAllergy() {
        return otherAllergy;
    }

    public void setOtherAllergy(String otherAllergy) {
        this.otherAllergy = otherAllergy;
    }

    public String getOtherAllergyReaction() {
        return otherAllergyReaction;
    }

    public void setOtherAllergyReaction(String otherAllergyReaction) {
        this.otherAllergyReaction = otherAllergyReaction;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    public String getAllergyOrSensitivity() {
        return allergyOrSensitivity;
    }

    public void setAllergyOrSensitivity(String allergyOrSensitivity) {
        this.allergyOrSensitivity = allergyOrSensitivity;
    }

    /** @return the reactionDescription */
    public String getReactionDescription() {
        return reactionDescription;
    }

    /** @param reactionDescription the reactionDescription to set */
    public void setReactionDescription(String reactionDescription) {
        this.reactionDescription = reactionDescription;
    }

    /** @return the comment */
    public String getComment() {
        return comment;
    }

    /** @param comment the comment to set */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the isActive
     */
    public String getIsActive() {
        return isActive;}

    /**
     * Sets the isActive.
     *
     * @param aIsActive the isActive to set
     */
    public void setIsActive(String aIsActive) {
        isActive = aIsActive;}
}

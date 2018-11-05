package com.brahamaputra.mahindra.brahamaputra.Data;

import com.google.gson.annotations.SerializedName;

public class ShelterData {

    @SerializedName("physicalCondition")
    String physicalCondition;

    @SerializedName("noOBtsInsideShelter")
    String noOfBtsInsideShelter;

    @SerializedName("noOfBtsOutsideShelter")
    String noOfBtsOutsideShelter;

    @SerializedName("shelterLock")
    String shelterLock;

    @SerializedName("outdoorShelterLock")
    String outdoorShelterLock;

    @SerializedName("igbStatus")
    String igbStatus;

    @SerializedName("egbStatus")
    String egbStatus;

    @SerializedName("noOfOdcAvailable")
    String noOfOdcAvailable;

    @SerializedName("odcLock")
    String odcLock;

    public ShelterData() {
        this.physicalCondition = "";
        this.noOfBtsInsideShelter = "";
        this.noOfBtsOutsideShelter = "";
        this.shelterLock = "";
        this.outdoorShelterLock = "";
        this.igbStatus = "";
        this.egbStatus = "";
        this.noOfOdcAvailable = "";
        this.odcLock = "";
    }

    public ShelterData(String physicalCondition, String noOfBtsInsideShelter, String noOfBtsOutsideShelter, String shelterLock, String outdoorShelterLock, String igbStatus, String egbStatus, String noOfOdcAvailable, String odcLock) {
        this.physicalCondition = physicalCondition;
        this.noOfBtsInsideShelter = noOfBtsInsideShelter;
        this.noOfBtsOutsideShelter = noOfBtsOutsideShelter;
        this.shelterLock = shelterLock;
        this.outdoorShelterLock = outdoorShelterLock;
        this.igbStatus = igbStatus;
        this.egbStatus = egbStatus;
        this.noOfOdcAvailable = noOfOdcAvailable;
        this.odcLock = odcLock;
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public String getNoOfBtsInsideShelter() {
        return noOfBtsInsideShelter;
    }

    public void setNoOfBtsInsideShelter(String noOfBtsInsideShelter) {
        this.noOfBtsInsideShelter = noOfBtsInsideShelter;
    }

    public String getNoOfBtsOutsideShelter() {
        return noOfBtsOutsideShelter;
    }

    public void setNoOfBtsOutsideShelter(String noOfBtsOutsideShelter) {
        this.noOfBtsOutsideShelter = noOfBtsOutsideShelter;
    }

    public String getShelterLock() {
        return shelterLock;
    }

    public void setShelterLock(String shelterLock) {
        this.shelterLock = shelterLock;
    }

    public String getOutdoorShelterLock() {
        return outdoorShelterLock;
    }

    public void setOutdoorShelterLock(String outdoorShelterLock) {
        this.outdoorShelterLock = outdoorShelterLock;
    }

    public String getIgbStatus() {
        return igbStatus;
    }

    public void setIgbStatus(String igbStatus) {
        this.igbStatus = igbStatus;
    }

    public String getEgbStatus() {
        return egbStatus;
    }

    public void setEgbStatus(String egbStatus) {
        this.egbStatus = egbStatus;
    }

    public String getNoOfOdcAvailable() {
        return noOfOdcAvailable;
    }

    public void setNoOfOdcAvailable(String noOfOdcAvailable) {
        this.noOfOdcAvailable = noOfOdcAvailable;
    }

    public String getOdcLock() {
        return odcLock;
    }

    public void setOdcLock(String odcLock) {
        this.odcLock = odcLock;
    }

}

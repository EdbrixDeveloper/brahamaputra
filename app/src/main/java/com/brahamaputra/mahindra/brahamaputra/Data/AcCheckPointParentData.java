
package com.brahamaputra.mahindra.brahamaputra.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcCheckPointParentData implements Serializable
{

    @SerializedName("noOfAcAvailableAtSite")
    @Expose
    private String noOfAcAvailableAtSite;
    @SerializedName("acCheckPoints")
    @Expose
    private List<AcCheckPoint> acCheckPoints = null;

    @SerializedName("shelterTemperature")
    @Expose
    private String shelterTemperature;
    @SerializedName("base64StringTakePhotoOfTemperature")
    @Expose
    private String base64StringTakePhotoOfTemperature;
    @SerializedName("shelterDoorStatus")
    @Expose
    private String shelterDoorStatus;

    @SerializedName("registerFault")
    @Expose
    private String registerFault;
    @SerializedName("typeOfFault")
    @Expose
    private String typeOfFault;
    @SerializedName("base64StringUploadPhotoOfRegisterFault")
    @Expose
    private String base64StringUploadPhotoOfRegisterFault;

    @SerializedName("isSubmited")
    @Expose
    private int isSubmited = 0;

    public String getNoOfAcAvailableAtsite() {
        return noOfAcAvailableAtSite;
    }

    public void setNoOfAcAvailableAtsite(String noOfAcAvailableAtSite) {
        this.noOfAcAvailableAtSite = noOfAcAvailableAtSite;
    }

    public List<AcCheckPoint> getAcCheckPoints() {
        return acCheckPoints;
    }

    public void setAcCheckPoints(List<AcCheckPoint> acCheckPoints) {
        this.acCheckPoints = acCheckPoints;
    }

    public AcCheckPointParentData(String noOfAcAvailableAtSite, List<AcCheckPoint> acCheckPoints,String registerFault, String typeOfFault, String base64StringUploadPhotoOfRegisterFault, String shelterTemperature, String base64StringTakePhotoOfTemperature, String shelterDoorStatus) {
        this.noOfAcAvailableAtSite = noOfAcAvailableAtSite;
        this.acCheckPoints = acCheckPoints;
        this.registerFault = registerFault;
        this.typeOfFault = typeOfFault;
        this.base64StringUploadPhotoOfRegisterFault = base64StringUploadPhotoOfRegisterFault;
        if(!this.noOfAcAvailableAtSite.isEmpty()) {
            isSubmited = 2;
        }else {
            isSubmited = 1;
        }

        this.shelterTemperature = shelterTemperature;
        this.base64StringTakePhotoOfTemperature = base64StringTakePhotoOfTemperature;
        this.shelterDoorStatus = shelterDoorStatus;
    }

    public AcCheckPointParentData() {
        this.noOfAcAvailableAtSite = "";
        this.registerFault = "";
        this.typeOfFault = "";
        this.acCheckPoints = new ArrayList<>();
        this.base64StringUploadPhotoOfRegisterFault = "";
        this.isSubmited = 0;
        this.shelterTemperature = "";
        this.base64StringTakePhotoOfTemperature = "";
        this.shelterDoorStatus = "";
    }

    public int getSubmited() {
        return isSubmited;
    }

    public void setSubmited(int isSubmited) {
        this.isSubmited = isSubmited;
    }

    public String getRegisterFault() {
        return registerFault;
    }

    public void setRegisterFault(String registerFault) {
        this.registerFault = registerFault;
    }

    public String getTypeOfFault() {
        return typeOfFault;
    }

    public void setTypeOfFault(String typeOfFault) {
        this.typeOfFault = typeOfFault;
    }

    public String getBase64StringUploadPhotoOfRegisterFault() {
        return base64StringUploadPhotoOfRegisterFault;
    }

    public void setBase64StringUploadPhotoOfRegisterFault(String base64StringUploadPhotoOfRegisterFault) {
        this.base64StringUploadPhotoOfRegisterFault = base64StringUploadPhotoOfRegisterFault;
    }

    public String getShelterTemperature() {
        return shelterTemperature;
    }

    public void setShelterTemperature(String shelterTemperature) {
        this.shelterTemperature = shelterTemperature;
    }

    public String getBase64TakePhotoOfTemperature() {
        return base64StringTakePhotoOfTemperature;
    }

    public void setBase64TakePhotoOfTemperature(String base64StringTakePhotoOfTemperature) {
        this.base64StringTakePhotoOfTemperature = base64StringTakePhotoOfTemperature;
    }

    public String getShelterDoorStatus() {
        return shelterDoorStatus;
    }

    public void setShelterDoorStatus(String shelterDoorStatus) {
        this.shelterDoorStatus = shelterDoorStatus;
    }
}

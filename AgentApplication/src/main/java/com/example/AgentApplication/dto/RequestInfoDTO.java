package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.enumeration.PaidState;

public class RequestInfoDTO {

    private Long adId;
    private String brandName;
    private String modelName;

    private int pendingRequestNum;
    private int acceptedRequestNum;
    private int canceledRequestNum;

    public RequestInfoDTO() {
    }

    public RequestInfoDTO(Request request, PaidState paidState) {
        this.adId = request.getAdvertisement().getId();
        this.acceptedRequestNum = 0;
        this.canceledRequestNum = 0;
        this.pendingRequestNum = 0;
        this.brandName = request.getAdvertisement().getCar().getBrand().getBrandName();
        this.modelName = request.getAdvertisement().getCar().getModel().getModelName();
        append(paidState);
    }

    public RequestInfoDTO append(PaidState paidState) {
        switch (paidState){
            case PENDING:
                pendingRequestNum++;
                break;
            case RESERVED:
                acceptedRequestNum++;
                break;
            case CANCELED:
                canceledRequestNum++;
                break;
            default:
                break;
        }

        return this;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public int getPendingRequestNum() {
        return pendingRequestNum;
    }

    public void setPendingRequestNum(int pendingRequestNum) {
        this.pendingRequestNum = pendingRequestNum;
    }

    public int getAcceptedRequestNum() {
        return acceptedRequestNum;
    }

    public void setAcceptedRequestNum(int acceptedRequestNum) {
        this.acceptedRequestNum = acceptedRequestNum;
    }

    public int getCanceledRequestNum() {
        return canceledRequestNum;
    }

    public void setCanceledRequestNum(int canceledRequestNum) {
        this.canceledRequestNum = canceledRequestNum;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}

package com.example.RequestService.dto;

public class RequestInfoDTO {

    private Long adId;
    private int pendingRequestNum;
    private int acceptedRequestNum;
    private int canceledRequestNum;

    public RequestInfoDTO() {
    }

    public RequestInfoDTO(Long adId) {
        this.adId = adId;
        this.acceptedRequestNum = 0;
        this.canceledRequestNum = 0;
        this.pendingRequestNum = 0;
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
}

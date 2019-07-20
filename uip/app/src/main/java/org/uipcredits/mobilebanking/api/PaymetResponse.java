package org.uipcredits.mobilebanking.api;

public class PaymetResponse {

    Integer success;
    String Message;

    public PaymetResponse(Integer success, String message) {
        this.success = success;
        Message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

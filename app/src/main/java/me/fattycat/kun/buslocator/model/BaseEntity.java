package me.fattycat.kun.buslocator.model;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class BaseEntity {

    /**
     * error :
     * status : SUCCESS
     * errorCode : EMPTYERROR
     */

    private String error;
    private String status;
    private String errorCode;

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

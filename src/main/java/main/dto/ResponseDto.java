package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"httpStatus"})
public class ResponseDto {

    private boolean success;

    private Object data;
    private String message;
    private HttpStatus httpStatus;

    public ResponseDto(boolean success, Object data, String message, HttpStatus httpStatus){
        this.success = success;
        this.data = data;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}

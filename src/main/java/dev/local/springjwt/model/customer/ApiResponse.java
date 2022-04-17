package dev.local.springjwt.model.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApiResponse<T> {

    private int status;
    private String message;
    private Object data;
    private Object token;

    public ApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int status, String message, Object result,Object data) {
        this.status = status;
        this.message = message;
        this.data = result;
        this.token = data;
    }

}

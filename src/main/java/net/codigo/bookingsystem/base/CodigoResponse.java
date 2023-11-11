package net.codigo.bookingsystem.base;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CodigoResponse implements Serializable {
    public String msg;
    public Object data;
    public Integer statusCode;
    private long timestamp;

    public static CodigoResponse fail(String errorMsg) {
        return CodigoResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data("*****")
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
    public static CodigoResponse success(String msg, Object data) {
        return CodigoResponse.builder()
                .statusCode(Constant.SUCCESS_CODE)
                .msg(msg)
                .data(data)
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
    public static CodigoResponse fail(String errorMsg, Object data) {
        return CodigoResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data(data.toString())
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
}

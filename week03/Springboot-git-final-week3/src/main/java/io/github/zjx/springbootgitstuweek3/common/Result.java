package io.github.zjx.springbootgitstuweek3.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(HttpStatus.OK.value(), "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(HttpStatus.OK.value(), msg, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}

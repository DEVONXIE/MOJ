package com.devon.moj.utilis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;
    private long count;

    public static Result success(Object data, long count) {
        return new Result(200, "success", data, count);
    }

    public static Result success(long count) {
        return new Result(200, "success", null, count);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, null, 0);
    }

    public static Result fail(int code, String message, Object data) {
        return new Result(code, message, data, 0L);
    }
}

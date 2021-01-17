package com.yy.system.exception;

import com.yy.system.api.IErrorCode;

/*
 *@Description: 断言处理类，用于抛出各种API异常
 *@ClassAuthor: tengYong
 *@Date: 2021-01-16 11:28:26
*/
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}

package com.yy.system.exception;

import com.yy.system.api.IErrorCode;

/*
 *@Description: 自定义API异常
 *@ClassAuthor: tengYong
 *@Date: 2021-01-16 11:26:43
*/
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}

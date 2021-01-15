package com.yy.system.api;

/*
 *@Description: 封装API的错误码
 *@ClassAuthor: tengYong
 *@Date: 2021-01-15 13:26:22
*/
public interface IErrorCode {
    long getCode();

    String getMessage();
}

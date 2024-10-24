package com.itsp.basicspring.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * NotFoundException的定义，使用@ExceptionMapper注解修饰
 * code:代表接口的异常码
 * msg:代表接口的异常提示
 */
@ExceptionMapper(code = "1404", msg = "找不到对象")
public class NotFoundException extends RuntimeException {

}
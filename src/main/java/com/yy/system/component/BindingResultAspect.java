package com.yy.system.component;

import com.yy.system.api.CommonResult;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/*
 *@Description: HibernateValidator错误结果处理切面
 *@ClassAuthor: tengYong
 *@Date: 2021-01-15 17:59:42
*/
@Aspect
@Component
@Order(2)
public class BindingResultAspect {

    // 符号             含义
    // execution（） 表达式的主体；
    // 第一个”*“符号 表示返回值的类型任意；
    // com.sample.service.impl AOP所切的服务的包名，即，我们的业务部分
    // 包名后面的”..“ 表示当前包及子包
    // 第二个”*“ 表示类名，*即所有类。此处可以自定义，下文有举例
    // .*(..) 表示任何方法名，括号表示参数，两个点表示任何参数类型
    @Pointcut("execution(public * com.yy.system.*.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if (fieldError != null) {
                        return CommonResult.validateFailed(fieldError.getDefaultMessage());
                    } else {
                        return CommonResult.validateFailed();
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}

package com.givejeong.todo.common;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log
@Component
@Aspect
public class ControllerLoggerAdvice {
    @Before("execution(* com.givejeong.todo.controller.*.*(..))")
    public void startLog(JoinPoint jp){
        log.info("********************************************************************");
        log.info("------------"+jp.getSignature()+"-----------");
        log.info("------------"+ Arrays.toString(jp.getArgs())+"-----------");
        log.info("********************************************************************");
    }
}

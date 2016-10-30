package com.leelab.bnwserver.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAop {
	
	@Pointcut("execution(public * *(..))")
	private void log(){}
	
	@Around("log()")
	public Object logger(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("½ÇÇà?");
		return jp.proceed();
	}
	
}

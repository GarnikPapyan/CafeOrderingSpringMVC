package org.example.cafe.aspect;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cafe.cache.MethodsCaches;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Arrays;
import java.util.Optional;


@Component
@Aspect
public class LoggingAspect {



    private static final Logger log = Logger.getLogger(LoggingAspect.class.getName());

    private final MethodsCaches cache;


    public LoggingAspect(MethodsCaches cache) {
        this.cache = cache;
    }

    @Pointcut("execution(* org.example.cafe.controllers.*.*(..))")
    public void timeExecutionForDao(){}

    @Around("timeExecutionForDao()")
    public Object aroundExecutionTimeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Long startTime = System.currentTimeMillis();

        Object res = proceedingJoinPoint.proceed();

        Long endTime = System.currentTimeMillis();
        Long processingTime = endTime-startTime;
        ThreadContext.put("processing_time", processingTime.toString());
        String uri = getRequestURI();
        ThreadContext.put("request_uri", uri);
        log.info("info");

        System.out.println("Method name: " + proceedingJoinPoint.getSignature().getName() + "  Request URI:"+ uri
                + "   executing time(ms): " + processingTime + "ms:" );
        return  res;
    }

    @Around("timeExecutionForDao() || execution(* org.example.cafe.dao.*.*(..))")
    public Object aroundHandleExecutionLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
           return    proceedingJoinPoint.proceed();
        } catch (Exception e) {
            System.out.println("Name of the method that threw the exception " +
                    proceedingJoinPoint.getSignature().getName() + " :" + e.getMessage());
            throw e;
        }
    }

    @Around("execution(* org.example.cafe.dao.*.*(..))")
    public Object aroundCacheForGetMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = createKay(proceedingJoinPoint);
        Optional optional = cache.get(key);
        if(optional.isPresent()) {
            return optional.get();
        }
        Object res = proceedingJoinPoint.proceed();
        cache.put(key,res);
        return res;
    }

    private String getRequestURI(){
        String uri = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (attributes != null) {
            request = attributes.getRequest();
            uri = request.getRequestURI();
        }
        return uri;
    }

    private String createKay(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        String args = String.join(",", Arrays.asList(proceedingJoinPoint.getArgs()).toString());
        return methodName + "(" + args + ")";
    }

}

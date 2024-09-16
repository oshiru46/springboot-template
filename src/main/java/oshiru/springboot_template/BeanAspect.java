package oshiru.springboot_template;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class BeanAspect {

    @Around("execution(* oshiru.springboot_template.*.*(..))")
    public Object beanAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        /** Feature: Bean IN/OUT Logging */
        log.info("**Oshiru** IN {}.{}()", className, methodName);

        /** Feature: Bean Name Stacktrace */
        RequestContext.current().getBeanStack().push(className + "." + methodName + "()");

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.info("Exception!", throwable);
            throw throwable;
        } finally {
            /** Feature: Bean Name Stacktrace */
            RequestContext.current().getBeanStack().pop();
            /** Feature: Bean IN/OUT Logging */
            log.info("**Oshiru** OUT {}.{}()", className, methodName);
        }
    }
}

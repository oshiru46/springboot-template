package oshiru.springboot_template;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

@Aspect
@Component
public class BeanAspect {
    private static final Logger log = LoggerFactory.getLogger(BeanAspect.class);

    @Around("execution(* oshiru.springboot_template.*.*(..))")
    public Object beanAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        /** Feature: Bean IN/OUT Logging */
        log.methodIn(className, methodName);

        /** Feature: Bean Name Stacktrace */
        RequestContext.currentOptional().ifPresent(ctx -> {
            ctx.getBeanStack().push(className + "." + methodName + "()");
        });

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.info("Exception!", throwable);
            throw throwable;
        } finally {
            /** Feature: Bean Name Stacktrace */
            RequestContext.currentOptional().ifPresent(ctx -> {
                ctx.getBeanStack().pop();
            });
            /** Feature: Bean IN/OUT Logging */
            log.methodOut(className, methodName);
        }
    }
}

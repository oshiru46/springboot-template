package oshiru.springboot_template;

import java.util.Stack;

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
            log.debug("Exception! class: {}, message: {}",
                    throwable.getClass().getName(), throwable.getMessage());
            /** Feature: Bean Name Stacktrace */
            RequestContext.currentOptional().ifPresent(ctx -> {
                if (ctx.getBeanStackLastExceptionThrown() == null) {
                    ctx.setBeanStackLastExceptionThrown(new Stack<>());
                    for (String beanName : ctx.getBeanStack()) {
                        ctx.getBeanStackLastExceptionThrown().push(beanName);
                    }
                }
            });
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

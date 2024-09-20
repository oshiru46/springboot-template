package oshiru.springboot_template;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

@RestControllerAdvice
public class OshiruControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(OshiruControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.debug("Handling Exception");

        /** Feature: Bean Name Stacktrace */
        RequestContext.currentOptional().ifPresent(ctx -> {
            log.debug("bean stack last exception thrown: {}", ctx.getBeanStackLastExceptionThrown().toString());
            log.debug("current bean stack: {}", ctx.getBeanStack().toString());
        });

        return "exception handled";
    }
}

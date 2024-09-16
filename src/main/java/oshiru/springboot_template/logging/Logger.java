package oshiru.springboot_template.logging;

import java.util.Collections;

import oshiru.springboot_template.RequestContext;

public class Logger {
    /** 委譲先 */
    private final org.slf4j.Logger logger;

    Logger(Class<?> clazz) {
        this.logger = org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public void debug(String format, Object... arguments) {
        /** Feature: Storied Debug Log */
        RequestContext.currentOptional().ifPresentOrElse(ctx -> {
            int stackLength = ctx.getBeanStack().size();
            String storyLine = String.join("", Collections.nCopies(stackLength, "│"));
            this.logger.debug(storyLine + " " + format, arguments);
        }, () -> {
            this.logger.debug(format, arguments);
        });
    }

    public void methodIn(String className, String methodName) {
        /** Feature: Storied Debug Log */
        RequestContext.currentOptional().ifPresentOrElse(ctx -> {
            int stackLength = ctx.getBeanStack().size();
            String storyLine = String.join("", Collections.nCopies(stackLength, "│"));
            this.logger.debug(storyLine + "┐" + " IN {}.{}()", className, methodName);
        }, () -> {
            this.logger.debug("IN {}.{}()", className, methodName);
        });
    }

    public void methodOut(String className, String methodName) {
        /** Feature: Storied Debug Log */
        RequestContext.currentOptional().ifPresentOrElse(ctx -> {
            int stackLength = ctx.getBeanStack().size();
            String storyLine = String.join("", Collections.nCopies(stackLength, "│"));
            this.logger.debug(storyLine + "┘" + " OUT {}.{}()", className, methodName);
        }, () -> {
            this.logger.debug("OUT {}.{}()", className, methodName);
        });
    }

    public void info(String message) {
        this.logger.info(message);
    }

    public void info(String message, Throwable th) {
        this.logger.info(message, th);
    }
}

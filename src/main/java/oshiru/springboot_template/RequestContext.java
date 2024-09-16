package oshiru.springboot_template;

import java.util.Date;
import java.util.Stack;

import lombok.Data;

/**
 * Feature: Request Context
 */
@Data
public class RequestContext {
    private static final ThreadLocal<RequestContext> CONTEXT = ThreadLocal.withInitial(RequestContext::new);

    public static RequestContext current() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    /** UUID. useful for logging. */
    private String requestId;
    /** Request received date. */
    private Date requestDate;

    /** Feature: Bean Name Stacktrace */
    private Stack<String> beanStack = new Stack<>();
}

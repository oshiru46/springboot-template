package oshiru.springboot_template;

import java.util.Date;
import java.util.Optional;
import java.util.Stack;

import lombok.Data;

/**
 * Feature: Request Context
 */
@Data
public class RequestContext {
    /**
     * RequestContextを保持するThreadLocal。
     * FilterでRequestContextの生成と破棄を行う。
     * 非リクエスト処理(起動時処理やバックグラウンド処理等)ではFilterを通らないため、
     * withInitial(RequestContext::new)で初期化すると非リクエスト処理で思わぬThreadLocalの保持が行われる可能性がある。
     * そのため、withInitialではなくコンストラクタで初期化する。
     */
    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    public static RequestContext init() {
        RequestContext context = new RequestContext();
        CONTEXT.set(context);

        return context;
    }

    public static RequestContext current() {
        return CONTEXT.get();
    }

    public static Optional<RequestContext> currentOptional() {
        if (current() == null) {
            return Optional.empty();
        }

        return Optional.of(current());
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
    /** Feature: Bean Name Stacktrace */
    private Stack<String> beanStackLastExceptionThrown;
}

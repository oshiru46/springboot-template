package oshiru.springboot_template;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Feature: Request Context
 */
@Component
public class MyRequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            RequestContext context = RequestContext.current();

            context.setRequestDate(new Date());

            // RequestIdはHTTPヘッダで指定されてくることもある、という仕様を仮定。
            // APIごとに挙動を変える仕様の場合はHandlerInterceptorでContextにセットするほうがよいかもしれない。
            String requestId;
            if (ObjectUtils.isEmpty(httpRequest.getHeader("X-Request-ID"))) {
                requestId = (UUID.randomUUID().toString());
            } else {
                requestId = httpRequest.getHeader("X-Request-ID");
            }

            context.setRequestId(requestId);

            // 次のフィルタへ処理を渡す
            chain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }

}

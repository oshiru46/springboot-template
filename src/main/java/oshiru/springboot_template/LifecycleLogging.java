package oshiru.springboot_template;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LifecycleLogging implements ServletContextListener {
    /**
     * DBCP初期化前
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("**Oshiru** Context initialized.");
        ServletContextListener.super.contextInitialized(sce);
    }

    /**
     * 実際にListenできるイベントを見るのによい。
     */
    @Component
    static class CatchAll implements ApplicationListener<SpringApplicationEvent> {
        @Override
        public void onApplicationEvent(@NonNull SpringApplicationEvent event) {
            log.info("**Oshiru**　Caught ApplicationEvent: {}", event.getClass().getSimpleName());
        }
    }

    /**
     * リクエスト受付可能状態になったとき
     */
    @Component
    static class Ready implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
            log.info("**Oshiru** Application is READY!");
        }
    }

    /**
     * 起動シーケンスでエラーとなったとき
     */
    @Component
    static class Failed implements ApplicationListener<ApplicationFailedEvent> {
        @Override
        public void onApplicationEvent(@NonNull ApplicationFailedEvent event) {
            Throwable cause = event.getException();
            log.info("**Oshiru** Application failed!! message: {}", cause.getMessage());
        }
    }

    /**
     * DBCP解放前
     */
    @Component
    static class Destroying implements DisposableBean {
        @Override
        public void destroy() throws Exception {
            log.info("**Oshiru** Application is been destroying.");
        }
    }

    /**
     * DBCP解放後
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("**Oshiru** Context destroyed.");
        ServletContextListener.super.contextDestroyed(sce);
    }
}

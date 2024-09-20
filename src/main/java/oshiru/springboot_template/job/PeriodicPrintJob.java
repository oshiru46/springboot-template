package oshiru.springboot_template.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

/**
 * Feature: Background Job
 */
@Component
public class PeriodicPrintJob {
    private static final Logger log = LoggerFactory.getLogger(PeriodicPrintJob.class);
    private final ScheduledExecutorService scheduler;

    PeriodicPrintJob() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    void init() {
        scheduleNextJob();
        log.debug("PeriodicPrintJob registered!");
    }

    @PreDestroy
    void destroy() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                log.debug("PeriodicPrintJob Termination timeout");
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.debug("Interrupted awaitTermination. force shutdown.");
            scheduler.shutdownNow();
        }
    }

    private void scheduleNextJob() {
        // You can decide delay dynamically.
        int delaySec = 60;

        scheduler.schedule(() -> {
            performJob();
            scheduleNextJob();
        }, delaySec, TimeUnit.SECONDS);
    }

    private void performJob() {
        log.debug("Job Called!!");
    }
}

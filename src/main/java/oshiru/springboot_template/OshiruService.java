package oshiru.springboot_template;

import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import oshiru.springboot_template.exception.OshiruRetryableException;
import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

@Service
public class OshiruService {
    private static final Logger log = LoggerFactory.getLogger(OshiruService.class);

    @Transactional
    public void test() {
        log.debug("at Service");
    }

    public void causeError() {
        log.debug("throwing exception");
        throw new RuntimeException("error!");
    }

    /** Feature: Retry Request */
    @Transactional
    @Retryable(retryFor = { OshiruRetryableException.class }, maxAttempts = 3)
    public void giveup() {
        log.debug("trying...");
        RetryContext retryContext = RetrySynchronizationManager.getContext();
        if (retryContext != null) {
            // retry count: first giveup -> 0, first retry -> 1
            log.debug("Current retry count: {}", retryContext.getRetryCount());
        }

        throw new OshiruRetryableException("giveup...");
    }

    /** Feature: Retry Request */
    @Recover
    public void recover(OshiruRetryableException e) {
        log.debug("retry count over...");
    }
}

package oshiru.springboot_template.logging;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Feature: Transaction Logging
 */
public class TransactionLogging {
    private static final Logger log = LoggerFactory.getLogger(TransactionLogging.class);

    /**
     * BEGIN, COMMIT, ROLLBACK前後にログ出力をするカスタムTransactionManager
     */
    public static class CustomTransactionManager implements PlatformTransactionManager {

        private final PlatformTransactionManager delegate;

        public CustomTransactionManager(PlatformTransactionManager delegate) {
            this.delegate = delegate;
        }

        @Override
        public @NonNull TransactionStatus getTransaction(@Nullable TransactionDefinition definition) {
            // BEGIN TRANSACTIONとログに出してはいるが、実際にBEGINで開始しているとは限らない。
            log.debug("[Aspect] Before BEGIN TRANSACTION");
            TransactionStatus status = delegate.getTransaction(definition);
            log.debug("[Aspect] After BEGIN TRANSACTION");
            return status;
        }

        @Override
        public void commit(@NonNull TransactionStatus status) {
            log.debug("[Aspect] Before COMMIT");
            delegate.commit(status);
            log.debug("[Aspect] After COMMIT");
        }

        @Override
        public void rollback(@NonNull TransactionStatus status) {
            log.debug("[Aspect] Before ROLLBACK");
            delegate.rollback(status);
            log.debug("[Aspect] After ROLLBACK");
        }
    }

    @Configuration
    @EnableTransactionManagement
    public static class TransactionManagerConfig {

        private final DataSource dataSource;

        public TransactionManagerConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        @Primary
        public PlatformTransactionManager customTransactionManager() {
            // DataSourceTransactionManagerを使う前提
            PlatformTransactionManager defaultTransactionManager = new DataSourceTransactionManager(dataSource);

            return new CustomTransactionManager(defaultTransactionManager);
        }
    }
}

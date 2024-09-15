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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionLogging {
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
            log.info("**Oshiru** Before BEGIN TRANSACTION");
            TransactionStatus status = delegate.getTransaction(definition);
            log.info("**Oshiru** After BEGIN TRANSACTION");
            return status;
        }

        @Override
        public void commit(@NonNull TransactionStatus status) {
            log.info("**Oshiru** Before COMMIT");
            delegate.commit(status);
            log.info("**Oshiru** After COMMIT");
        }

        @Override
        public void rollback(@NonNull TransactionStatus status) {
            log.info("**Oshiru** Before ROLLBACK");
            delegate.rollback(status);
            log.info("**Oshiru** After ROLLBACK");
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

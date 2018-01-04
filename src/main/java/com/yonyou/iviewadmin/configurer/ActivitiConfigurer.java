package com.yonyou.iviewadmin.configurer;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ActivitiConfigurer extends AbstractProcessEngineAutoConfiguration {
    //注入数据源和事务管理器
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource,
                                                                             @Qualifier("TransactionManager") PlatformTransactionManager transactionManager,
                                                                             SpringAsyncExecutor springAsyncExecutor) throws IOException {
        return this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
    }


}


package com.vlad.store.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.vlad.store")
@EnableTransactionManagement
//@EntityScan("com.jezh.textsaver.entity.extension")
public class DataSourceConfig {
}

package com.lws.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Slf4j
@MapperScan(basePackages = {"com.lws.demo.mapper.db2"}, sqlSessionFactoryRef = "db2SqlSessionFactory")
public class DB2Config {

    @Bean
    @ConfigurationProperties("mydb2.datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db2DataSource() {
        DataSourceProperties dataSourceProperties = db2DataSourceProperties();
        log.info("mydb2 datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager db2TxManager(DataSource db2DataSource) {
        return new DataSourceTransactionManager(db2DataSource);
    }

    @Bean
    public SqlSessionFactory db2SqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(db2DataSource());
        factoryBean.setVfs(SpringBootVFS.class);
        SqlSessionFactory factory = factoryBean.getObject();
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        factory.getConfiguration().getTypeAliasRegistry().registerAliases("com.lws.demo.entity.db2");

        return factory;
    }

    @Bean
    public SqlSessionTemplate db2SqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(db2SqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }
}

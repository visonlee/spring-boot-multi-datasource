## spring boot & Mybatis多数据源配置

[项目地址](https://github.com/visonlee/spring-boot-multi-datasource)

因为xml文件放到代码里面的文件夹了,所以在`pom.xml`上,打包考虑mapper的xml文件
```xml
<!-- 编译时考虑 src/main/java 目录下的*.xml文件-->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
```

在main方法的注解里面,我们需要修改成,代表我们需要修改配置
```java
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
```

跟着在`com.lws.demo.config`包,里面添加两个配置类,分别呢添加两个类,`DB1Config`和`DB2Config`，其中一个代码如下:

```java
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
@MapperScan(basePackages = {"com.lws.demo.mapper.db1"}, sqlSessionFactoryRef = "db1SqlSessionFactory")
public class DB1Config {

    @Bean
    @ConfigurationProperties("mydb1.datasource")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db1DataSource() {
        DataSourceProperties dataSourceProperties = db1DataSourceProperties();
        log.info("mydb1 datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager db1TxManager(DataSource db1DataSource) {
        return new DataSourceTransactionManager(db1DataSource);
    }

    @Bean
    public SqlSessionFactory db1SqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(db1DataSource());
        factoryBean.setVfs(SpringBootVFS.class);
        SqlSessionFactory factory = factoryBean.getObject();
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        factory.getConfiguration().getTypeAliasRegistry().registerAliases("com.lws.demo.entity.db1");

        return factory;
    }

    @Bean
    public SqlSessionTemplate db1SqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(db1SqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }
}

```

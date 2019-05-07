package com.lws.demo;

import com.lws.demo.entity.db1.User;
import com.lws.demo.entity.db2.Blog;
import com.lws.demo.mapper.db1.UserMapper;
import com.lws.demo.mapper.db2.BlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> userList = userMapper.getAllUser();
        System.out.println(userList);
        List<Blog> blogList = blogMapper.getAllBlog();
        System.out.println(blogList);
    }
}


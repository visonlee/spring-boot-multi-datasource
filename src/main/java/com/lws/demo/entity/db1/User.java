package com.lws.demo.entity.db1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Integer id;
    private String name;
    private Integer age;
}

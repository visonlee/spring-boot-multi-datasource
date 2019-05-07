package com.lws.demo.entity.db2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog {

    private Integer id;
    private String title;
    private String content;
}

package com.obeast.common.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wxl
 * Date 2023/1/9 13:51
 * @version 1.0
 * Description: 邮箱配置类
 */
@Data
@ConfigurationProperties("project")
public class BendanProjectProperties {


    /**
     * 名字
     * */
    private String name;

    /**
     * 作者
     * */
    private String author;
}

package com.obeast.common.mail;

import com.obeast.common.mail.config.BendanMailAutoConfig;
import com.obeast.common.mail.config.BendanProjectProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wxl
 * Date 2023/1/9 13:58
 * @version 1.0
 * Description: 开启邮箱的注解
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({BendanMailAutoConfig.class, BendanProjectProperties.class})
public @interface EnableMail {
}

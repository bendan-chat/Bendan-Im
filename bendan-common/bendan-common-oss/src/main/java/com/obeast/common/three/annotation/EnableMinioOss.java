package com.obeast.common.three.annotation;

import com.obeast.common.three.config.MinioOssAutoConfig;
import com.obeast.common.three.properties.ThirdPartyMinioProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wxl
 * Date 2023/1/4 12:45
 * @version 1.0
 * Description: minio
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MinioOssAutoConfig.class)
public @interface EnableMinioOss {
}

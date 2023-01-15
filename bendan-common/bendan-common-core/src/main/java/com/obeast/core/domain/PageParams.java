package com.obeast.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wxl
 * Date 2022/12/21 14:59
 * @version 1.0
 * Description: 分页对象
 */
@Data
public class PageParams {

    /**
     * 当前页码
     */
    @Schema(description = "当前页码，从1开始")
    private Long cur;

    /**
     * 每页显示记录数
     */
    @Schema(description = "每页显示记录数")
    private Long limit;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段 create_time")
    private String orderField;

    /**
     * 排序方式
     */
    @Schema(description = "排序方式，可选值(true -> asc;  false -> desc)")
    private Boolean order;

}

package com.obeast.common.oss.enumration;

import lombok.Getter;

/**
 * @author wxl
 * Date 2023/1/3 17:52
 * @version 1.0
 * Description: 分片上传的状态
 */
public enum ShardFileStatusCode {
    /**
     *  SUCCESS(20000, "操作成功"),
     *     PARAM_ERROR(40000, "参数异常"),
     *     NULL_ARGS(40000, "参数为空"),
     *     NOT_FOUND(40004, "资源不存在"),
     *     FOUND(40007, "资源存在"),
     *     FAILURE(50000, "系统异常"),
     *     CUSTOM_FAILURE(50001, "自定义异常错误"),
     *     ALONE_CHUNK_UPLOAD_SUCCESS(20001, "分片上传成功的标识"),
     *     ALL_CHUNK_UPLOAD_SUCCESS(20002, "所有的分片均上传成功"),
     *     ALL_CHUNK_MERGE_SUCCESS(20003, "所有的分片均合并成功"),
     *     ALL_CHUNK_SUCCESS(20004, "文件上传成功"),
     *     FILE_IS_NULL(50004, "文件为空"),
     *     SHARD_MUST_MORE_THAN_5M(50005, "分片大小必须大于5M");
     * */
    SUCCESS(20000, "操作成功"),
    PARAM_ERROR(40000, "参数异常"),
    NULL_ARGS(40000, "参数为空"),
    NOT_FOUND(40004, "资源不存在"),
    FOUND(40007, "资源存在"),
    FAILURE(50000, "系统异常"),
    CUSTOM_FAILURE(50001, "自定义异常错误"),
    ALONE_CHUNK_UPLOAD_SUCCESS(20001, "分片上传成功的标识"),
    ALL_CHUNK_UPLOAD_SUCCESS(20002, "所有的分片均上传成功"),
    ALL_CHUNK_MERGE_SUCCESS(20003, "所有的分片均合并成功"),
    ALL_CHUNK_SUCCESS(20004, "文件上传成功"),
    FILE_IS_NULL(50004, "文件为空"),
    UUID_IS_NULL(50004, "UUID为空"),
    SHARD_MUST_MORE_THAN_5M(50005, "分片大小必须大于5M");
    @Getter
    private final Integer code;
    @Getter
    private final String message;

    ShardFileStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}


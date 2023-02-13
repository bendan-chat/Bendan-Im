package com.obeast.core.base;



import com.obeast.core.constant.WebResultEnum;

import java.util.Optional;

/**
 * @author wxl
 * Date 2022/8/17 14:13
 * @version 1.0
 * Description: 全局返回值类
 */
public class CommonResult<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    public interface Success {
        int successCode = 200;
        String successMsg = "操作成功！";

        Boolean success = Boolean.TRUE;
    }

    public CommonResult() {
    }

    protected CommonResult(Integer code, String msg, T data, Boolean isSuccess) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = isSuccess;
    }

    /**
     * Description: 根据 boolean 自动判断是否成功
     * @author wxl
     * Date: 2023/2/11 21:46
     * @param isSuccess  isSuccess
     * @return com.obeast.core.base.CommonResult<T>
     */
    public static <T> CommonResult<T> auto(boolean isSuccess) {
        return isSuccess ? CommonResult.success() : CommonResult.error();

    }

    /**
     * Description: 无返回值
     * @author wxl
     * Date 2022/7/15 17:56
     * @return null
     **/
    public static <T> CommonResult<T> success() {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setSuccess(Success.success);
        commonResult.setCode(Success.successCode);
        commonResult.setMsg(Success.successMsg);
        return commonResult;
    }


    /**
     * Description: 成功有描述
     * @author wxl
     * Date: 2022/8/17 14:12
     * @param message msg
     * @return com.worldintek.common.api.R<T>
     */
    public static <T> CommonResult<T> success(String message) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setSuccess(true);
        commonResult.setCode(200);
        commonResult.setMsg(message);
        return commonResult;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(WebResultEnum.SUCCESS.getCode(), WebResultEnum.SUCCESS.getMessage(), data, true);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(WebResultEnum.SUCCESS.getCode(), message, data, true);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> error(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null, false);
    }

    /**
     * 失败返回结果
     * @param code 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult<>(code, message, null, false);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> error(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null, false);
    }

    /**
     * 失败返回结果
     * @param message 错误信息
     * @param data 返回数据
     */
    public static <T> CommonResult<T> error(String message, T data) {
        return new CommonResult<>(WebResultEnum.FAILURE.getCode(), message, data, false);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(WebResultEnum.FAILURE.getCode(), message, null, false);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> error() {
        return error(WebResultEnum.FAILURE);
    }

    /**
     * 远程失败返回结果
     */
    public static <T> CommonResult<T> remoteFailed() {
        return error(WebResultEnum.REQ_REJECT);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return error(WebResultEnum.VALID_CODE_ERROR);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(WebResultEnum.VALID_CODE_ERROR.getCode(), message, null, false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<>(WebResultEnum.UN_AUTHORIZED.getCode(), WebResultEnum.UN_AUTHORIZED.getMessage(), null, false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(WebResultEnum.UN_AUTHORIZED.getCode(), message, null, false);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(WebResultEnum.REQ_REJECT.getCode(), WebResultEnum.REQ_REJECT.getMessage(), data, false);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Optional<T> getOptionalData(CommonResult<T> result){
        return Optional.of(result.getData());
    }
}


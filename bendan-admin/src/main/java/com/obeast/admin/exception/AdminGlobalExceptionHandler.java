package com.obeast.admin.exception;

import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import com.obeast.core.exception.BendanException;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @author wxl
 * Date 2022/9/21 15:41
 * @version 1.0
 * Description: 全局异常处理
 */
@RestControllerAdvice
public class AdminGlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * Description: 全局异常处理
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return com.worldintek.core.base.CommonResult<?>
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<?> exception(Exception e) throws Exception {
        log.error("全局异常捕获---------------->", e);
        return CommonResult.error(e.getMessage());
    }
    /**
     * Description: 参数为空校验异常处理
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return com.worldintek.core.base.CommonResult<?>
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<?> illegalArgumentException(IllegalArgumentException e) throws Exception {
        log.warn("参数为空校验异常处理---------------->{}", e.getMessage());
        return CommonResult.error(e.getMessage());
    }
    /**
     * Description: 远程调用异常
     * @author wxl
     * Date: 2022/12/10 20:20
     * @param e
     * @return com.worldintek.core.base.CommonResult<?>
     */

  @ExceptionHandler({RetryableException.class, FeignException.class})
    public CommonResult<?> retryableException(Exception e) throws Exception {
        log.error("远程调用异常---------------->{}", e.getMessage());
        return CommonResult.error(e.getMessage());
    }


    /**
     * Description: 处理自定义异常
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return com.worldintek.core.base.CommonResult<?>
     */
    @ExceptionHandler(BendanException.class)
    public CommonResult<?> bendanException(Exception e) throws Exception {
        log.error("自定义异常捕获---------------->{}", e.getMessage());
        return CommonResult.error(e.getMessage());
    }


    /**
     * Description: 处理路径找不到异常
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return com.worldintek.core.base.CommonResult<java.lang.Object>
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<Object> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = formatMessage(bindingResult);
        return CommonResult.error(message);
    }


    /**
     * Description: 没有权限处理
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return java.lang.Object
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult<Object> accessDeniedException(Exception e) {
        log.debug("accessDeniedException--> ", e);
        return CommonResult.error(WebResultEnum.REQ_REJECT);
    }

    /**
     * Description: 处理找不到路径
     * @author wxl
     * Date: 2022/12/9 11:24
     * @param e  e
     * @return java.lang.Object
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public CommonResult<Object> noHandlerFoundException(Exception e) {
        return CommonResult.error(WebResultEnum.NOT_FOUND ,e.getMessage());
    }

    /**
     * Description: 处理异常参数校验异常信息
     * @author wxl
     * Date: 2022/12/9 11:23
     * @param e  e
     * @return java.lang.Object
     */
    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    public CommonResult<Object> nullPointerException(Exception e) {
        log.error("程序中出现空引用和空参数，请检查！ ------------>{}", e.getMessage());
        return CommonResult.error(WebResultEnum.PARAM_MISS);
    }

    /**
     * Description: 处理异常参数校验异常信息
     * @author wxl
     * Date: 2022/12/9 11:21
     * @param bindingResult  bindingResult
     * @return java.lang.String
     */
    private String formatMessage(BindingResult bindingResult) {
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return message;
    }



}
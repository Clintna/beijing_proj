package beijing.transport.beijing_proj.common.exception;

import beijing.transport.beijing_proj.common.enums.ErrorCodeEnum;
import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 全局异常处理类
 *
 * @author Jinglin
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//设置接口返回的http状态，根据需求设置
    @ExceptionHandler(RuntimeException.class)
    public ServiceResponse<?> handlerRuntimeException(RuntimeException e) {
//        e.printStackTrace();
        log.error("系统运行异常，异常信息", e);
        return new ServiceResponse<>(e);
    }


    @ExceptionHandler(value = Exception.class)
    public GongjiaoResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new GongjiaoResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("系统内部异常");
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GongjiaoResponse ConstraintViolationExceptionHandler(MethodArgumentNotValidException ex) {

        return new GongjiaoResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }

}


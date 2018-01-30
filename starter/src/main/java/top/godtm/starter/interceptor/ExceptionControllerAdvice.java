package top.godtm.starter.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.godtm.core.util.ServerResponse;

/**
 * date: 2018/1/30
 * author: wt
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

//    @ExceptionHandler
//    @ResponseBody
//    public ServerResponse bindExceptionHandler(BindException e) {
//        return ServerResponse.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//    }

    @ExceptionHandler
    @ResponseBody
    public ServerResponse exceptionHandler(Exception e) {
        return ServerResponse.error(e.getCause().getLocalizedMessage());
    }
}

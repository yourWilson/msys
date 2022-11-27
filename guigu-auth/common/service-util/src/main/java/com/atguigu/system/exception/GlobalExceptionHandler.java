package com.atguigu.system.exception;


import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//首先，ControllerAdvice本质上是一个Component，因此也会被当成组建扫描，一视同仁，扫扫扫。
// @ControllerAdvice 配合 @ExceptionHandler 实现全局异常处理
@ControllerAdvice
public class GlobalExceptionHandler {

//全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("出现了全局异常处理！");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("出现了算数异常处理");
    }

    //特定异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result error(NullPointerException e){
        e.printStackTrace();
        return Result.fail().message("出现了零指针异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result error(GuiguException e){
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

}

package xyz.peikun.product.exceptionhandler;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.peikun.common.exception.BizCodeEnum;
import xyz.peikun.common.utils.R;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R argumentNotValid(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();

        result.getFieldErrors().forEach(item -> {
            String message = item.getDefaultMessage();
            String filed = item.getField();

            map.put(item.getObjectName() + "对象中的" + filed, message);

        });
        return R.error(BizCodeEnum.VALUED_EXCEPTION.getCode(), BizCodeEnum.VALUED_EXCEPTION.getMsg()).put("data",map);
    }

    @ExceptionHandler(Exception.class)
    public R unknownRequest(Exception e){
        e.printStackTrace();
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }

}
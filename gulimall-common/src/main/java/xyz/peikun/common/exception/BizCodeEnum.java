package xyz.peikun.common.exception;

public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统状态异常"),
    VALUED_EXCEPTION(10001,"数据校验不合法");



    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

package xyz.peikun.common.constant;

public class ProductConstant {
    public enum attr{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");

        attr(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        int code;
        String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}

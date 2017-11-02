package t.c;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/10/29.
 */
//返回json数据
@Component
public class Message1 {
    private int code;
    private String message;
    private boolean result;

    public Message1() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}

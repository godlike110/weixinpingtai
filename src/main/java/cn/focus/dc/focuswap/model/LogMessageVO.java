package cn.focus.dc.focuswap.model;

import org.springframework.stereotype.Component;

/**
 * @author qiaowang
 *
 */
@Component
public class LogMessageVO {
    
    private String message;
    
    private LOG_MSG_TYPE msgType;
    
    public static enum LOG_MSG_TYPE { 
        ACCESSLOG,COMMONLOG;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LOG_MSG_TYPE getMsgType() {
        return msgType;
    }

    public void setMsgType(LOG_MSG_TYPE msgType) {
        this.msgType = msgType;
    }

}

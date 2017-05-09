package zwp.quickly.base;

import java.io.Serializable;

/**
 * <p>describe：bean的基本实体类
 * <p>    note：
 * <p>  author：zwp on 2017/4/12 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class BaseBean implements Serializable {

    /**
     * IsSuccessful : true
     * Msg : sample string 2
     * StateCode : 4
     */

    private boolean IsSuccessful;
    private String Msg;
    private int StateCode;

    public boolean isIsSuccessful() {
        return IsSuccessful;
    }

    public void setIsSuccessful(boolean IsSuccessful) {
        this.IsSuccessful = IsSuccessful;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int StateCode) {
        this.StateCode = StateCode;
    }
}

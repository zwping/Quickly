package zwp.quickly;

import zwp.quickly.utils.SDCardUtils;

/**
 * <p>describe：常用配置文件配置
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class Config {

    /* 项目中使用的本地文件地址 */
    public static final String ROOT_PATH = SDCardUtils.getSDCardPath() + "Quickly/";
    public static final String CRASH = ROOT_PATH + "Crash/";
    public static final String IMG = ROOT_PATH + "Img/";

    /* hawk保存使用的key */
    public static final String LOADING_STATUS = "LOADING_STATUS";  //登陆状态
    public static final String USER_INFO = "USER_INFO";   //用户信息
    public static final String USER_ACCOUNT = "USER_ACCOUNT"; //用户账号

}

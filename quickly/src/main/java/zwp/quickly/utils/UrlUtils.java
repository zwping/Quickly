package zwp.quickly.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * <p>describe：Url操作 and Url解析操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 *     <tr>
 *         <th>
 *             url编码 {@link #encode(String)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             url解码 {@link #decode(String)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             解析URL(协议名称/主机名/端口号/路径/参数对) {@link #UrlUtils(String)}
 *         </th>
 *     </tr>
 * </table>
 */

public class UrlUtils {

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * url编码
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, CHARSET_NAME);
    }

    /**
     * url解码
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, CHARSET_NAME);
    }

    /**
     * Url
     */
    private String mUrl;

    /**
     * 协议
     */
    private String mProtocol;

    /**
     * 主机
     */
    private String mHost;

    /**
     * 端口
     */
    private int mPort = -1;

    /**
     * 路径
     */
    private String mPath;

    /**
     * 参数
     */
    private HashMap<String, String> mParamMap;

    @Deprecated
    private UrlUtils() {
    }

    public UrlUtils(String url) {
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException("url is illegal!");
        }
        mUrl = url;
        startParse();
    }

    /**
     * 解析URL
     */
    private void startParse() {
        String[] parts = mUrl.split("[?]"); // 以？分成两部分
        if (parts != null && parts.length > 0) {
            String[] entity = parts[0].split("://");  // 以://区分协议和主机、路径部分
            if (entity != null && entity.length > 1) {
                mProtocol = entity[0];  // 协议
                String f = entity[1];
                if (f.contains(":")) {  // 包含端口
                    mHost = f.substring(0, f.indexOf(":"));
                    if (f.contains("/")) {  // 包含路径
                        mPort = Integer.parseInt(f.substring(f.indexOf(":") + 1, f.indexOf("/")));
                        mPath = f.substring(f.indexOf("/"));
                    } else {    // 不包含路径
                        mPort = Integer.parseInt(f.substring(f.indexOf(":") + 1));
                        mPath = "";
                    }
                } else {    // 不包含端口
                    if (f.contains("/")) {  // 包含路径
                        mHost = f.substring(0, f.indexOf("/"));
                        mPath = f.substring(f.indexOf("/"));
                    } else {    // 不包含路径
                        mHost = f;
                        mPath = "";
                    }
                }
            }
            if (parts.length > 1) { // 有参数
                String[] params = parts[1].split("[&]");    // 以&区分参数
                if (params != null && params.length > 0) {
                    mParamMap = new HashMap<String, String>(params.length);
                    for (String param : params) {   // 遍历参数对
                        if (!param.contains("=")) {
                            continue;
                        }
                        String[] s = param.split("[=]");
                        if (s != null) {
                            if (s.length == 2) {    // 取出参数键值对
                                String v = s[1];
                                if (v.contains("#")) {  // 参数值去掉#部分
                                    v = v.substring(0, v.indexOf("#"));
                                }
                                mParamMap.put(s[0], v);
                            } else {
                                if (s.length == 1) {    // 无参数值附空值
                                    mParamMap.put(s[0], null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取协议名称
     *
     * @return
     */
    public String getProtocol() {
        return mProtocol;
    }

    /**
     * 获取主机名
     *
     * @return
     */
    public String getHost() {
        return mHost;
    }

    /**
     * 获取端口号
     *
     * @return
     */
    public int getPort() {
        return mPort;
    }

    /**
     * 获取路径
     *
     * @return
     */
    public String getPath() {
        return mPath;
    }

    /**
     * 获取参数对
     *
     * @return
     */
    public HashMap<String, String> getParams() {
        return mParamMap;
    }


}

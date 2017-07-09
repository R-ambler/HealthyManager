package Comm;

/**
 * Created by Administrator on 06/01 0001.
 */
public class CommVariable {

    //用户信息类实例，用来保存用户登录信息
    public static final UserInfo userInfo = new UserInfo();
    //Service服务端相关参数
    public static final String serverIP = "http://192.168.191.1:8080";
    public static final String serverURL = serverIP+"/HealthyManager/AppService";
    public static final String serverEncoding = "UTF-8";
    public static final String serverRequestMethod = "POST";

    //SMS短信验证码相关参数
    public static final String smsAccount = "18829027594";      //API账号
    public static final String smsPassword = "wxq854019134";    //API密码
    public static final String smsSendURL = "http://sms.tehir.cn/code/sms/api/v1/send";     //发送验证码访问路径
    public static final String smsGroupSendURL = "http://sms.tehir.cn/code/sms/api/v2/send"; //群发验证码访问路径（可自定义模板）
    public static final String smsGetURL = "http://sms.tehir.cn/code/rcode/v1/get";         //获取验证码访问路径
    public static final String smsVerifyURL = "http://sms.tehir.cn/code/rcode/v1/verify";   //校验验证码访问路径
    public static final int smsEffectiveTime = 300;             //验证码有效时长（秒）
    public static final int smsCodeLength = 6;                  //验证码长度，最长为9
    public static final int smsSendCodeAgainTime = 60;          //再次发送验证码间隔时长（秒）
    //短信模板
    public static String getNoteContent(String code){
        String content = "您的验证码是："+code+"，"+smsEffectiveTime/60+"分钟内输入有效。";
        return content;
    }
}

package Comm;

/**
 * 用户信息类——用来保存用户登录信息
 * Created by Administrator on 06/07 0007.
 */
public class UserInfo {

    private String userName;        //用户名
    private String phoneNumber;     //手机号
    private String password;        //登录密码
    private long userNo;          //用户编号
    private String lastLogTime;     //上次登录时间

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public long getUserNo() {
        return userNo;
    }

    public void setLastLogTime(String lastLogTime) {
        this.lastLogTime = lastLogTime;
    }

    public String getLastLogTime() {
        return lastLogTime;
    }

    //赋值
    public void setVal(long userNo, String userName, String phoneNumber, String password, String lastLogTime) {
        this.userNo = userNo;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.lastLogTime = lastLogTime;
    }

    //清空
    public void clear() {
        this.userNo = -1;
        this.userName = "";
        this.phoneNumber = "";
        this.password = "";
        this.lastLogTime = "";
    }
}

package cn.stylefeng.guns.modular.vsshop.model;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianyinlin
 * @since 2019-03-09
 */
@TableName("vss_user")
public class VssUser extends Model<VssUser> {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.INPUT)
    private String uuid;
    /**
     * 1.csdn
     */
    private String source;
    private String username;
    private String password;
    private String coin;
    private String cookie;
    private String type;
    /**
     * 1登录成功，2登录失败
     */
    @TableField("login_status")
    private String loginStatus;
    /**
     * 1，正常，2禁用
     */
    private String status;

    @TableField("last_login_time")
    private Date lastLoginTime;

    @TableField("insert_time")
    private Date insertTime;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "VssUser{" +
        ", uuid=" + uuid +
        ", source=" + source +
        ", username=" + username +
        ", password=" + password +
        ", cookie=" + cookie +
        ", loginStatus=" + loginStatus +
        ", status=" + status +
        ", lastLoginTime=" + lastLoginTime +
        ", insertTime=" + insertTime +
        "}";
    }
}

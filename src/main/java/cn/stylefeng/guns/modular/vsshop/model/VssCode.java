package cn.stylefeng.guns.modular.vsshop.model;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianyinlin
 * @since 2019-03-09
 */
@TableName("vss_code")
public class VssCode extends Model<VssCode> {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.INPUT)
    @Excel(name = "uuid", needMerge = true)
    private String uuid;

    @NotBlank(message = "请输入激活码")
    @Excel(name = "激活码", needMerge = true)
    private String code;
    /**
     * down_url，下载地址
     */
    @NotBlank(message = "请输入下载地址")
    @TableField("down_url")
    @Excel(name = "下载地址", needMerge = true)
    private String downUrl;

    @TableField("file_path")
    @Excel(name = "文件地址", needMerge = true)
    private String filePath;

    @Excel(name = "积分", needMerge = true)
    private String coin;
    /**
     * 1:未使用，2已使用，3作废
     */
    @Excel(name = "状态", needMerge = true)
    private String status;
    private String ip;
    private String name;
    private String tel;
    private String wx;
    private String email;
    /**
     * 1、csdn
     */
    @Excel(name = "网站", needMerge = true)
    private String source;
    /**
     * 1、normal,2,大积分
     */
    @Excel(name = "类型", needMerge = true)
    private String type;
    private String owner;
    @Excel(name = "生成时间", needMerge = true,exportFormat =DatePattern.NORM_DATETIME_PATTERN )
    @TableField("insert_time")
    private Date insertTime;
    @Excel(name = "使用时间", needMerge = true,exportFormat =DatePattern.NORM_DATETIME_PATTERN)
    @TableField("used_time")
    private Date usedTime;
    @Excel(name = "更新时间", needMerge = true,exportFormat =DatePattern.NORM_DATETIME_PATTERN)
    @TableField("update_time")
    private Date updateTime;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "VssCode{" +
        ", uuid=" + uuid +
        ", code=" + code +
        ", downUrl=" + downUrl +
        ", status=" + status +
        ", ip=" + ip +
        ", name=" + name +
        ", tel=" + tel +
        ", wx=" + wx +
        ", email=" + email +
        ", source=" + source +
        ", insertTime=" + insertTime +
        ", usedTime=" + usedTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

package com.frame.model.bmemberwx;

import java.util.Date;

public class BMemberwx {
    private Integer weixinId;

    private Integer memberId;

    private String openId;

    private String mobile;

    private String isbound;

    private Date createTime;

    private Date updateTime;

    private String iscanced;

    private String code;

    private String imgurl;

    public Integer getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(Integer weixinId) {
        this.weixinId = weixinId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsbound() {
        return isbound;
    }

    public void setIsbound(String isbound) {
        this.isbound = isbound;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIscanced() {
        return iscanced;
    }

    public void setIscanced(String iscanced) {
        this.iscanced = iscanced;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
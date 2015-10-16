package com.frame.model.bcollections;

import java.util.Date;

import com.frame.model.bpublisher.BPublisher;

public class BCollections {
    private Integer collectId;

    private Integer collecteUser;

    private Integer publishId;

    private Date createTime;

    private String isCancel;
    
    private BPublisher bPublisher;
    
    
    

    public BPublisher getbPublisher() {
		return bPublisher;
	}

	public void setbPublisher(BPublisher bPublisher) {
		this.bPublisher = bPublisher;
	}

	public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getCollecteUser() {
        return collecteUser;
    }

    public void setCollecteUser(Integer collecteUser) {
        this.collecteUser = collecteUser;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }
}
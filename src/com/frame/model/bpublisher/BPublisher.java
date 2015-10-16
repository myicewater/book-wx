package com.frame.model.bpublisher;

import java.util.Date;

import com.frame.model.bbook.BBook;
import com.frame.model.bmember.BMember;

public class BPublisher {
    private Integer publishId;

    private Integer memberId;

    private Integer bookId;

    private Date createTime;

    private String status;

    private Date lendTime;

    private Date lendEndTime;
    
    private Integer collectedCount;
    
    private BBook book;
    
    private BMember member;
    
    private Integer lendDays;
    
    private Double lendFee;
    
     
    
    

    

	public Double getLendFee() {
		return lendFee;
	}

	public void setLendFee(Double lendFee) {
		this.lendFee = lendFee;
	}

	public Integer getCollectedCount() {
		return collectedCount;
	}

	public void setCollectedCount(Integer collectedCount) {
		this.collectedCount = collectedCount;
	}

	

	public Integer getLendDays() {
		return lendDays;
	}

	public void setLendDays(Integer lendDays) {
		this.lendDays = lendDays;
	}

	public BBook getBook() {
		return book;
	}

	public void setBook(BBook book) {
		this.book = book;
	}

	public BMember getMember() {
		return member;
	}

	public void setMember(BMember member) {
		this.member = member;
	}

	public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLendTime() {
        return lendTime;
    }

    public void setLendTime(Date lendTime) {
        this.lendTime = lendTime;
    }

    public Date getLendEndTime() {
        return lendEndTime;
    }

    public void setLendEndTime(Date lendEndTime) {
        this.lendEndTime = lendEndTime;
    }
}
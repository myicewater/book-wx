package com.frame.model.border;

import java.util.Date;

import com.frame.model.bmember.BMember;
import com.frame.model.bpublisher.BPublisher;

public class BOrder {
    private Integer orderId;

    private Integer borrower;

    private Integer publisherId;

    private String orderNo;

    private Date createTime;

    private Date returnDate;

    private String getType;

    private Double rent;

    private Double cashOff;

    private Double freight;

    private Double discount;

    private String address;

    private String username;

    private String phone;

    private String status;

    private Integer borrowDays;
    
    private BPublisher bPublisher;
    
    private BMember borrowMember;
    
    
    

    public BMember getBorrowMember() {
		return borrowMember;
	}

	public void setBorrowMember(BMember borrowMember) {
		this.borrowMember = borrowMember;
	}

	public BPublisher getbPublisher() {
		return bPublisher;
	}

	public void setbPublisher(BPublisher bPublisher) {
		this.bPublisher = bPublisher;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getGetType() {
        return getType;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getCashOff() {
        return cashOff;
    }

    public void setCashOff(Double cashOff) {
        this.cashOff = cashOff;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(Integer borrowDays) {
        this.borrowDays = borrowDays;
    }
}
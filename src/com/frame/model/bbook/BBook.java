package com.frame.model.bbook;

import java.util.Date;
import java.util.List;

import com.frame.model.bauthor.BAuthor;

public class BBook {
    private Integer bookId;

    private String subtitle;

    private String isbn;

    private String author;

    private String originTitle;

    private String binding;

    private Integer pages;

    private String imagesSmall;

    private String imagesLarge;

    private String imagesMedium;

    private String publisher;

    private String title;

    private String authorIntro;

    private String summary;

    private Double price;

    private String beizhu;

    private Date createTime;

    private String isPass;
    
    private List<BAuthor> bauthors;
    
    
    

   

	public List<BAuthor> getBauthors() {
		return bauthors;
	}

	public void setBauthors(List<BAuthor> bauthors) {
		this.bauthors = bauthors;
	}

	public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getImagesSmall() {
        return imagesSmall;
    }

    public void setImagesSmall(String imagesSmall) {
        this.imagesSmall = imagesSmall;
    }

    public String getImagesLarge() {
        return imagesLarge;
    }

    public void setImagesLarge(String imagesLarge) {
        this.imagesLarge = imagesLarge;
    }

    public String getImagesMedium() {
        return imagesMedium;
    }

    public void setImagesMedium(String imagesMedium) {
        this.imagesMedium = imagesMedium;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    

    public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }
}
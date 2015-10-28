package com.frame.model.bcredit;

import java.util.Date;

public class BCreditScoreChangeInfo {
    private Integer changeId;

    private Integer memberId;

    private Integer changeScore;

    private String changeDes;
    //变更描述：0（注册增加100） 1（发布加10） 2（借书减5） 3（借出去加5） 4（还书过期一天减少2） 
    //5 （被关注加5分） 6（书籍被收藏加1） 7（评论图书加3）

    private Date createTime;

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getChangeScore() {
        return changeScore;
    }

    public void setChangeScore(Integer changeScore) {
        this.changeScore = changeScore;
    }

    public String getChangeDes() {
        return changeDes;
    }

    public void setChangeDes(String changeDes) {
        this.changeDes = changeDes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
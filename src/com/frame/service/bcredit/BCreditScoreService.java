package com.frame.service.bcredit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.bcredit.BCreditScoreChangeInfoMapper;
import com.frame.dao.bcredit.BCreditScoreMapper;
import com.frame.model.bcredit.BCreditScore;
import com.frame.model.bcredit.BCreditScoreChangeInfo;

@Service
@Transactional
public class BCreditScoreService {

	@Autowired
	private BCreditScoreChangeInfoMapper bCreditScoreChangeInfoMapper;
	
	
	@Autowired
	private BCreditScoreMapper bCreditScoreMapper;
	
	/**
	 * 注册编辑信用
	 * @return
	 */
	public int regEditScore(Integer memberId){
		BCreditScore creditScroe = new BCreditScore();
		creditScroe.setCreditScore(100);
		creditScroe.setMemberId(memberId);
		int r1 = bCreditScoreMapper.insert(creditScroe);
		
		BCreditScoreChangeInfo change = new BCreditScoreChangeInfo();
		change.setChangeDes("0");
		change.setChangeScore(100);
		change.setCreateTime(new Date());
		change.setMemberId(memberId);
		
		int r2 = bCreditScoreChangeInfoMapper.insert(change);
		
		
		return r2;
	}
	
	
	/**
	 * 变更描述：0（注册增加100） 1（发布加10） 2（借书减5） 3（借出去加5）
	 *  4（还书过期一天减少2） 5 （被关注加5分） 6（书籍被收藏加1） 7（评论图书加3）
	 *  
	 *  score:分数
	 *  changeDes:变更描述
	 * @return
	 */
	public int editCreditScore(Integer memberId,Integer score,String changeDes){
		
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("score", score);
		
		bCreditScoreMapper.updateUserScore(map);
		
		BCreditScoreChangeInfo change = new BCreditScoreChangeInfo();
		change.setChangeDes(changeDes);
		change.setChangeScore(score);
		change.setCreateTime(new Date());
		change.setMemberId(memberId);
		
		bCreditScoreChangeInfoMapper.insert(change);
		return 1;
		
	}
}

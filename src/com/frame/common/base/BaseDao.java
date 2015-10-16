package com.frame.common.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.frame.common.util.page.PageResult;


/**
 * 基类dao
 * @author jianyu
 *
 */
public class BaseDao extends SqlSessionDaoSupport {
	/**
	 * 分页查询的每页最大数量，默认为15
	 */
	protected final int pageSize = 15;
	protected static Logger logger = null;
	protected static String className = null;
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	public BaseDao() {
		super();
		className = getClass().getName();
		logger = Logger.getLogger(className);
	}

	/**
	 * 查询单条数据
	 * @param sql
	 * @param object
	 * @return
	 */
	protected Object find(String sqlId, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		return getSqlSession().selectOne(sqlId, param);
	}

	/**
	 * 查询多条数据
	 * @param sql
	 * @param object
	 * @return
	 */
	protected List queryList(String sqlId, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		return getSqlSession().selectList(sqlId, param);
	}
	
	/**
	 * 分页查询,可自定义显示条数
	 * @param sqlId
	 * @param object
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected PageResult queryPageResult(String sqlId, Object param, Integer pageNum , Integer pageSize) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		List list = getSqlSession().selectList(sqlId, param, new RowBounds(pageNum, pageSize));
		PageResult pageResult = new PageResult( pageNum , Integer.parseInt(new PageInfo(list).getTotal()+""), pageSize , list );
		return pageResult;
	}
	/**
	 * 分页查询
	 * @param sqlId
	 * @param object
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected PageResult queryPageResult(String sqlId, Object param, Integer pageNum) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		List list = getSqlSession().selectList(sqlId, param, new RowBounds(pageNum, pageSize));
		PageResult pageResult = new PageResult( pageNum , Integer.parseInt(new PageInfo(list).getTotal()+""), pageSize , list );
		return pageResult;
	}
	
	/**
	 * 新增
	 * @param sqlId
	 * @param object
	 * @return 新增条数
	 */
	protected int insert(String sqlId, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		return getSqlSession().insert(sqlId,param);
	}
	
	/**
	 * 更新
	 * @param sqlId
	 * @param object
	 * @return 更新条数
	 */
	protected int update(String sql, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		return getSqlSession().update(sql,param);
	}
	
	/**
	 * 删除
	 * @param sqlId
	 * @param object
	 * @return 删除条数
	 */
	protected int delete(String sql, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		return getSqlSession().delete(sql,param);
	}

	protected Map execProc4Map(String sql, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		Map mp = (Map) getSqlSession().selectOne(sql, param);
		getSqlSession().clearCache(); // 防止存储过程导致数据改变，myibats仍从缓存中取
		return mp;
	}

	protected int query4Int(String sql, Object param) {
		logger.info("--------------------------------------------------------------");
		logger.info("--------------------------------------------------------------");
		int s = (Integer) getSqlSession().selectOne(sql, param);
		getSqlSession().clearCache(); // 防止myibats仍从缓存中取;
		return s;
	}

}

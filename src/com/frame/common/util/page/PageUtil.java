package com.frame.common.util.page;

public class PageUtil {
	/**
	 * 
	 * 功能说明: 根据总记录数和每页显示记录数修正当前页号.
	 * @param totalRowSize 总记录数,不能小于0.
	 * @param perPageRowSize 每页显示记录数,不能小于或等于0.
	 * @param curPage  当前页
	 * @return 当前页号.
	 * 作者: 肖建宇
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
	 *
	 */
	public static int repairCurPage(int totalRowSize,int perPageRowSize,int curPage){
		validate(totalRowSize,perPageRowSize);
		int pageSize = totalRowSize / perPageRowSize;
	
		if(totalRowSize%perPageRowSize != 0 || totalRowSize == 0){
			pageSize ++;
		}
		if(curPage > pageSize){
			curPage = pageSize;
		}else if(curPage < 1){
			curPage = 1;
		}else{
		}
		return curPage;
	}
	
	private static void validate(int totalRowSize,int perPageRowSize){
		if(totalRowSize <0){
			throw new RuntimeException("参数 totalRowSize 不能小于0.");
		}
		if(perPageRowSize <= 0){
			throw new RuntimeException("参数 perPageRowSize 不能小于或等于0.");
		}
	}
	
	
	
	/**
	 * 
	 * 功能说明: 主要用于数据库分页,
	 * 根据总记录数,每页显示记录数,当前页号计算出开始行号.
	 * (行号从0开始).
	 * @param totalRowSize 总记录数,不能小于0.
	 * @param perPageRowSize 每页显示记录数,不能小于或等于0.
	 * @param curPage  当前页
	 * @return 数据库分页的开始行号.
	 * 作者: 肖建宇
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
	 *
	 */
	public static int getStartRow(int totalRowSize,int perPageRowSize,int curPage){
		return (repairCurPage(totalRowSize,perPageRowSize,curPage) - 1) * perPageRowSize;
	}
	
	/**
	 * 
	 * 功能说明: 根据总记录数,每页显示记录数,计算页数.
	 * (行号从0开始).
	 * @param totalRowSize 总记录数,不能小于0.
	 * @param perPageRowSize 每页显示记录数,不能小于或等于0.
	 * @return 总页数.
	 * 作者: 肖建宇
	 * 创建日期:
	 *
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
	 *
	 */
	public static int getPageTotalSize(int totalRowSize,int perPageRowSize){
		validate(totalRowSize,perPageRowSize);
		int pageSize = totalRowSize/perPageRowSize;
		if(totalRowSize % perPageRowSize != 0 || totalRowSize == 0){
			pageSize ++ ;
		}
		return pageSize;
	}
}

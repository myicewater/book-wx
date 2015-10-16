package com.frame.common.util;

import jodd.util.StringUtil;

/**
	 * 
	 * 功能说明:用于获取文件的全路径。
	 * 作者:xiaojianyu
	 * 创建日期:20111102
	 *
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
 */
public class FilePathUitl {
	
	/**
	 * 
	 * 功能说明:用于获取upload下文件的全路径。
	 * @param fileName 传入文件名称。
	 * @return String 返回全路径
	 * 作者:xiaojianyu
	 * 创建日期:20111102
	 *
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
	 */
	public static String getUploadDirPath(String fileName){
		if(StringUtil.isBlank(fileName)){
			return null;
		}else{
			String root = PropUtil.getValue("fileupload.uploadDir");
			String fullPath = "";
			if(StringUtil.isBlank(root)){
				fullPath = fileName;
			}else{
				fullPath = root+fileName;
			}
			return fullPath;
		}
	}
	
	/**
	 * 
	 * 功能说明:用于获取临时文件下文件的全路径。
	 * @param fileName 传入文件名称。
	 * @return String 返回全路径
	 * 作者:xiaojianyu
	 * 创建日期:20111102
	 *
	 * 修改人：
	 * 修改日期:
	 * 修改内容:
	 */
	public static String getTempUploadPathDirPath(String fileName){
		if(StringUtil.isBlank(fileName)){
			return null;
		}else{
			String root = PropUtil.getValue("fileupload.tempUploadPathDir");
			String fullPath = "";
			if(StringUtil.isBlank(root)){
				fullPath = fileName;
			}else{
				fullPath = root+fileName;
			}
			return fullPath;
		}
	}
}

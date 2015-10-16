package com.frame.common.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frame.common.util.FileUtil;
import com.frame.common.util.StringUtil;

abstract public class BaseController {

	// private Logger log =


	public BaseController() {
	}

	/**
	 * 
	 * 功能说明:把文件从临时文件夹中移动到目标文件夹中
	 * 
	 * @param targetDir
	 *            相对的目标文件夹路径
	 * @param newFileName
	 *            文件的名称 作者:肖建宇 修改人： 修改日期: 修改内容:
	 * @throws IOException
	 * 
	 */
	protected void moveFileIntoTargetDirFromTempUploadDir(String targetDir,
			String newFileName) throws IOException {
		FileUtil.moveFileIntoTargetDirFromTempUploadDir(targetDir, newFileName);
	}

	/**
	 * ajax回应信息 作者:肖建宇
	 * 
	 * @param response
	 * @param context
	 */
	public void returnJson(String context,HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(context);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 
	 * 功能说明: 去掉params中每个元素内容两端空格。
	 * 
	 * @param params
	 * @return 作者:肖建宇 修改人： 修改日期: 修改内容:
	 * 
	 */
	protected <T> T trim(T obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Map) {
			Map params = (Map) obj;
			Set keySet = params.keySet();
			for (Iterator itor = keySet.iterator(); itor.hasNext();) {
				Object key = itor.next();
				Object value = params.get(key);
				if (value instanceof String) {
					params.put(key, StringUtil.trim((String) value));
				} else {
				}
			}
			return (T) params;

		} else if (obj instanceof String) {
			String objVal = StringUtil.trim((String) obj);
			return (T) objVal;
		} else {
			Class clazz = obj.getClass();
			// 得到对象的所有方法
			Method[] objMethod = clazz.getMethods();
			for (Method m : objMethod) {
				// 得到方法名
				String methodName = m.getName();
				// 如果以get开头
				if (methodName != null && methodName.startsWith("get")) {
					try {
						// 得到属性值
						Object value = m.invoke(obj);
						// 如果属性值不等于空并且是String对象
						if (value != null && value instanceof java.lang.String) {
							// 把属性值去掉前后空格
							value = StringUtil.trim((String) value);
							// 得到get后面的字段字符串
							String temp = methodName.substring(3,
									methodName.length());
							// set+截取的字段字符串（如：set+Bdid）
							String setMethodName = "set" + temp;
							Method setMethod = clazz.getMethod(setMethodName,
									String.class);
							if (setMethod == null) {
							} else {
								// 把属性值set回对象里面
								setMethod.invoke(obj, (String) value);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 返回重新赋值的对象
			return obj;
		}
	}

	/**
	 * 从request中获得参数Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getParamMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		while (entries.hasNext()) {
			String value = "";
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value += values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
}

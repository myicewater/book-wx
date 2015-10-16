package com.frame.common.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.frame.common.base.BaseController;
import com.frame.common.util.FileUtil;
import com.frame.common.util.JsonUtil;
import com.frame.common.util.PropUtil;

@Controller
@RequestMapping("/UploadAction.do")
public class Uploadcontroller extends BaseController{
	private static final String tempUploadPathDir = FileUtil.repairWebDirPath(PropUtil.getValue("fileupload.tempUploadPathDir", "tempUpload/"));
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=uploadFile")
	public void uploadFile(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
		List<MultipartFile> files=request.getFiles("uploadFile");  
		String webRoot = request.getContextPath() + "/";
		if(files != null){
			//定义返回的附件List
			List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
			MultipartFile mf = null;
			String oldFileName = null;
			String contentType = null;
			Map<String,String> fileNameMap = null;
			for(int i = 0 ; i < files.size() ; i++){
				String id = UUID.randomUUID().toString().replaceAll("-","");
				mf = files.get(i);
				oldFileName = mf.getOriginalFilename();
				contentType = mf.getContentType();
				fileNameMap = new HashMap<String,String>();
				String newFileName = FileUtil.putFileIntoTempUploadDir(mf, oldFileName);
				fileNameMap.put("id", id);
				fileNameMap.put("newFileName", newFileName);
				fileNameMap.put("oldFileName", oldFileName);
				fileNameMap.put("fileContentType", contentType);
				fileNameMap.put("tempPath", webRoot + tempUploadPathDir);
				//rsList.add(fileNameMap);
			}
			super.returnJson(JsonUtil.toJson(fileNameMap) , response);
		}else{
			super.returnJson("" , response);
		}
	}
}

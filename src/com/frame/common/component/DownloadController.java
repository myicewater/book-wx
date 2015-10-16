package com.frame.common.component;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.common.base.BaseController;
import com.frame.common.util.FileUtil;

/**
 * @author 
 *
 */
@Controller
@RequestMapping("/DownloadAction.do")
public class DownloadController extends BaseController{
	
	/**下载文件
	 * @param fileName
	 * @param path
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=downloadFile")
	public void downloadFile(HttpServletRequest request
			,HttpServletResponse response) throws Exception{
		String upload = FileUtil.getUploadDir();
		String newFileName = request.getParameter("newFileName");
		String mPath = request.getParameter("mPath");
		String path = FileUtil.getWebRootPath() + upload + mPath + newFileName;
		File file = new File(path);
		String fileName = request.getParameter("oldFileName");
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
	    response.addHeader("Content-Length", "" + file.length());
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		os.write(buffer);// 输出文件
		os.flush();
		os.close();
	}
}

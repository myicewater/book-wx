package com.frame.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import jodd.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	public static final String SEPARATOR= File.separator;
	private static String webRootPath = "";
	private static final String tempUploadPathDir = repairWebDirPath(PropUtil.getValue("fileupload.tempUploadPathDir", "tempUpload/"));
	private static final String uploadDir = repairWebDirPath(PropUtil.getValue("fileupload.uploadDir", "upload/"));
	private static Log log = LogFactory.getLog(FileUtil.class);
	private FileUtil(){
	}
	/**
	 * 
	 * 功能说明:根据fileFilter的条件加载dir目录下的所有file文件对象到rsCol中。
	 * @param dir 目录文件对象
	 * @param rsCol 需要装在文件对象的collection集合
	 * @param fileFilter 过滤文件对象
	 *
	 */
	public static void loadFiles(File dir, Collection rsCol,FileFilter fileFilter) {
		File subFilesList[] = dir.listFiles();
		if(subFilesList != null ){
			for(int i = 0 ; i < subFilesList.length ; i++){
				if(subFilesList[i].isDirectory()){
					loadFiles(subFilesList[i], rsCol,fileFilter);
				}else if(fileFilter.accept(subFilesList[i])){
					rsCol.add(subFilesList[i]);
				}
			}
		}else{
		}
	}
	/**
	 * 
	 * 功能说明:加载dir目录下的所有file文件对象到rsCol中。
	 * @param dir 目录文件对象
	 * @param rsCol 需要装在文件对象的collection集合
	 */
	public static void loadFiles(File dir, Collection rsCol) {
		File subFilesList[] = dir.listFiles();
		if(subFilesList != null ){
			for(int i = 0 ; i < subFilesList.length ; i++){
				if(subFilesList[i].isDirectory()){
					loadFiles(subFilesList[i], rsCol);
				}else{
					rsCol.add(subFilesList[i]);
				}
			}
		}else{
		}
	}	
	
	private static String changePathToSystemPath(String path){
		if(!StringUtil.isBlank(path)){
			if("/".equals(SEPARATOR)){
				return path.replaceAll("\\\\", SEPARATOR);
			}else{
				return path.replaceAll("/", "\\\\");
			}
		}else{
			return path;
		}
	}
	/**
	 * 
	 * 功能说明:获取项目的classpath 取到WEB-INF\classes\目录
	 * @return
	 *
	 */
	public static String getClassPathOfAbsolute(){
		Resource r = new ClassPathResource("/", FileUtil.class);
		String path = null;
		//try {
			//path = r.getFile().getAbsolutePath();
			path=FileUtil.class.getResource("FileUtil.class").toString();
			path = changePathToSystemPath(path);
			String classDir = SEPARATOR + "classes" + SEPARATOR;
			int endPoint = path.indexOf(classDir) + classDir.length();
			
			
			/** file:/C:/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/pc-20111031Node01Cell/zhiqin20110622.ear/zhidui.war/WEB-INF/ **/
			if(path.startsWith("file")){
				path= path.substring("file:\\".length(), endPoint);
			}else if(path.startsWith("jar")){ /** 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径  **/
				path = path.substring(10, endPoint);
			}else if(path.startsWith("zip")){ /** zip:/opt/bea/user_projects/domains/base_domain/./servers/AdminServer/tmp/_WL_user/syswincrm0/keszfd/war/WEB-INF/lib**/
				path = path.substring(4, endPoint);
			}
			path = path.replaceAll("%20", " ");
			log.debug("classes dir is :" + path);
			if(path.endsWith(File.separator)){
				return path;
			}else{
				return path + File.separator;
			}
		//} catch (IOException e) {
			//throw new RuntimeException(" path isn't existed.",e);
		//}
	}
	

	/**
	 * 
	 *<p>Description: 获取项目存放sql文件的目录  取到的目录是 WEB-INF\classes\config\sql\  </p>
	 *<p>createTime：2011-11-7上午09:40:39</p>
	 *<p>author:xubin </p>
	 *<p>modifyTime: </p>
	 *<p>modify by  </p>
	 *   @return
	 */
	public static String  getConfigSqlPath(){
		return repairSystemDirPath(getClassPathOfAbsolute())+"config"+SEPARATOR+"sql"+SEPARATOR;
	}
	
	/**
	 * 
	 * 功能说明:获取web目录根路径
	 * @return
	 */
	public static String getWebRootPath(){
		if(StringUtil.isBlank(webRootPath)){
			throw new NullPointerException("没有找到web根目录。");
		}else{
			if(webRootPath.endsWith("/")){
				return webRootPath;
			}else{
				return webRootPath + "/";
			}
		}
	}
	
	public static void setWebRootPath(String webRootPath){
		FileUtil.webRootPath = webRootPath;
	}
	/**
	 * 
	 * 功能说明:把文件上传到临时目录中
	 * @param file 需要上传的文件
	 * @return 文件的名称
	 */
	public static String putFileIntoTempUploadDir(MultipartFile file,String fileName) throws IOException{
		if( null == file){
			throw new NullPointerException("上传的文件 file 对象为空.");
		}
		String newFileName = getNewFileName(fileName);
		FileOutputStream fileOS = new FileOutputStream(createFile(getWebRootPath() + tempUploadPathDir , newFileName));
        fileOS.write(file.getBytes());
//        System.out.println(fileOS);
        fileOS.close();
		
		/*InputStream fis = new FileInputStream(file);
		OutputStream fos = new FileOutputStream(createFile(getWebRootPath() + tempUploadPathDir, newFileName));
		byte[] bytes = new byte[1024];
		int len = 0;
		while( (len = fis.read(bytes)) > 0){
			fos.write(bytes);
		}
		fos.close();
		fis.close();*/
		return newFileName;
	}
	
	
	/**
	 * 
	 * 功能说明:移动临时文件到目标文件夹中
	 * @param targetPath 目标路径
	 * @param fileName 目标路径+文件名称
	 * @return
	 */
	public static String moveFileIntoTargetDirFromTempUploadDir(String targetPath,String fileName) throws IOException{
		File srcFile = createFile(getWebRootPath() + tempUploadPathDir, fileName);
		File targetFile = createFile(getWebRootPath() + uploadDir + targetPath,fileName);
		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(targetFile);
		byte[] bytes = new byte[1024];
		int len = 0;
		while( (len = fis.read(bytes)) > 0){
			fos.write(bytes);
		}
		fos.close();
		fis.close();
		return targetPath+fileName;
	}
	
	/**
	 * 
	 * 功能说明:从临时文件夹中获取文件的字节数组
	 * @param fileName 文件名称
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] getFileBytesFromTempUploadDir(String fileName ) throws IOException{
		File file = createFile(getWebRootPath()+tempUploadPathDir,fileName);
		return getBytesFromFile(file);
	}
	
	/**
	 * 
	 * 功能说明:从文件对象中获取文件的字节数组
	 * @param fileName 文件对象
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException{
		if(file == null){
			return new byte[0];
		}else{
			InputStream in = new FileInputStream(file);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			in.close();
			return bytes;
		}
	}
	
	/**
	 * 
	 * 功能说明: 修复成web访问的文件夹路径
	 * @param dirPath 目录路径
	 * @return 修复后的文件路径
	 */
	public static String repairWebDirPath(String dirPath){
		dirPath = StringUtil.trimDown(dirPath);
		dirPath = changePathToWebPath(dirPath);
		if(StringUtil.isBlank(dirPath)){
			throw new NullPointerException("dirPath 不允许为空");
		}else if(dirPath.endsWith("/")){
			return dirPath;
		}else{
			return dirPath + "/";
		}
	}
	/**
	 * 
	 * 功能说明: 修复文件夹路径
	 * @param dirPath 目录路径
	 * @return 修复后的文件路径
	 */
	public static String repairSystemDirPath(String dirPath){
		dirPath = StringUtil.trimDown(dirPath);
		dirPath = changePathToSystemPath(dirPath);
		if(StringUtil.isBlank(dirPath)){
			throw new NullPointerException("dirPath 不允许为空");
		}else if(dirPath.endsWith(SEPARATOR)){
			return dirPath;
		}else{
			return dirPath + SEPARATOR;
		}
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static String changePathToWebPath(String path){
		if(StringUtil.isBlank(path)){
			return path;
		}else{
			return path.replaceAll("\\\\", "/");
		}
	}
	/**
	 * 
	 * 功能说明:获取新的文件名称 时间戳_旧的文件名称
	 * @param fileName 旧的文件名称
	 * @return 时间戳_文件名.扩展名称
	 */
	private static String getNewFileName(String fileName){
		return DateUtil.getMillisecond() + "_" + fileName;
	}
	
	/**
	 * 
	 * 功能说明:获取文件扩展名称
	 * @param fileName 旧的文件名称
	 * @return 扩展名称
	 */
	private static String getExtName(String fileName){
		if(StringUtil.isBlank(fileName)){
			return "";
		}else{
			// 获取拓展名
			if (fileName.lastIndexOf(".") >= 0) {
				return  fileName.substring(fileName.lastIndexOf("."));
			}else{
				return "";
			}
		}
	}
	
	/**
	 * 
	 * 功能说明: 获取文件的名称
	 * @param fileName 文件的相对路径+ 文件名称
	 * @return 文件的名称（去掉时间戳)
	 */
	public static String getOldFileName(String fileName){
		if(StringUtil.isBlank(fileName)){
			return "";
		}else{
			// 获取拓展名afds_fdsaf_/dfasdf_afasfd
			int point = fileName.lastIndexOf("/");
			if(point > 0){
				String tempFileName = fileName.substring(point+1,fileName.length());
				point = tempFileName.indexOf("_");
				if(point > 0){
					return tempFileName.substring(point+1,tempFileName.length());
				}else{
					return tempFileName;
				}
			}else{
				point = fileName.indexOf("_");
				if(point > 0){
					return fileName.substring(point+1,fileName.length());
				}else{
					return fileName;
				}
			}
		}
	}
	
	
	
	/**
	 * 
	 * 功能说明:创建文件
	 * @param path 路径
	 * @param fileName 文件名
	 * @return
	 */
	public static File createFile(String path,String fileName){
		if(StringUtil.isBlank(path)){
			throw new NullPointerException("path 不允许为空");
		}else{
			path = StringUtil.trimDown(path);
			createDir(path);
			if(StringUtil.isBlank(fileName)){
				return new File(path);
			}else{
				return new File(path + StringUtil.trimDown(fileName));
			}
		}
	}
	
	/**
	 * 
	 * 功能说明:如果存在目录则不创建，如果不存在则创建。
	 * @param path 相对路径
	 */
	private static void createDir(String path){
		File dir = new File(path);
		if(dir.exists()){
		}else{
			dir.mkdirs();
		}
	}
	
	public static String getTempUploadPathDir(){
		return tempUploadPathDir;
	}
	
	/**
	 * 
	 * 功能说明:获取上传文件夹相对路径
	 * @return
	 */
	public static String getUploadDir(){
		return uploadDir;
	}
	
	/**
	 * 删除指定目录下文件
	 * @param file
	 */
	public static void delete(File file) {
		if (file != null && file.exists()) {
			if (file.isFile()) {
				file.delete();
			}
			else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i=0; i<files.length; i++) {
					delete(files[i]);
				}
			}
			file.delete();
		}
	}
}

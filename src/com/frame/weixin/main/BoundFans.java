package com.frame.weixin.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.common.util.PropUtil;



/**
 * @author xiaojianyu
 */
public class BoundFans {
	private static Logger log = LoggerFactory.getLogger(BoundFans.class);
	private static String APPID = PropUtil.getValue("weixin.appid");
	private static String SECRET = PropUtil.getValue("weixin.secret");

	public static void main(String[] args) throws IOException {

		// 调用接口获取access_token
		/*AccessToken at = WeixinUtil.getAccessToken(APPID, SECRET);
		List openIdList = WeixinUtil.getFansOpenIds(at.getToken());
		try {
			if(openIdList != null && openIdList.size() > 0){
				for(int i=0; i < openIdList.size(); i++){
					String openid = (String)openIdList.get(i);
					fansAbolish(openid);
				}
				System.out.println("数据库连接成功");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public static Connection getConnection() throws SQLException {

		String driverName = "com.mysql.jdbc.Driver";
		String connectiionString = "jdbc:mysql://192.168.50.8:3306/wlctt?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&"
				+ "user=root&password=root";
		Connection connection = null;
		try {
			Class.forName(driverName);
			connection = (Connection) DriverManager
					.getConnection(connectiionString);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}

	public static void fansAbolish(String openid) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" INSERT INTO wlc_member_weixin ")
		.append("             (")
		.append("              bound_status, ")
		.append("              open_id, ")
		.append("              relevance_status, ")
		.append("              relevance_time) ")
		.append(" VALUES (")
		.append("         ?, ")
		.append("         ?, ")
		.append("         ?, ")
		.append("         ?) ");
		PreparedStatement pstmt = null;
		Connection conn = BoundFans.getConnection();
		// 下面是针对数据库的具体操作
		try {
			// 连接数据库
			pstmt = conn.prepareStatement(sqlBuf.toString());
			pstmt.setInt(1, 2);
			pstmt.setString(2, openid);
			pstmt.setInt(3, 1);
			pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
			// 进行数据库更新操作
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("操作出现异常");
		} finally {
			// 关闭数据库连接
			closeConnection(null, pstmt, conn);
		}
	}

	public static void closeConnection(ResultSet resultSet,
			PreparedStatement preparedStatement, Connection connection)
			throws SQLException {

		if (resultSet != null)
			resultSet.close();
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null && connection.isClosed() == false)
			connection.close();
		//System.out.println("数据库关闭");

	}
}

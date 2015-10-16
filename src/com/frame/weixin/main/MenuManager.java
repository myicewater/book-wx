package com.frame.weixin.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.common.util.PropUtil;
import com.frame.weixin.pojo.AccessToken;
import com.frame.weixin.pojo.Button;
import com.frame.weixin.pojo.CommonButton;
import com.frame.weixin.pojo.ComplexButton;
import com.frame.weixin.pojo.Menu;
import com.frame.weixin.pojo.ViewButton;
import com.frame.weixin.util.WeixinUtil;

/**
 * @author xiaojianyu
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	private static String APPID = PropUtil.getValue("weixinAppid");
	private static String SECRET = PropUtil.getValue("weixinSecret");
	
	public static void main(String[] args) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(APPID, SECRET);
		if (at != null) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			System.out.println(result);
			// 判断菜单创建结果
			if (0 == result){
				log.info("菜单创建成功！");
			System.out.println("菜单创建成功！");
			}else{
				log.info("菜单创建失败，错误码：" + result);
			}
		}
	}
	
	public static String getUrlByKeyWord(String keyWord){
		//获取项目路径
		String path = PropUtil.getValue("weixinServerAddr");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+path+"winxin/winxinFilter.htm?menuType="+keyWord+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
		return url;
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		
		
		ViewButton indexMenue = new ViewButton();
		indexMenue.setName("找书");
		indexMenue.setType("view");
		indexMenue.setUrl(getUrlByKeyWord("findBook"));
		
		ViewButton publishBook = new ViewButton();
		publishBook.setName("发布图书");
		publishBook.setType("view");
		publishBook.setUrl(getUrlByKeyWord("publishBook"));
		
		ViewButton userCenter = new ViewButton();
		userCenter.setName("个人中心");
		userCenter.setType("view");
		userCenter.setUrl(getUrlByKeyWord("userCenter"));
		
		/*
		ViewButton wallet_strategy = new ViewButton();
		wallet_strategy.setName("钱包攻略");
		wallet_strategy.setType("view");
		wallet_strategy.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+path+"winxin/winxinFilter.htm?menuType=kf&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
		
		
		ComplexButton click_me;
		{////生成组合菜单
			ViewButton feedback = new ViewButton();
			feedback.setName("意见反馈");
			feedback.setType("view");
			feedback.setUrl("http://www.baidu.com");
			
			ViewButton bb_question = new ViewButton();
			bb_question.setName("抱抱答疑");
			bb_question.setType("view");
			bb_question.setUrl("http://www.baidu.com");
			
			ViewButton about_us = new ViewButton();
			about_us.setName("关于我们");
			about_us.setType("view");
			about_us.setUrl("http://www.baidu.com");
			
			
			CommonButton push = new CommonButton();
			push.setKey("pushQR");
			push.setName("扫码推");
			push.setType("scancode_push");
			
			
			CommonButton pic_album = new CommonButton();
			pic_album.setKey("pic_album");
			pic_album.setName("照片");
			pic_album.setType("pic_sysphoto");
			
			click_me = new ComplexButton();
			click_me.setName("点我服务");
			click_me.setSub_button(new Button[]{feedback,bb_question,about_us});//,push,pic_album});
		}
		*/
		
		////生成最终菜单对象
		Menu menu = new Menu();
		menu.setButton(new Button[] {indexMenue,publishBook,userCenter});

		return menu;
	}
}

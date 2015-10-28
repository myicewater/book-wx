package com.frame.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReturnCode {
	/**提现中*/
	public static String withdraw_wait = "9000";
	/**操作成功*/
    public static String SUCCESS_COD = "000";
    /**处理中*/
    public static String PROCESSING_COD = "111";
    /**服务器异常*/
    public static String ERROR = "500";
    /**登录*/
    public static String LOGIN_CODE = "501";
    /**未登录*/
    public static String NOT_LOGIN_CODE = "502";
    /**登录失败请重新登陆*/
    public static String LOGIN_ERROR = "503";
    
    /**手机号格式不正确*/
    public static String MOBILE_GESHI_ERROR = "1001";
    /**手机号不能为空*/
    public static String MOBILE_IS_NOT_NULL_ERRO = "1002";
    /**验证码类型不能为空*/
    public static String VALIDATE_CODE_TYPE_IS_NOT_NULL_ERRO = "1003";
    /**该用户不存在*/
    public static String MEMBER_ISNOT_EXIST_ERRO = "1004";
    /**该用户已存在*/
    public static String MEMBER_EXIST_ERRO = "1005";
    /**验证码错误*/
    public static String VALIDATE_CODE_ERRO = "1006";
    /**手机号或验证码错误*/
    public static String MOBILE_OR_VALIDATE_CODE_ERRO = "1007";
    /**验证码不能为空*/
    public static String VALIDATE_IS_NOT_NULL_ERRO = "1008";
    /**验证码失效*/
    public static String VALIDATE_IS_LOSE = "1009";
    /**银行卡不能为空*/
    public static String BANK_CARD_IS_NOT_NULL="1010";
    /**真实姓名不能为空*/
    public static String NAME_IS_NOT_NULL="1011";
    /**购买金额不能为空*/
    public static String PURCHASE_AMOUNT_IS_NOT_NULL="1012";
    /**购买金额不能低于100*/
    public static String PURCHASE_AMOUNT_ERROR="1013";
    /**支付密码不能为空*/
    public static String PAY_PASSWORD_ERROR="1014";
    /**渠道方注册接口出错*/
    public static String CHANNEL_REGISTER_ERROR="1015";
    /**渠道方购买下单接口出错*/
    public static String CHANNEL_BY_ORDER_ERROR="1016";
    /**用户id不能为空*/
    public static String MEMBERID_IS_NOT_NULL_ERROR="1017";
    /**麻麻，您的可提现金额不足，请重新输入*/
    public static String WITHDRAW_MONEY_Not_ENOUGH="1018";
    /**你的取现金额大于100元 提现金额必须大于等于100元*/
    public static String WITHDRAW_MONEY_MORE_THAN_HUNDRED="1019";
    /**您还没有绑卡*/
    public static String NOT_BANGDINGCARD_ERROR="1020";
    /**提现失败*/
    public static String CASH_FAILER_ERROR="1021";
    /**完善银行卡信息*/
    public static String UPDATE_BANKCARD_INFO="1022";
    /**本月已经提现*/
    public static String ALREADY_CASH="1023";
    /**提现数据异常*/
    public static String CASH_DATA_ERROR="1024";
    /**没有真实投资过*/
    public static String NOT_INVEST_ERROR="1025";
    /**提现补充银行卡信息*/
    public static String WITHDRAW_BANKCARD_ERROR="1026";
    
    
    
    /**用户账号错误*/
    public static String ACCOUNT_ERROR = "2001";
    /**密码有误*/
    public static String PWD_ERROR = "2002";
    /**密码必须字母和数字组合，且长度为6到16位*/
    public static String PWD_FORMAT_ERROR = "2003";
    /**用户名或账号不能为空*/
    public static String ACCOUNT_PWD_NOTEMPTY = "2004";
    /**
     * 密码不能为空
     */
    public static String PWD_IS_NOT_NULL_ERRO = "2005";
    /**账号未激活*/
    public static String NOT_LIVE = "2006";
    /**
     * 邀请码存在
     */
    public static String INVITATION_CODE_EXIST= "2007";
    /**
     * 邀请码不存在
     */
    public static String INVITATION_CODE_NO_EXIST = "2008";
    
    /**密码不一致*/
    public static String PASSWORD_NOT_SAME = "2007";
    /**用户非法操作*/
    public static String INVALID_OP = "2008";
    /**原密码有误*/
    public static String OLD_PWD_ERROR = "2009";
    /**身份证格式错误*/
    public static String ID_CARD_FORMAT_ERROR = "2010";
    /**身份证与姓名不匹配*/
    public static String  ID_CARD_NAME_NOT_MATCHE = "2011";
    /**身份证与姓名不能为空*/
    public static String  ID_CARD_NAME_NOT_EMPTY = "2012";
    /**取现密码必须为6位数字*/
    public static String  WITHDARW_PWD_FORMAT_ERROR = "2013";
    /**取现密码与登录密码相同*/
    public static String  WITHDRAW_LOGIN_PWD_IS_SAME = "2014";
    /**取现密码错误*/
    public static String  WITHDRAW_PWD_ERROR = "2015";
    /**手机号与注册手机号不一致*/
    public static String MOBILE_IS_NOT_REGISTER_MOBILE = "2016";
    /**实名认证接口异常*/
    public static String IDENTIFYCATION_EXCEPTION = "2017";
    /**已经实名认证*/
    public static String IS_REAL = "2018";
    /**未实名认证*/
    public static String IS_NOT_REAL = "2019";
    /**未注册玖富用户*/
    public static String JF_NOT_REGISTER = "2020";
    /**还没有设置取现密码*/
    public static String WITHDRAW_PASSWARD_IS_NULL = "2021";
    /**支付失败*/
    public static String PAY_FAILED = "2022";
    /**使用了本金券，则不能再使用加息券*/
    public static String CASH_NOT_INTEREST;
    /**密码必须字母和数字组合，且长度为6到20位*/
    public static String NEW_PWD_FORMAT_ERROR = "2022";
    
    /**未设置银行卡*/
    public static String NO_SET_USER_BANK = "2022";
    /**每个用户账户购买金额最多为10万元*/
    public static String USER_ACCOUNT_IS_FULL = "2023";
    /**用户有可以使用的优惠券*/
    public static String COUPONS_IS_NOT_EMPTY = "2024";
    /**用户购买金额不能大于剩余可购买金额*/
    public static String NO_TOO_MUCH_MONEY_LEFT = "2025";
    /**银行卡校验失败，请输入合法的银行卡号*/
    public static String BANK_CHECK_FAILED = "2026";
    
    
    
    public static String getReturnMsg(String code){
    	return codeMap.get(code);
    }
    
    public static Map<String, String> codeMap = new HashMap<String, String>();
    
    public static void initErrorMap() {
    	//codeMap.put(withdraw_wait,"提现失败，此订单正在提现中");
    	
    	codeMap.put(PROCESSING_COD, "处理中");
    	codeMap.put(SUCCESS_COD, "操作成功");
    	codeMap.put(ERROR, "服务器异常");
    	codeMap.put(LOGIN_CODE, "已经登录");
    	codeMap.put(NOT_LOGIN_CODE, "未登录");
    	codeMap.put(LOGIN_ERROR, "登录失败请重新登录");
    	
    	
    	codeMap.put(MOBILE_GESHI_ERROR,"手机号格式不正确");
    	codeMap.put(MOBILE_IS_NOT_NULL_ERRO,"手机号不能为空");
    	codeMap.put(VALIDATE_CODE_TYPE_IS_NOT_NULL_ERRO,"验证码类型不能为空");
    	codeMap.put(MEMBER_ISNOT_EXIST_ERRO,"该用户不存在");
    	codeMap.put(MEMBER_EXIST_ERRO,"该用户已存在");
    	codeMap.put(VALIDATE_CODE_ERRO,"验证码错误");
    	codeMap.put(MOBILE_OR_VALIDATE_CODE_ERRO,"手机号或验证码错误");
    	codeMap.put(VALIDATE_IS_NOT_NULL_ERRO,"证码不能为空");
    	codeMap.put(VALIDATE_IS_LOSE,"验证码不存在，请重新获取验证码");
    	codeMap.put(BANK_CARD_IS_NOT_NULL,"银行卡不能为空");
    	codeMap.put(NAME_IS_NOT_NULL,"真实姓名不能为空");
    	codeMap.put(PURCHASE_AMOUNT_IS_NOT_NULL,"购买金额不能为空");
    	codeMap.put(PURCHASE_AMOUNT_ERROR,"购买金额不能低于100");
    	codeMap.put(PAY_PASSWORD_ERROR,"支付密码不能为空");
    	codeMap.put(CHANNEL_REGISTER_ERROR,"渠道方注册接口出错");
    	codeMap.put(CHANNEL_BY_ORDER_ERROR,"渠道方购买下单接口出错");
    	codeMap.put(MEMBERID_IS_NOT_NULL_ERROR,"用户id不能不能为空");
    	codeMap.put(WITHDRAW_MONEY_Not_ENOUGH,"麻麻，您的可提现金额不足，请重新输入");
    	codeMap.put(WITHDRAW_MONEY_MORE_THAN_HUNDRED,"你的取现金额大于100元 提现金额必须大于等于100元");
    	codeMap.put(NOT_BANGDINGCARD_ERROR,"您还没有绑卡");
    	codeMap.put(CASH_FAILER_ERROR,"提现失败");
    	codeMap.put(UPDATE_BANKCARD_INFO,"完善银行卡信息");
    	codeMap.put(ALREADY_CASH,"本月已经提现");
    	codeMap.put(CASH_DATA_ERROR,"提现数据异常");
    	codeMap.put(NOT_INVEST_ERROR,"没有真实投资过");
    	codeMap.put(WITHDRAW_BANKCARD_ERROR,"提现补充银行卡信息");
    	
    	codeMap.put(ACCOUNT_ERROR,"用户账号错误");
    	codeMap.put(PWD_ERROR,"密码有误");
    	codeMap.put(PWD_FORMAT_ERROR,"密码必须字母和数字组合，且长度为6到16位");
    	codeMap.put(PWD_IS_NOT_NULL_ERRO," 密码不能为空");
    	
    	codeMap.put(ACCOUNT_PWD_NOTEMPTY,"用户名或账号不能为空");
    	codeMap.put(NOT_LIVE,"账号未激活");
    	codeMap.put(PASSWORD_NOT_SAME,"密码不一致");
    	codeMap.put(INVITATION_CODE_EXIST,"邀请码存在");
    	codeMap.put(INVITATION_CODE_NO_EXIST,"邀请码不存在");
    	codeMap.put(INVALID_OP,"用户非法操作");
    	codeMap.put(OLD_PWD_ERROR,"原密码有误");
    	codeMap.put(ID_CARD_FORMAT_ERROR,"身份证格式错误");
    	codeMap.put(ID_CARD_NAME_NOT_MATCHE,"身份证与姓名不匹配");
    	codeMap.put(ID_CARD_NAME_NOT_EMPTY,"身份证与姓名不能为空");
    	codeMap.put(WITHDARW_PWD_FORMAT_ERROR, "取现密码必须为6位数字");
    	codeMap.put(WITHDRAW_LOGIN_PWD_IS_SAME, "取现密码不能与登录密码相同");
    	codeMap.put(WITHDRAW_PWD_ERROR, "取现密码错误");
    	codeMap.put(MOBILE_IS_NOT_REGISTER_MOBILE, "手机号与注册手机号不一致");
    	codeMap.put(IDENTIFYCATION_EXCEPTION, "实名认证接口异常");
    	codeMap.put(IS_REAL, "已经实名认证");
    	codeMap.put(IS_NOT_REAL, "未实名认证");
    	codeMap.put(JF_NOT_REGISTER, "未注册用户用户，不能进行下单");
    	codeMap.put(WITHDRAW_PASSWARD_IS_NULL, "还没有设置取现密码");
    	
    	codeMap.put(NEW_PWD_FORMAT_ERROR, "密码必须字母和数字组合，且长度为6到20位");
    	codeMap.put(NO_SET_USER_BANK, "未设置银行卡");
    	codeMap.put(PAY_FAILED, "支付失败啦！");
    	codeMap.put(CASH_NOT_INTEREST, "使用了本金券，就不能再使用加息券啦");
    	codeMap.put(USER_ACCOUNT_IS_FULL,"每个用户账户购买金额最多为10万元哦");
    	codeMap.put(COUPONS_IS_NOT_EMPTY,"您有可用的卡券哦");
    	codeMap.put(NO_TOO_MUCH_MONEY_LEFT, "用户购买金额不能大于剩余可购买金额哦");
    	codeMap.put(BANK_CHECK_FAILED, "银行卡校验失败，请输入合法的银行卡号");
    }
    
    
    
    public static ArrayList<String> ignoreUrls = new ArrayList<String>();
    
    /**
     * 初始化应该被忽视的请求的url，<pre>忽视规则:<font color="red">当请求url包含忽视列表中某个字符串时，就判定为该忽视的url</font></pre>
     */
    public static void initIgnoreUrls(){
    	///所有不需要登陆的请求路劲都应该添加进来
    	/*ignoreUrls.add("/showLogin");
    	ignoreUrls.add("/activity");
    	ignoreUrls.add("/activityDiscount");
    	ignoreUrls.add("/exchange");
    	ignoreUrls.add("/toCouponDiscount");
    	ignoreUrls.add("/vlidateCode");
    	ignoreUrls.add("/toCouponDesc");
    	ignoreUrls.add("/winxin");
    	
    	ignoreUrls.add("/login");
    	ignoreUrls.add("/exitSystem");
    	ignoreUrls.add("/realTimeDisplay.htm");
    	ignoreUrls.add("/login.htm");
    	ignoreUrls.add("/deposit.htm");
    	ignoreUrls.add("/babyPlanDetail.htm");
    	ignoreUrls.add("/createValidateCode.htm");
    	ignoreUrls.add("/checkValidateCode.htm");
    	ignoreUrls.add("channel/");
//    	ignoreUrls.add("/showBindingCard");
    	
    	ignoreUrls.add("/my.htm");
    	ignoreUrls.add("/my.htm?currentPage=me");
    	ignoreUrls.add("/my.htm?currentPage=pbb");
    	ignoreUrls.add("/changePassword.htm");
    	ignoreUrls.add("/aboutBaoBao.htm");
    	ignoreUrls.add("/babyPlanDetail.htm");
    	ignoreUrls.add("/baobaoQuestion.htm");
    	ignoreUrls.add("/babySafe.htm");*/
    	ignoreUrls.add("bmember/getBMemeberById.htm");
    	ignoreUrls.add("/bmember/checkInviteCode.htm");
    	ignoreUrls.add("/bmember/toRegister.htm");
    	ignoreUrls.add("/bmember/memberRegister.htm");
    	ignoreUrls.add("/bmember/toLoginPage.htm");
    	ignoreUrls.add("/bmember/login.htm");
    	ignoreUrls.add("/bmember/testPage.htm");
    	ignoreUrls.add("/bbook/toBookIndex.htm");
    	ignoreUrls.add("/winxin");
    	ignoreUrls.add("/weixin/loadWeixinMenue.htm");
    	ignoreUrls.add("/templateTest.htm");
    	
    	
    }
    
    
    public static void init(){
    	initErrorMap();
    	
    	initIgnoreUrls();
    }
}

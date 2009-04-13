package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.*;
	/**
	 * 
	 * @author 张宏志 lanfanss@126.com zhanghongzhi@baidu.com
	 * 
	 */
	public class LoginDelegate extends RemoteDelegate
	{

		public function LoginDelegate( pResponder : IResponder=null ) 
		{
			super("login", "loginService");
			responder = pResponder;
		}
		/**
		 * 向服务端发送登陆认证请求
		 */ 
		public function checkLogin( username:String, password:String ) : void 
		{
			login(new AsyncResponder(loginResult, loginFault),username,password);
		}
		/**
		 *这里相当于首先向服务端发送了登陆请求，登陆成功后，再次发送一个请求，获取用户 
		 * @param re
		 * @param token
		 * 
		 */		
		public function loginResult(re:ResultEvent, token:Object=null):void {
			var checkToken:AsyncToken =  service.checkLogin();
			checkToken.addResponder(responder);
		}
		
		public function isUserHaveLogin():void {
			var checkToken:AsyncToken =  service.checkLogin();
			checkToken.addResponder(responder);
			checkToken.operation=NotificationConstants.LOGIN;
		}
		
		public function loginFault(event:FaultEvent, token:Object=null):void {
			trace(event.message);
			Alert.show("登陆失败！");
			
		}
	}
}
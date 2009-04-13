package com.baidu.ite.mrbs.controller
{
	
	import com.baidu.ite.mrbs.ApplicationFacade;
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.LoginDelegate;
	import com.baidu.ite.mrbs.entity.MrbsUser;
	
	import mx.controls.Alert;
	import mx.logging.ILogger;
	import mx.logging.Log;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	public class LoginCommand  extends SimpleCommand implements IResponder
	{
		private static var _logger:ILogger;
       
        private static function get logger():ILogger {
            if (_logger == null)
                _logger = Log.getLogger("com.baidu.ite.mrbs.controller.LoginCommand");
            return _logger;
        }
        
		private var userToLogin:MrbsUser;
		
		override public function execute(notification:INotification):void
		{	
			userToLogin = notification.getBody() as MrbsUser ;
			new LoginDelegate(this).checkLogin(userToLogin.loginName, userToLogin.password);
		}
		
		 public function result(data:Object):void
		{
			var cureentUser:MrbsUser = data.result as MrbsUser;
			//服务端返回当前登陆的用户对象
			if(cureentUser) {
				logger.debug("登陆成功");
				//将登陆后的user设置到applicationfacade中
				var af:ApplicationFacade = ApplicationFacade.getInstance();
				af.user=cureentUser;
				sendNotification(NotificationConstants.LOGIN_VALIDATED);
			} else {
				Alert.show("对不起，您还没有登陆");
			}
		}
		
		 public function fault(info:Object):void
		{
			var fe:FaultEvent = info as FaultEvent;
			trace(fe);
			Alert.show("错误的用户名密码");
	
		}
		
	}
}
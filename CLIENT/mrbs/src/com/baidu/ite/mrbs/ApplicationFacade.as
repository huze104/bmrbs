package com.baidu.ite.mrbs
{
	import com.baidu.ite.mrbs.controller.LoginCommand;
	import com.baidu.ite.mrbs.controller.RoomThumbnailInitCommand;
	import com.baidu.ite.mrbs.controller.StartupCommand;
	import com.baidu.ite.mrbs.entity.MrbsUser;
	
	import org.puremvc.as3.interfaces.IFacade;
	import org.puremvc.as3.patterns.facade.Facade;
	/**
	 * 
	 * @author 张宏志
	 * 
	 */	
	public class ApplicationFacade extends Facade implements IFacade
	{
		
		/**
		 * 将当前登陆用户作为一个全部的变量来存储和使用
		 */ 
		private var _user:MrbsUser=null;
		
		private var _isUserLogin:Boolean;
		
		public function set user(value:MrbsUser):void {
            _user = value;
            if(user!=null){
				isUserLogin=true;
            }
        }
        
        public function get user():MrbsUser {
            return _user;
        }
		/**
		 * 判断用户是否登陆
		 * @return 用户是否登陆 
		 * 
		 */
		[Bindable]		
		public function get isUserLogin():Boolean{
			return _isUserLogin;
		}
		
		public function set isUserLogin(islogin:Boolean):void{
			this._isUserLogin=islogin;
		}
		
		
		
		/**
		 * Singleton ApplicationFacade Factory Method
		 */
		public static function getInstance() : ApplicationFacade {
			if ( instance == null ) instance = new ApplicationFacade( );
			return instance as ApplicationFacade;
		}
		
		/**
		 * Start the application
		 */
		 public function startup(app:Object):void
		 {
		 	sendNotification( NotificationConstants.STARTUP, app );	
		 }

		/**
		 * Register Commands with the Controller 
		 */
		override protected function initializeController( ) : void 
		{
			super.initializeController();		
            //当系统启动时需要进行的初始化操作，将这些操作封装到一个commond中				
			registerCommand( NotificationConstants.STARTUP, StartupCommand );
			registerCommand( NotificationConstants.ROOMTHUMB_INIT, RoomThumbnailInitCommand );
			registerCommand(NotificationConstants.LOGIN,LoginCommand);
		}
		
	}
}
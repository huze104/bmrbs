package com.baidu.ite.mrbs.model
{
	import com.baidu.ite.mrbs.ApplicationFacade;
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.LoginDelegate;
	import com.baidu.ite.mrbs.business.MrbsUserDelegate;
	import com.baidu.ite.mrbs.entity.MrbsUser;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	public class MrbsUserProxy extends Proxy implements IResponder
	{
		public static const NAME:String = "MrbsUserProxy";
		
		
		private var _mrbsUserDelegate : MrbsUserDelegate;
		private var _loginDelegate : LoginDelegate;
		
		public function MrbsUserProxy( )
		{
			super( NAME, new ArrayCollection() );
			//将需要的delegate也初始好
			_mrbsUserDelegate = new MrbsUserDelegate(this);
			_loginDelegate=new LoginDelegate(this);
		}
		
		public function result(pResult:Object) : void
		{
			var resultEvent : ResultEvent = pResult as ResultEvent;
			var mrbsUser : MrbsUser;
			var i : int;
			
			switch ( resultEvent.token.operation ) {
				case NotificationConstants.CREATE_MRBSUSER:
					mrbsUser = resultEvent.result as MrbsUser;
				 	mrbsUsers.addItem( mrbsUser );
				 	sendNotification( NotificationConstants.MRBSUSER_CREATED, mrbsUser);
					break;
				case NotificationConstants.UPDATE_MRBSUSER:
					mrbsUser = resultEvent.result as MrbsUser;
					for ( i=0; i<mrbsUsers.length; i++ ) {
						if ( mrbsUsers[i].id == mrbsUser.id ) {
			            	 mrbsUsers.setItemAt(mrbsUser, i);
						}
					}
					sendNotification( NotificationConstants.MRBSUSER_UPDATED, mrbsUsers );
					break;
				case NotificationConstants.REMOVE_MRBSUSER:
					mrbsUser = resultEvent.result as MrbsUser;
					for ( i=0; i<mrbsUsers.length; i++ ) {
						if ( mrbsUsers[i].id == mrbsUser.id ) {
			            	mrbsUsers.removeItemAt(i);
						}
					}
					sendNotification( NotificationConstants.MRBSUSER_REMOVED, mrbsUser );
					break;
				case NotificationConstants.FIND_ALL_MRBSUSERS:
					mrbsUsers.removeAll();
					var resultMrbsUsers : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsUser in resultMrbsUsers ) {
						mrbsUsers.addItem(mrbsUser);
					}	
					sendNotification( NotificationConstants.MRBSUSERS_FOUND, mrbsUsers );
					break;
				case NotificationConstants.LOGIN:
					var cureentUser:MrbsUser = resultEvent.result as MrbsUser;
					//服务端返回当前登陆的用户对象
					if(cureentUser) {
						//将登陆后的user设置到applicationfacade中
						var af:ApplicationFacade = ApplicationFacade.getInstance();
						af.user=cureentUser;
						sendNotification(NotificationConstants.LOGIN_VALIDATED);
					}
					break;	
					
			}
		}

		public function fault(pResult:Object):void
		{
			var e : FaultEvent = pResult as FaultEvent;
		}
		
		public function get mrbsUsers():ArrayCollection
		{
			return data as ArrayCollection ;
		}
		
		public function set mrbsUsers(col:ArrayCollection):void
		{
			data = col;
		}
		
		public function isUserHaveLogin():void
        {
           _loginDelegate.isUserHaveLogin();
        }
        
		public function create( mrbsUser:MrbsUser ):void
        {
           _mrbsUserDelegate.create(mrbsUser);
        }
        
        public function update( mrbsUser:MrbsUser ):void
        {
            _mrbsUserDelegate.update(mrbsUser);
        }
        
        public function remove( mrbsUser:MrbsUser ):void
        {
            _mrbsUserDelegate.remove(mrbsUser);
        }
        
        public function getAll() : void {
        	_mrbsUserDelegate.getAll();
        }

	}
}
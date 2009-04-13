package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.entity.MrbsUser;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;


	public class MrbsUserDelegate extends RemoteDelegate 
	{

		public function MrbsUserDelegate( pResponder : IResponder=null ) 
		{
			super("mrbsUser", "mrbsUserManager");
			responder = pResponder;
		}
				
		public function create( mrbsUser:MrbsUser ) : void 
		{
			var token:AsyncToken =  service.save(mrbsUser);
			token.addResponder(responder);
			token.operation = NotificationConstants.CREATE_MRBSUSER;
		}
		public function update( mrbsUser:MrbsUser ) : void 
		{
			var token:AsyncToken =  service.save(mrbsUser);
			token.addResponder(responder);
			token.operation = NotificationConstants.UPDATE_MRBSUSER;
		}
		
		/**
		 * 访问服务器，返回的结果可以在proxy中看到
		 */ 
		public function getAll() : void 
		{
			var token:AsyncToken =  service.getAll();
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSUSERS;
		}
		
		public function remove( mrbsUser:MrbsUser ) : void 
		{
			var token:AsyncToken =  service.remove(mrbsUser);
			token.addResponder(responder);
			token.operation = NotificationConstants.REMOVE_MRBSUSER;
		}
		
		public function load( id:int ) : void 
		{
			var token:AsyncToken =  service.load(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.GET_MRBSUSER;
		}
		
		
	}
}
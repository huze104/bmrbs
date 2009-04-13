package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.entity.MrbsRepeat;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;


	public class MrbsRepeatDelegate extends RemoteDelegate 
	{

		public function MrbsRepeatDelegate( pResponder : IResponder=null ) 
		{
			super("mrbsRepeat", "mrbsRepeatManager");
			responder = pResponder;
		}
				
		public function saveRepeat( mrbsRepeat:MrbsRepeat ) : void 
		{
			var token:AsyncToken =  service.saveRepeat(mrbsRepeat);
			token.addResponder(responder);
			token.operation = NotificationConstants.CREATE_MRBSREPEAT;
		}
		
		/**
		 * 访问服务器，返回的结果可以在proxy中看到
		 */ 
		public function getAll() : void 
		{
			var token:AsyncToken =  service.getAll();
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSREPEATS;
		}
		
		public function remove( mrbsRepeat:MrbsRepeat ) : void 
		{
			var token:AsyncToken =  service.remove(mrbsRepeat);
			token.addResponder(responder);
			token.operation = NotificationConstants.REMOVE_MRBSREPEAT;
		}
		
		public function load( id:int ) : void 
		{
			var token:AsyncToken =  service.load(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.GET_MRBSREPEAT;
		}
		
		
	}
}
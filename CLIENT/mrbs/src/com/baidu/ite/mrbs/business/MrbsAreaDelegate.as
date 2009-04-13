package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.entity.MrbsArea;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;


	public class MrbsAreaDelegate extends RemoteDelegate 
	{

		public function MrbsAreaDelegate( pResponder : IResponder=null ) 
		{
			super("mrbsArea", "mrbsAreaManager");
			responder = pResponder;
		}
				
		public function create( mrbsArea:MrbsArea ) : void 
		{
			var token:AsyncToken =  service.save(mrbsArea);
			token.addResponder(responder);
			token.operation = NotificationConstants.CREATE_MRBSAREA;
		}
		public function update( mrbsArea:MrbsArea ) : void 
		{
			//调用服务端的merge方法，避免以下错误
			//http://www.graniteds.org/jira/browse/GDS-103
			var token:AsyncToken =  service.merge(mrbsArea);
			token.addResponder(responder);
			token.operation = NotificationConstants.UPDATE_MRBSAREA;
		}
		
		/**
		 * 访问服务器，返回的结果可以在proxy中看到
		 */ 
		public function getAll() : void 
		{
			var token:AsyncToken =  service.getAll();
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSAREAS;
		}
		
		public function remove( mrbsArea:MrbsArea ) : void 
		{
			var token:AsyncToken =  service.remove(mrbsArea);
			token.addResponder(responder);
			token.operation = NotificationConstants.REMOVE_MRBSAREA;
		}
		
		public function load( id:int ) : void 
		{
			var token:AsyncToken =  service.load(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.GET_MRBSAREA;
		}
		
		
	}
}
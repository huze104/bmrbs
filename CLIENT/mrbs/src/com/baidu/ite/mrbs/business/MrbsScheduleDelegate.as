package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.entity.MrbsSchedule;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;


	public class MrbsScheduleDelegate extends RemoteDelegate 
	{

		public function MrbsScheduleDelegate( pResponder : IResponder=null ) 
		{
			super("mrbsSchedule", "mrbsScheduleManager");
			responder = pResponder;
		}
				
		public function create( mrbsSchedule:MrbsSchedule ) : void 
		{
			var token:AsyncToken =  service.save(mrbsSchedule);
			token.addResponder(responder);
			token.operation = NotificationConstants.CREATE_MRBSSCHEDULE;
		}
		public function update( mrbsSchedule:MrbsSchedule ) : void 
		{
			var token:AsyncToken =  service.save(mrbsSchedule);
			token.addResponder(responder);
			token.operation = NotificationConstants.UPDATE_MRBSSCHEDULE;
		}
		
		/**
		 * 访问服务器，返回的结果可以在proxy中看到
		 */ 
		public function getAll() : void 
		{
			var token:AsyncToken =  service.getAll();
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSSCHEDULES;
		}
		
		public function remove( id:Number ) : void 
		{
			var token:AsyncToken =  service.removeById(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.REMOVE_MRBSSCHEDULE;
		}
		
		public function load( id:int ) : void 
		{
			var token:AsyncToken =  service.load(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.GET_MRBSSCHEDULE;
		}
		
		
		public function loadSomeDaySchedule(roomId:Number,date:Date) : void 
		{
			var token:AsyncToken =  service.loadTodaySchedule(roomId,date);
			token.addResponder(responder);
			token.operation = NotificationConstants.LOAD_TODAY_MRBSSCHEDULES;
		}
		
		public function loadScheduleAfter(roomId:Number) : void 
		{
			var token:AsyncToken =  service.loadScheduleAfter(roomId);
			token.addResponder(responder);
			token.operation = NotificationConstants.AFTER_TODAY_MRBSSCHEDULES;
		}
		
		
	}
}
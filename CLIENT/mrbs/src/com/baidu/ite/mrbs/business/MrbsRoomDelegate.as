package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.entity.MrbsArea;
	import com.baidu.ite.mrbs.entity.MrbsRoom;
	import com.baidu.ite.mrbs.entity.util.RoomSearchBean;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;

	/**
	 * 
	 * @author zhanghognzhi@baidu.com
	 * 
	 */
	public class MrbsRoomDelegate extends RemoteDelegate 
	{
		/**
		 * 
		 * @param pResponder
		 * 
		 */
		public function MrbsRoomDelegate( pResponder : IResponder=null ) 
		{
			super("mrbsRoom", "mrbsRoomManager");
			responder = pResponder;
		}
		/**
		 * 
		 * @param mrbsRoom
		 * 
		 */				
		public function create( mrbsRoom:MrbsRoom ) : void 
		{
			var token:AsyncToken =  service.save(mrbsRoom);
			token.addResponder(responder);
			token.operation = NotificationConstants.CREATE_MRBSROOM;

		}
		/**
		 * 
		 * @param mrbsRoom
		 * 
		 */		
		public function update( mrbsRoom:MrbsRoom ) : void 
		{
			var token:AsyncToken =  service.save(mrbsRoom);
			token.addResponder(responder);
			token.operation = NotificationConstants.UPDATE_MRBSROOM;
		}
		
		/**
		 * 访问服务器，返回的结果可以在proxy中看到
		 */ 
		public function getAll() : void 
		{
			var token:AsyncToken =  service.getAll();
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSROOMS;
		}
		/**
		 * 
		 * @param mrbsRoom
		 * 
		 */		
		public function remove( mrbsRoom:MrbsRoom ) : void 
		{
			var token:AsyncToken =  service.remove(mrbsRoom);
			token.addResponder(responder);
			token.operation = NotificationConstants.REMOVE_MRBSROOM;
		}
		/**
		 * 
		 * @param id
		 * 
		 */		
		public function load( id:int ) : void 
		{
			var token:AsyncToken =  service.load(id);
			token.addResponder(responder);
			token.operation = NotificationConstants.GET_MRBSROOM;
		}
		
		/**
		 * 访问服务器，获取区域内的会议室信息，结果将会在proxy中处理
		 */ 
		public function getAreaRooms(area:MrbsArea) : void 
		{
			var token:AsyncToken =  service.getAreaRooms(area);
			token.addResponder(responder);
			token.operation = NotificationConstants.FIND_ALL_MRBSROOMS;
		}
		  
		/**
		 * 访问服务器，搜索空余会议室
		 * @param searchBean
		 * 
		 */		 
		public function searchIdleRooms(searchBean:RoomSearchBean) : void {
			var token:AsyncToken =  service.searchIdleRooms(searchBean);
			token.addResponder(responder);
			token.operation = NotificationConstants.SEARCHIDLEROOM;
		}
	}
}
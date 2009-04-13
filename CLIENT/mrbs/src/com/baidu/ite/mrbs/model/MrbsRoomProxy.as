package com.baidu.ite.mrbs.model
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.MrbsRoomDelegate;
	import com.baidu.ite.mrbs.entity.MrbsArea;
	import com.baidu.ite.mrbs.entity.MrbsRoom;
	import com.baidu.ite.mrbs.entity.util.RoomSearchBean;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	public class MrbsRoomProxy extends Proxy implements IResponder
	{
		public static const NAME:String = "MrbsRoomProxy";
		
		
		private var _mrbsRoomDelegate : MrbsRoomDelegate;
		
		public function MrbsRoomProxy( )
		{
			super( NAME, new ArrayCollection() );
			//将需要的delegate也初始好
			_mrbsRoomDelegate = new MrbsRoomDelegate(this);
		}
		
		public function result(pResult:Object) : void
		{
			var resultEvent : ResultEvent = pResult as ResultEvent;
			var mrbsRoom : MrbsRoom;
			var i : int;
			
			switch ( resultEvent.token.operation ) {
				case NotificationConstants.CREATE_MRBSROOM:
					mrbsRoom = resultEvent.result as MrbsRoom;
				 	mrbsRooms.addItem( mrbsRoom );
				 	sendNotification( NotificationConstants.MRBSROOM_CREATED, mrbsRoom);
					break;
				case NotificationConstants.UPDATE_MRBSROOM:
					mrbsRoom = resultEvent.result as MrbsRoom;
					for ( i=0; i<mrbsRooms.length; i++ ) {
						if ( mrbsRooms[i].id == mrbsRoom.id ) {
			            	 mrbsRooms.setItemAt(mrbsRoom, i);
						}
					}
					sendNotification( NotificationConstants.MRBSROOM_UPDATED, mrbsRooms );
					break;
				case NotificationConstants.REMOVE_MRBSROOM:
					mrbsRoom = resultEvent.result as MrbsRoom;
					for ( i=0; i<mrbsRooms.length; i++ ) {
						if ( mrbsRooms[i].id == mrbsRoom.id ) {
			            	mrbsRooms.removeItemAt(i);
						}
					}
					sendNotification( NotificationConstants.MRBSROOM_REMOVED, mrbsRoom );
					break;
				case NotificationConstants.FIND_ALL_MRBSROOMS:
					mrbsRooms.removeAll();
					var resultMrbsRooms : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsRoom in resultMrbsRooms ) {
						mrbsRooms.addItem(mrbsRoom);
					}	
					sendNotification( NotificationConstants.MRBSROOMS_FOUND, mrbsRooms );
					break;
				case NotificationConstants.SEARCHIDLEROOM:
					var resultMrbsRooms : ArrayCollection = resultEvent.result as ArrayCollection;
					sendNotification( NotificationConstants.SEARCHROOMFIND, resultMrbsRooms );
					break;	
			}
		}

		public function fault(pResult:Object):void
		{
			var e : FaultEvent = pResult as FaultEvent;
		}
		
		public function get mrbsRooms():ArrayCollection
		{
			return data as ArrayCollection ;
		}
		
		public function set mrbsRooms(col:ArrayCollection):void
		{
			data = col;
		}
		
		
		public function create( mrbsRoom:MrbsRoom ):void
        {
           _mrbsRoomDelegate.create(mrbsRoom);
        }
        
        public function update( mrbsRoom:MrbsRoom ):void
        {
            _mrbsRoomDelegate.update(mrbsRoom);
        }
        
        public function remove( mrbsRoom:MrbsRoom ):void
        {
            _mrbsRoomDelegate.remove(mrbsRoom);
        }
        
        public function getAll() : void {
        	_mrbsRoomDelegate.getAll();
        }
        
        /**
        * 获取到某个区域的会议室列表
        */ 
        public function getAreaRooms(area:MrbsArea) : void {
        	_mrbsRoomDelegate.getAreaRooms(area);
        }
        /**
        * 搜索空闲会议室
        */ 
        public function searchIdleRooms(searchBean:RoomSearchBean) : void {
        	_mrbsRoomDelegate.searchIdleRooms(searchBean);
        }

	}
}
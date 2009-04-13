package com.baidu.ite.mrbs.model
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.MrbsScheduleDelegate;
	import com.baidu.ite.mrbs.entity.MrbsSchedule;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	public class MrbsScheduleProxy extends Proxy implements IResponder
	{
		public static const NAME:String = "MrbsScheduleProxy";
		
		
		private var _mrbsScheduleDelegate : MrbsScheduleDelegate;
		
		public function MrbsScheduleProxy( )
		{
			super( NAME, new ArrayCollection() );
			//将需要的delegate也初始好
			_mrbsScheduleDelegate = new MrbsScheduleDelegate(this);
		}
		
		public function result(pResult:Object) : void
		{
			var resultEvent : ResultEvent = pResult as ResultEvent;
			var mrbsSchedule : MrbsSchedule;
			var i : int;
			
			switch ( resultEvent.token.operation ) {
				case NotificationConstants.CREATE_MRBSSCHEDULE:
					mrbsSchedule = resultEvent.result as MrbsSchedule;
				 	mrbsSchedules.addItem( mrbsSchedule );
				 	sendNotification( NotificationConstants.MRBSSCHEDULE_CREATED, mrbsSchedule);
					break;
				case NotificationConstants.UPDATE_MRBSSCHEDULE:
					mrbsSchedule = resultEvent.result as MrbsSchedule;
					for ( i=0; i<mrbsSchedules.length; i++ ) {
						if ( mrbsSchedules[i].id == mrbsSchedule.id ) {
			            	 mrbsSchedules.setItemAt(mrbsSchedule, i);
						}
					}
					sendNotification( NotificationConstants.MRBSSCHEDULE_UPDATED, mrbsSchedules );
					break;
				case NotificationConstants.REMOVE_MRBSSCHEDULE:
					mrbsSchedule = resultEvent.result as MrbsSchedule;
					for ( i=0; i<mrbsSchedules.length; i++ ) {
						if ( mrbsSchedules[i].id == mrbsSchedule.id ) {
			            	mrbsSchedules.removeItemAt(i);
						}
					}
					sendNotification( NotificationConstants.MRBSSCHEDULE_REMOVED, mrbsSchedule );
					break;
				case NotificationConstants.FIND_ALL_MRBSSCHEDULES:
					mrbsSchedules.removeAll();
					var resultMrbsSchedules : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsSchedule in resultMrbsSchedules ) {
						mrbsSchedules.addItem(mrbsSchedule);
					}	
					sendNotification( NotificationConstants.MRBSSCHEDULES_FOUND, mrbsSchedules );
					break;
				case NotificationConstants.LOAD_TODAY_MRBSSCHEDULES:
				    var resultCollection=new ArrayCollection();
					var resultMrbsSchedules : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsSchedule in resultMrbsSchedules ) {
						resultCollection.addItem(mrbsSchedule);
					}	
					sendNotification( NotificationConstants.MRBSSCHEDULES_TODAY_FOUND, resultCollection );
					break;
				case NotificationConstants.AFTER_TODAY_MRBSSCHEDULES:
				    var resultCollection=new ArrayCollection();
					var resultMrbsSchedules : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsSchedule in resultMrbsSchedules ) {
						resultCollection.addItem(mrbsSchedule);
					}	
					sendNotification( NotificationConstants.MRBSSCHEDULES_TODAYAFTER_FOUND, resultCollection );
					break;
			}
		}

		public function fault(pResult:Object):void
		{
			var e : FaultEvent = pResult as FaultEvent;
			trace(e);
		}
		
		public function get mrbsSchedules():ArrayCollection
		{
			return data as ArrayCollection ;
		}
		
		public function set mrbsSchedules(col:ArrayCollection):void
		{
			data = col;
		}
		
		
		public function create( mrbsSchedule:MrbsSchedule ):void
        {
           _mrbsScheduleDelegate.create(mrbsSchedule);
        }
        
        public function update( mrbsSchedule:MrbsSchedule ):void
        {
            _mrbsScheduleDelegate.update(mrbsSchedule);
        }
        
        public function remove( id:Number ):void
        {
            _mrbsScheduleDelegate.remove(id);
        }
        
        public function getAll() : void {
        	_mrbsScheduleDelegate.getAll();
        }
        /**
         *获取某天的会议室预定情况 
         * 
         */        
        public function loadSomeDaySchedule(roomId:Number,date:Date) : void {
        	_mrbsScheduleDelegate.loadSomeDaySchedule(roomId,date);
        }
        /**
         *获取从今以后会议室预定情况 
         * 
         */        
        public function loadScheduleAfter(roomId:Number) : void {
        	_mrbsScheduleDelegate.loadScheduleAfter(roomId);
        }
	}
}
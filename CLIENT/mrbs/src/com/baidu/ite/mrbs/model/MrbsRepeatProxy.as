package com.baidu.ite.mrbs.model
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.MrbsRepeatDelegate;
	import com.baidu.ite.mrbs.entity.MrbsRepeat;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	public class MrbsRepeatProxy extends Proxy implements IResponder
	{
		public static const NAME:String = "MrbsRepeatProxy";
		
		
		private var _mrbsRepeatDelegate : MrbsRepeatDelegate;
		
		public function MrbsRepeatProxy( )
		{
			super( NAME, new ArrayCollection() );
			//将需要的delegate也初始好
			_mrbsRepeatDelegate = new MrbsRepeatDelegate(this);
		}
		
		public function result(pResult:Object) : void
		{
			var resultEvent : ResultEvent = pResult as ResultEvent;
			var mrbsRepeat : MrbsRepeat;
			var i : int;
			switch ( resultEvent.token.operation ) {
				case NotificationConstants.CREATE_MRBSREPEAT:
				 	sendNotification( NotificationConstants.MRBSREPEAT_CREATED,resultEvent.result as Boolean );
					break;
				case NotificationConstants.REMOVE_MRBSREPEAT:
					mrbsRepeat = resultEvent.result as MrbsRepeat;
					for ( i=0; i<mrbsRepeats.length; i++ ) {
						if ( mrbsRepeats[i].id == mrbsRepeat.id ) {
			            	mrbsRepeats.removeItemAt(i);
						}
					}
					sendNotification( NotificationConstants.MRBSREPEAT_REMOVED, mrbsRepeat );
					break;
				case NotificationConstants.FIND_ALL_MRBSREPEATS:
					mrbsRepeats.removeAll();
					var resultMrbsRepeats : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsRepeat in resultMrbsRepeats ) {
						mrbsRepeats.addItem(mrbsRepeat);
					}	
					sendNotification( NotificationConstants.MRBSREPEATS_FOUND, mrbsRepeats );
					break;
			}
		}

		public function fault(pResult:Object):void
		{
			var e : FaultEvent = pResult as FaultEvent;
		}
		
		public function get mrbsRepeats():ArrayCollection
		{
			return data as ArrayCollection ;
		}
		
		public function set mrbsRepeats(col:ArrayCollection):void
		{
			data = col;
		}
		
		
		public function saveRepeat( mrbsRepeat:MrbsRepeat ):void
        {
           _mrbsRepeatDelegate.saveRepeat(mrbsRepeat);
        }
        
        public function remove( mrbsRepeat:MrbsRepeat ):void
        {
            _mrbsRepeatDelegate.remove(mrbsRepeat);
        }
        
        public function getAll() : void {
        	_mrbsRepeatDelegate.getAll();
        }

	}
}
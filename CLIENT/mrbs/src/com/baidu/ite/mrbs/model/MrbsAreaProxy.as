package com.baidu.ite.mrbs.model
{
	import com.baidu.ite.mrbs.NotificationConstants;
	import com.baidu.ite.mrbs.business.MrbsAreaDelegate;
	import com.baidu.ite.mrbs.entity.MrbsArea;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.puremvc.as3.patterns.proxy.Proxy;

	public class MrbsAreaProxy extends Proxy implements IResponder
	{
		public static const NAME:String = "MrbsAreaProxy";
		
		
		private var _mrbsAreaDelegate : MrbsAreaDelegate;
		
		public function MrbsAreaProxy( )
		{
			super( NAME, new ArrayCollection() );
			//将需要的delegate也初始好
			_mrbsAreaDelegate = new MrbsAreaDelegate(this);
		}
		
		public function result(pResult:Object) : void
		{
			var resultEvent : ResultEvent = pResult as ResultEvent;
			var mrbsArea : MrbsArea;
			var i : int;
			
			switch ( resultEvent.token.operation ) {
				case NotificationConstants.CREATE_MRBSAREA:
					mrbsArea = resultEvent.result as MrbsArea;
				 	mrbsAreas.addItem( mrbsArea );
				 	sendNotification( NotificationConstants.MRBSAREA_CREATED, mrbsArea);
					break;
				case NotificationConstants.REMOVE_MRBSAREA:
					mrbsArea = resultEvent.result as MrbsArea;
					for ( i=0; i<mrbsAreas.length; i++ ) {
						if ( mrbsAreas[i].id == mrbsArea.id ) {
			            	mrbsAreas.removeItemAt(i);
						}
					}
					sendNotification( NotificationConstants.MRBSAREA_REMOVED, mrbsArea );
					break;
				case NotificationConstants.FIND_ALL_MRBSAREAS:
					mrbsAreas.removeAll();
					var resultMrbsAreas : ArrayCollection = resultEvent.result as ArrayCollection;
					for each( mrbsArea in resultMrbsAreas ) {
						mrbsAreas.addItem(mrbsArea);
					}	
					sendNotification( NotificationConstants.MRBSAREAS_FOUND, mrbsAreas );
					break;
			}
		}

		public function fault(pResult:Object):void
		{
			var e : FaultEvent = pResult as FaultEvent;
		}
		
		public function get mrbsAreas():ArrayCollection
		{
			return data as ArrayCollection ;
		}
		
		public function set mrbsAreas(col:ArrayCollection):void
		{
			data = col;
		}
		
		
		public function create( mrbsArea:MrbsArea ):void
        {
           _mrbsAreaDelegate.create(mrbsArea);
        }
        
        public function update( mrbsArea:MrbsArea ):void
        {
            _mrbsAreaDelegate.update(mrbsArea);
        }
        
        public function remove( mrbsArea:MrbsArea ):void
        {
            _mrbsAreaDelegate.remove(mrbsArea);
        }
        
        public function getAll() : void {
        	_mrbsAreaDelegate.getAll();
        }

	}
}
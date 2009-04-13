package com.baidu.ite.mrbs.business
{
	import mx.collections.ArrayCollection;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.*;
	import mx.rpc.remoting.RemoteObject;
	
	public class RemoteObjectManager 
	{
		private var _remoteObjects:ArrayCollection;
		private static var _remoteObjectManager:RemoteObjectManager=new RemoteObjectManager();
		 
		public static  function getInstance():RemoteObjectManager{
		 	return _remoteObjectManager;
		}
		
		public function RemoteObjectManager()
		{
			_remoteObjects = new ArrayCollection();
		}
		
		public function createRemoteObject():RemoteObject {
			var ro:RemoteObject = new RemoteObject(); 
			_remoteObjects.addItem(ro);
			return ro;
		}
		
		public function logout():void {
			for each (var service:RemoteObject in _remoteObjects) {
				if(service.channelSet!=null) {
					var token:AsyncToken = service.channelSet.logout();
					token.addResponder(new AsyncResponder(
				    function(re:ResultEvent, token:Object=null):void {
				      trace(re);
				    },
				    function(fe:FaultEvent, token:Object=null):void {
				      trace(fe);
				    }
				  )); 
				}
			}
		}
		
	}
}
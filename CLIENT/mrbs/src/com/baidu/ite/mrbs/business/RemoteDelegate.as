package com.baidu.ite.mrbs.business
{
	import com.baidu.ite.mrbs.ApplicationFacade;
	import com.baidu.ite.mrbs.entity.MrbsUser;
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.messaging.config.ServerConfig;
	import mx.messaging.events.*;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.*;
	import mx.rpc.remoting.RemoteObject;
	
	
	/**
	 * RemoteDelegate is the base class for all delegates that deal with RemoteObject.
	 * 
	 * We user ChannelSet login/logout API instead of old methods of the RemoteObject to
	 * handle correctly logout and next login with another password.
	 * 
	 *
	 */
	public class RemoteDelegate 
	{
		protected var service : RemoteObject;
		protected var destinationId : String;
		protected var source : String;
			
		protected var _responder:IResponder;
		
		public function RemoteDelegate(destinationId:String = "", source:String = "")
		{
			this.destinationId = destinationId;
			this.source = source;
			service = RemoteObjectManager.getInstance().createRemoteObject();
			service.destination = this.destinationId;
	    	service.makeObjectsBindable = true;
	    	service.source = this.source;
		}	
			
		
		public function set responder(value:IResponder):void{
			_responder = value;
		}
		
		public function get responder():IResponder{
			return _responder;
		}
		
		public function channelFaultHandler( event:Event ) : void {
			Alert.show(event.toString());
		}
		
		public function login(pResponder : IResponder,loginName:String,password:String ):void{
			if(service.channelSet == null) {
				service.channelSet = ServerConfig.getChannelSet(this.destinationId);
			}
			var token:AsyncToken = service.channelSet.login(loginName, password);
			token.addResponder(pResponder);
		}
		
		public function handleMessageFault( event:FaultEvent ):void {
			Alert.show(event.fault.message);
		}
		
		
		public function logout() : void {	
			RemoteObjectManager.getInstance().logout();
		}

	}
}
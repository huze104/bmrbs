package com.baidu.ite.mrbs.view.mediator
{
    import com.baidu.ite.mrbs.NotificationConstants;
    import com.baidu.ite.mrbs.model.MrbsAreaProxy;
    
    import flash.events.Event;
    
    import mx.collections.ArrayCollection;
    
    import org.puremvc.as3.interfaces.*;
    import org.puremvc.as3.patterns.mediator.Mediator;
    /**
     * 
     * @author 张宏志 zhangzhongzhi@baidu.com
     * 
     */        
    public class AdminMediator extends Mediator implements IMediator
    {
    	/**
    	 *区域proxy 
    	 */    	
    	private var areaProxy:MrbsAreaProxy;
    	
        public function AdminMediator( mediatorName:String = null, viewComponent:Object = null) 
        {
            super( mediatorName, viewComponent );
            adminApp.addEventListener("update",onAreaUpdateEvent);
            areaProxy = facade.retrieveProxy( MrbsAreaProxy.NAME ) as MrbsAreaProxy;
        }
       
        override public function listNotificationInterests():Array 
        {
            return [ NotificationConstants.MRBSAREAS_FOUND];
        }

        override public function handleNotification( note:INotification ):void 
        {
            switch ( note.getName() ) 
			{
				case NotificationConstants.MRBSAREAS_FOUND:
					adminApp.areas=note.getBody() as ArrayCollection;	
					break;
            }
        }
        
        protected function get adminApp():admin
		{
            return viewComponent as admin
        }
       
        private function onAreaUpdateEvent(e:Event):void{
        	areaProxy.update(adminApp.area);
        }
	}
}
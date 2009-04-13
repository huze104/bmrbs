package com.baidu.ite.mrbs.view.mediator
{
    import com.baidu.ite.mrbs.NotificationConstants;
    import com.baidu.ite.mrbs.entity.MrbsSchedule;
    import com.baidu.ite.mrbs.model.MrbsScheduleProxy;
    import com.baidu.ite.mrbs.view.RoomThumbnail;
    
    import flash.events.Event;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    
    import org.puremvc.as3.interfaces.*;
    import org.puremvc.as3.patterns.mediator.Mediator;
    /**
     * 
     * @author 张宏志 zhangzhongzhi@baidu.com
     * 
     */           
    public class RoomThumbnailMediator extends Mediator implements IMediator
    {
    	private var scheduleProxy:MrbsScheduleProxy;
        public function RoomThumbnailMediator( mediatorName:String = null, viewComponent:Object = null) 
        {
            super( mediatorName, viewComponent );
            nail.addEventListener("loadTodaySchedule",onLoadTodayScheduleEvent);
            scheduleProxy = facade.retrieveProxy(MrbsScheduleProxy.NAME ) as MrbsScheduleProxy;
        }
       
        override public function listNotificationInterests():Array 
        {
            return [ NotificationConstants.MRBSSCHEDULES_TODAY_FOUND];
        }

        override public function handleNotification( note:INotification ):void 
        {
            switch ( note.getName() ) 
			{
				case NotificationConstants.MRBSSCHEDULES_TODAY_FOUND:
				   //shit，我发现puremvc的通知机制是基于广播机制的
				   //这个shit的逻辑会造成的事情就是当scheduleproxy拿到了查询结果以后，将会通知所有监听这个通知的mediator对象
				   //如果看到这里你还没有明白我在说什么，那么直接点，就是说，界面展现的预定结果都是一样的，是最后一个会议室的预定结果
				   //解决方案1:查查源码，（不过我这里没有没有网络，所以拿不到源码，再说吧）
				    //解决方案2
				    //抛却从facade中获取proxy的习惯，改成直接在这里New，当proxy拿到数据后，向外触发事件，而本mediator接收事件
				    //这样做的缺点就是将整个puremvc的推荐模式扔了
				    //解决方案3:最简单方案，那就是在这里写一个循环，判断数据是不是属于当前会议室的，是呢，就用，不是就算了
				    //这个最没有技术含量了,而卧选择了最后这个，大周日的，11点了.....
				    var schedules=note.getBody() as ArrayCollection;
				    var mrbsSchedule:MrbsSchedule;
				    if(schedules.length==0){
				    	return;
				    }
				    for each( mrbsSchedule in schedules ) {
				    	//说明数据是当前的
				    	if(mrbsSchedule.mrbsRoom.id==nail.room.id){
				    		//只需要判断第一个就行了
				    		break;
				    	}else{
					    	//说明不是，直接推出
					    	return;
				    	}
				    }
				    nail.scheduleSelection.dataProvider=schedules;
				break;
            }
        }
        
        protected function get nail():RoomThumbnail
		{
            return viewComponent as RoomThumbnail
        }
        /**
         * 获取今天的内容
         * @param e
         * 
         */           
        private function onLoadTodayScheduleEvent(e:Event):void{
        	//这里设计的也不怎么好
        	//这里将数据提供重置后，界面上的数据就会消失，当拿到数据后，再次填充到界面
        	nail.scheduleSelection.dataProvider=null;
        	scheduleProxy.loadSomeDaySchedule(nail.room.id,nail.currentDate);
        }
	}
}
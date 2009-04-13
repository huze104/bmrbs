package com.baidu.ite.mrbs.view.mediator
{

    
    import com.baidu.ite.mrbs.ApplicationFacade;
    import com.baidu.ite.mrbs.NotificationConstants;
    import com.baidu.ite.mrbs.entity.MrbsRepeat;
    import com.baidu.ite.mrbs.entity.MrbsSchedule;
    import com.baidu.ite.mrbs.model.MrbsAreaProxy;
    import com.baidu.ite.mrbs.model.MrbsRepeatProxy;
    import com.baidu.ite.mrbs.model.MrbsRoomProxy;
    import com.baidu.ite.mrbs.model.MrbsScheduleProxy;
    import com.baidu.ite.mrbs.view.RoomSearchEvent;
    
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
    public class IndexMediator extends Mediator implements IMediator
    {
    	/**
    	 *区域proxy 
    	 */    	
    	private var areaProxy:MrbsAreaProxy;
    	/**
    	 *scheduleproxy 
    	 */    	
    	private var scheduleProxy:MrbsScheduleProxy;
    	private var repeatProxy:MrbsRepeatProxy;
    	private var roomProxy:MrbsRoomProxy;
    	
        public function IndexMediator( mediatorName:String = null, viewComponent:Object = null) 
        {
            super( mediatorName, viewComponent );
            indexApp.addEventListener("roomsearch",onSearchRoomEvent);
            //添加登陆的事件监听
            indexApp.addEventListener(index.LOGIN,onLoginEvent);
            indexApp.roomPanel.details.addEventListener("roomchange",onRoomChangeEvent);
            indexApp.roomPanel.details.addEventListener("repeatsubmit",onRepeatsubmitEvent);
            indexApp.roomPanel.details.addEventListener("deleteschedule",onDeleteScheduleEvent);
            areaProxy = facade.retrieveProxy( MrbsAreaProxy.NAME ) as MrbsAreaProxy;
            scheduleProxy = facade.retrieveProxy( MrbsScheduleProxy.NAME ) as MrbsScheduleProxy;
            repeatProxy = facade.retrieveProxy( MrbsRepeatProxy.NAME ) as MrbsRepeatProxy;
            roomProxy = facade.retrieveProxy( MrbsRoomProxy.NAME ) as MrbsRoomProxy;
            
            
        }
       
        override public function listNotificationInterests():Array 
        {
            return [ NotificationConstants.MRBSAREAS_FOUND,
                     NotificationConstants.MRBSSCHEDULES_TODAYAFTER_FOUND,
                     NotificationConstants.MRBSREPEAT_CREATED,
                     NotificationConstants.LOGIN_VALIDATED
                      ];
        }

        override public function handleNotification( note:INotification ):void 
        {
            switch ( note.getName() ) 
			{
				case NotificationConstants.MRBSAREAS_FOUND:
					indexApp.areas=note.getBody() as ArrayCollection;	
					break;
				case NotificationConstants.MRBSSCHEDULES_TODAYAFTER_FOUND:
					indexApp.roomPanel.details.schedules=note.getBody() as ArrayCollection;	
					break;
				case NotificationConstants.LOGIN_VALIDATED:
					indexApp.username=ApplicationFacade.getInstance().user.name;
					indexApp.currentState="alreadylogin";
					break;
				case NotificationConstants.MRBSREPEAT_CREATED:
					var isSuccess=note.getBody() as Boolean;	
					//如果预定成功
					if(isSuccess){
						Alert.show("预定成功，可在列表中查看到预定结果！");
					    //将显示按钮集中在grid一栏
			            indexApp.roomPanel.details.tn.selectedIndex=0; 
			            //赋值一个新的repeat，避免再次预定时有原来的数据  
			            indexApp.roomPanel.details.repeatForm.repeat=new MrbsRepeat();
			            //将界面上的数据都重新加载一下
			            scheduleProxy.loadScheduleAfter(indexApp.roomPanel.details.room.id);
			            scheduleProxy.loadSomeDaySchedule(indexApp.roomPanel.details.room.id,indexApp.roomPanel.currentDate);
			        }else{
						Alert.show("该会议室已被预定！请更换会议室时间或预定其他会议室！");			        
			        }
					break;
					
            }
        }
        
        protected function get indexApp():index
		{
            return viewComponent as index
        }
        
        /**
         *完成界面上的搜索工作 
         * @param e
         * 
         */        
        private function onSearchRoomEvent(e:RoomSearchEvent):void{
        	roomProxy.searchIdleRooms(e.seachBean);
        }
        /**
         * 接收用户登陆的请求，并且交给commond来处理
         * @param e
         * 
         */        
        private function onLoginEvent(e:Event):void{
        	//对外触发登陆请求既可 ,由logincommand来处理具体的请求
        	sendNotification(NotificationConstants.LOGIN, indexApp.userLoin);	
        }
        /**
         * @param e
         */           
        private function onRoomChangeEvent(e:Event):void{
        	scheduleProxy.loadScheduleAfter(indexApp.roomPanel.details.room.id);
        }
        
        
        /**
         *删除grid中的schedule<br>
         * 
         * @param e
         * 
         */        
        private function onDeleteScheduleEvent(e:Event):void{
        	//其实这里是有风险的，那么就是，我将数据库删除和界面删除分开了，如果数据库没删除，而界面删除了，就会现实和事实不一致
        	var schedule:MrbsSchedule=indexApp.roomPanel.details.sheduleDataGrid.selectedItem as MrbsSchedule;
        	scheduleProxy.remove(schedule.id);
        	var schedules:ArrayCollection=indexApp.roomPanel.details.schedules;
			for (var i=0; i<schedules.length; i++ ) {
				if ( schedules[i].id == schedule.id ) {
			    	schedules.removeItemAt(i);
			    	break;
				}
			}
			indexApp.roomPanel.details.armed=false;
        }
        private function onRepeatsubmitEvent(e:Event):void{
        	repeatProxy.saveRepeat(indexApp.roomPanel.details.repeat);
        }
	}
}
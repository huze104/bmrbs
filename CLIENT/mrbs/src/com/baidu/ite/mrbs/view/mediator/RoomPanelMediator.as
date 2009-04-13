package com.baidu.ite.mrbs.view.mediator
{

   
    import com.baidu.ite.mrbs.ApplicationFacade;
    import com.baidu.ite.mrbs.NotificationConstants;
    import com.baidu.ite.mrbs.entity.MrbsArea;
    import com.baidu.ite.mrbs.model.MrbsRoomProxy;
    import com.baidu.ite.mrbs.view.RoomPanel;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.managers.CursorManager;
    
    import org.puremvc.as3.interfaces.*;
    import org.puremvc.as3.patterns.mediator.Mediator;
    /**
     * 
     * @author 张宏志 zhanghongzhi@baidu.com
     * 会议室展示panel的mediator，负责和界面的交互
     * 
     */           
    public class RoomPanelMediator extends Mediator implements IMediator
    {
    	
    	private var roomProxy:MrbsRoomProxy;
    	
        public function RoomPanelMediator( mediatorName:String = null, viewComponent:Object = null) 
        {
            super( mediatorName, viewComponent );
            roomProxy = facade.retrieveProxy(MrbsRoomProxy.NAME ) as MrbsRoomProxy;
        }
       
        override public function listNotificationInterests():Array 
        {
            return [ NotificationConstants.MRBSROOMS_FOUND,
            NotificationConstants.AREACHANGE,
            NotificationConstants.LOGIN_VALIDATED,
            NotificationConstants.SEARCHROOMFIND
            ];
        }

        override public function handleNotification( note:INotification ):void 
        {
            switch ( note.getName() ) 
			{
				case NotificationConstants.AREACHANGE:
					var area:MrbsArea=note.getBody() as MrbsArea;
					roomProxy.getAreaRooms(area);
				break;
				case NotificationConstants.SEARCHROOMFIND:
				    //首先移除忙碌标志
				    CursorManager.removeBusyCursor();
					var rooms=note.getBody() as ArrayCollection;
					if(rooms.length==0){
					  //没有找到
					  Alert.show("对不起，似乎没有搜索到可用的会议室！");	
					  break;
					}
					roomPanel.roomPanelTitle="以下搜索结果为可供选择的会议室和时间 请尽快预定！";
					roomPanel.isSearch=true;
					roomPanel.rooms=rooms;
					roomPanel.createThumbnails();
				break;
				case NotificationConstants.LOGIN_VALIDATED:
				    //如果是行政人员，则允许其预定更多会议室
					if(ApplicationFacade.getInstance().user.assistant){
						roomPanel.details.repeatForm.currentState="assistantState";
					}
				break;
            }
        }
        
        protected function get roomPanel():RoomPanel
		{
            return viewComponent as RoomPanel
        }
	}
}
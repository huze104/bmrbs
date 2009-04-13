package com.baidu.ite.mrbs.controller
{
	
	
	import com.baidu.ite.mrbs.model.MrbsAreaProxy;
	import com.baidu.ite.mrbs.model.MrbsRepeatProxy;
	import com.baidu.ite.mrbs.model.MrbsRoomProxy;
	import com.baidu.ite.mrbs.model.MrbsScheduleProxy;
	import com.baidu.ite.mrbs.model.MrbsUserProxy;
	import com.baidu.ite.mrbs.view.mediator.IndexMediator;
	import com.baidu.ite.mrbs.view.mediator.RoomPanelMediator;
	
	import org.puremvc.as3.interfaces.ICommand;
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	/**
	 * 
	 * @author zhanghongzhi@baidu.com
	 * 
	 */
	public class StartupCommand extends SimpleCommand implements ICommand
	{
		/**
		 * Register the Proxies and Mediators.
		 * 
		 * Get the View Components for the Mediators from the app,
		 * which passed a reference to itself on the notification.
		 */
		override public function execute( note:INotification ) : void	
		{
			var areaProxy:MrbsAreaProxy =new MrbsAreaProxy(); 
			var userProxy:MrbsUserProxy =new MrbsUserProxy(); 
			//将proxy注册
			facade.registerProxy(areaProxy);
			facade.registerProxy(new MrbsRoomProxy());
			facade.registerProxy(new MrbsScheduleProxy());
			facade.registerProxy(new MrbsRepeatProxy());
			facade.registerProxy(userProxy);
			//将界面和mediator结合起来
			var app:index = note.getBody() as index;
			facade.registerMediator(new IndexMediator("IndexMediator",app));
			facade.registerMediator(new RoomPanelMediator("RoomPanelMediator",app.roomPanel));
			//开始加载区域数据，供界面展示
			areaProxy.getAll();
			//初始化加载时，就向服务端判断当前用户是否处于登陆状态，这样做的主要目的就是为了将本flash嵌入到其他系统时，如果其他系统也使用cas作为sso
            //如果其他系统也是登陆状态，那么本系统也是登陆状态
			userProxy.isUserHaveLogin();
			
		}
	}
}
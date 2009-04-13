package com.baidu.ite.mrbs.controller
{
	import com.baidu.ite.mrbs.view.RoomThumbnail;
	import com.baidu.ite.mrbs.view.mediator.RoomThumbnailMediator;
	
	import org.puremvc.as3.interfaces.ICommand;
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	


	public class RoomThumbnailInitCommand extends SimpleCommand implements ICommand
	{
		/**
		 * Register the Proxies and Mediators.
		 * 
		 * Get the View Components for the Mediators from the app,
		 * which passed a reference to itself on the notification.
		 */
		override public function execute( note:INotification ) : void	
		{
			//将界面和mediator结合起来
			var roomThumbnail:RoomThumbnail = note.getBody() as RoomThumbnail;
			facade.registerMediator(new RoomThumbnailMediator("RoomThumbnailMediator",roomThumbnail));
		}
	}
}
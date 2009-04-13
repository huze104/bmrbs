package com.baidu.flexcomponent.alterSelectList
{
	import mx.controls.CheckBox;
	import mx.controls.listClasses.BaseListData;
	import mx.controls.listClasses.ListBase;
	import mx.events.ListEvent;

	public class CheckBoxItemRenderer extends CheckBox
	{
		override public function initialize():void {
			super.initialize();
			//we don't want the user to have normal interaction with the checkbox, so
			//they shouldn't be able to actually check or uncheck the checkbox.
			//That will get done based on the highlighting in the list control.
			this.mouseEnabled = false;
		}
		
		override public function set listData(value:BaseListData):void {
			super.listData = value;
			
			this.selected = (listData.owner as ListBase).isItemSelected(listData.uid);
			
			//all we need to do is add an event listener to get notified whenever
			//there a change event dispatched by the main list control
			(value.owner as ListBase).addEventListener(ListEvent.CHANGE, listEventHandler);
		}
		
		private function listEventHandler(event:ListEvent):void {
			//when the change event is thrown we check to see if this list item is
			//highlighted in the list and we set the selected property of the checkbox accordingly
			this.selected = (listData.owner as ListBase).isItemSelected(listData.uid);
		}
	}
}
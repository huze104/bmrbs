package com.baidu.flexcomponent.alterSelectList
{
	import mx.controls.TileList;
	import mx.controls.listClasses.IListItemRenderer;

	public class AlternateSelectionList extends TileList
	{
		
		override protected function selectItem(item:IListItemRenderer, shiftKey:Boolean, ctrlKey:Boolean, transition:Boolean=true):Boolean {
			if(selectedItems.length > 0 && !isItemSelected(item.data)) {

				var indices:Array = selectedIndices;
				indices.sort(Array.NUMERIC);
				
				//figure out the index of first item in the list that is selected
				var minIndex:int = indices[0];
				//index of last selected item
				var maxIndex:int = indices[indices.length - 1];
			
				//index of the item that was just selected
				var index:int = itemRendererToIndex(item);
				
				var i:int;
				var curItem:IListItemRenderer;
				
				//if the item that was just selected is before the first selected item in the list
				//then we're going to select each item between our current item and the first
				//selected item.
				if(index < minIndex) {
					for(i=index; i<minIndex; i++) {
						
						curItem = indexToItemRenderer(i);

						//if the item is visible in the list then we get an LisItemRenderer object,
						//but if the item isn't visible in the list we'll get null
						if(curItem != null) {
							if(!isItemSelected(curItem.data)) {
								//if the item is visible and not selected then we call List.selectItem
								super.selectItem(curItem, false, true, transition);
							}
						}
						else {
							//if the item isn't visible in the list then we just set the appropriate
							//item in selectedData
							selectedData[itemToUID(dataProvider[i])] = dataProvider[i];
						}
					}
					return true;
				}
				//if the item was below the last selected item then we're going to select
				//each item between the last selected item and the current item
				else if(index > maxIndex) {
					for(i=maxIndex + 1; i<=index; i++) {
						
						curItem = indexToItemRenderer(i);
	
						if(curItem != null) {
							if(!isItemSelected(curItem.data)) {
								super.selectItem(curItem, false, true, transition);
							}
						}
						else {
							selectedData[itemToUID(dataProvider[i])] = dataProvider[i];
						}
					}
					return true;
				}
			}
			
			//if we get here then that means that either there are no items already selected in
			//the list, or the item that was clicked was already selected (so we just de-select)
			
			//all we do is fake it so it seems like the user is holding down control
			return super.selectItem(item, shiftKey, true, transition);	
		}
	}
}
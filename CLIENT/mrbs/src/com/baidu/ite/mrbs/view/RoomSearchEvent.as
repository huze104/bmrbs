package com.baidu.ite.mrbs.view
{
    
import com.baidu.ite.mrbs.entity.util.RoomSearchBean;

import flash.events.Event;

public class RoomSearchEvent extends Event
{
    public static const ROOMSEARCH:String = "roomsearch";
    
    public var seachBean:RoomSearchBean;
    
    public function RoomSearchEvent(searchBean:RoomSearchBean)
    {
        super(ROOMSEARCH);
        this.seachBean = searchBean;
    }
    
    override public function clone():Event
    {
        return new RoomSearchEvent(seachBean);
    }
}

}
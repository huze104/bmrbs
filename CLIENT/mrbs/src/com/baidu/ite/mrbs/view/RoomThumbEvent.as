package com.baidu.ite.mrbs.view
{
    
import com.baidu.ite.mrbs.entity.MrbsRoom;

import flash.events.Event;

public class RoomThumbEvent extends Event
{
    public static const DETAILS:String = "details";
    public static const BROWSE:String = "browse";
    
    public var room:MrbsRoom;
    
    public function RoomThumbEvent(type:String, room:MrbsRoom)
    {
        super(type);
        this.room = room;
    }
    
    override public function clone():Event
    {
        return new RoomThumbEvent(type, room);
    }
}

}
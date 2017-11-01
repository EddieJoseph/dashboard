package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;


import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.EventLoader;

import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class SingleCalendarProvider extends CalendarProvider {
  private URL url;
  

  

  
  private Calendar from;
  private Calendar to;
  
  private EventLoader loader;
  //private CalendarController controller;
  
  public SingleCalendarProvider(URL url){
    //this.controller=controller;
    this.url=url;
    loader=new EventLoader(url);
    from = Utils.getStartOfWeek();
    to = Utils.getStartOfWeek();
    to.add(Calendar.DAY_OF_MONTH,7);
    //from.set(2017,10-1,23,0,0);
    //to.set(2017,10-1,30,0,0);
    updateCalendar();
  }
  
  protected void updateCalendar(){
     events=loader.load(from.getTime(),to.getTime());
  }
  
  

  
}

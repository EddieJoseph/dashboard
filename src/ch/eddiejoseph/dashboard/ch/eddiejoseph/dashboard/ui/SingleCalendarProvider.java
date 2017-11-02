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
  
  public SingleCalendarProvider(URL url, Calendar from, Calendar to){
    this.url=url;
    loader=new EventLoader(url);
    this.from = from;
    this.to = to;
    updateCalendar();
  }
  
  protected void updateCalendar(){
     events=loader.load(from.getTime(),to.getTime());
  }
  
  

  
}

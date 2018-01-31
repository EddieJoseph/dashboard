package ch.eddiejoseph.dashboard.ui;


import ch.eddiejoseph.dashboard.dataloader.calendar.EventLoader;

import java.net.URL;
import java.util.Calendar;

public class SingleCalendarProvider extends CalendarProvider {
  private URL url;
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

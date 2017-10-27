package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;


import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.EventLoader;

import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class SingleCalendarProvider extends CalendarProvider {
  private URL url;
  
  public List<CalendarEvent> getEvents() {
    return events;
  }
  

  
  private Calendar from;
  private Calendar to;
  private List<CalendarEvent> events;
  private EventLoader loader;
  private CalendarController controller;
  
  public SingleCalendarProvider(URL url, CalendarController controller){
    this.controller=controller;
    this.url=url;
    loader=new EventLoader(url);
    from = Calendar.getInstance();
    to = Calendar.getInstance();
    from.set(2017,10-1,23,0,0);
    to.set(2017,10-1,30,0,0);
    updateCalendar();
  }
  
  protected void updateCalendar(){
     events=loader.load(from.getTime(),to.getTime());
  }
  
  
  public List<CalendarEvent> call(){
    for(;;){
      try {
        updateCalendar();
        if (isCancelled()) {
          return events;
        }
        try {
          Thread.sleep(1000);
        }catch(InterruptedException e){
          if (isCancelled()) {
            return events;
          }
        }
      }catch (Exception e){
        return events;
      }
    }
  }
  
}

package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;


import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.EventLoader;
import javafx.concurrent.Task;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarProvider extends Task<List<CalendarEvent>>{
  private URL url;
  
  public Calendar getFrom() {
    return from;
  }
  
  public void setFrom(Calendar from) {
    this.from = from;
    updateCalendar();
  }
  
  public Calendar getTo() {
    return to;
  }
  
  public void setTo(Calendar to) {
    this.to = to;
    updateCalendar();
  }
  
  public List<CalendarEvent> getEvents() {
    return events;
  }
  

  
  private Calendar from;
  private Calendar to;
  private List<CalendarEvent> events;
  private EventLoader loader;
  private CalendarController controller;
  
  public CalendarProvider(URL url, CalendarController controller){
    this.controller=controller;
    this.url=url;
    loader=new EventLoader(url);
    from = Calendar.getInstance();
    to = Calendar.getInstance();
    from.set(2017,10-1,23,0,0);
    to.set(2017,10-1,30,0,0);
    updateCalendar();
  }
  
  private String previous="";
  
  private void updateCalendar(){
     events=loader.load(from.getTime(),to.getTime());
     StringBuilder sb=new StringBuilder();
     for(CalendarEvent e:events){
       sb.append(e.toString());
     }
     String tmp=sb.toString();
     if(!tmp.equals(previous)) {
       previous = tmp;
     }
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

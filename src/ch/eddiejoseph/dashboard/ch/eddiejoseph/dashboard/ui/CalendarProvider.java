package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import javafx.concurrent.Task;

import java.util.Calendar;
import java.util.List;

public abstract class CalendarProvider extends Task<List<CalendarEvent>> {
  protected Calendar from;
  protected Calendar to;
  protected List<CalendarEvent> events;
  protected int waitTime=10000;
  
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
  public List<CalendarEvent> call(){
    for(;;){
      try {
        updateCalendar();
        if (isCancelled()) {
          return events;
        }
        try {
          Thread.sleep(waitTime);
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
  public List<CalendarEvent> getEvents() {
    return events;
  }
  protected abstract void updateCalendar();
  
}

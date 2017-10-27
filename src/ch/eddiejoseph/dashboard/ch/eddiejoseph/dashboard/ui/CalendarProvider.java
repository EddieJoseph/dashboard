package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import javafx.concurrent.Task;

import java.util.Calendar;
import java.util.List;

public abstract class CalendarProvider extends Task<List<CalendarEvent>> {
  protected Calendar from;
  protected Calendar to;
  
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
  public abstract List<CalendarEvent> call();
  public abstract List<CalendarEvent> getEvents();
  protected abstract void updateCalendar();
  
}

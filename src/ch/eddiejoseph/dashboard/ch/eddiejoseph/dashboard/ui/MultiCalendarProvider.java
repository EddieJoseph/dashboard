package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiCalendarProvider extends CalendarProvider {
  private ArrayList<SingleCalendarProvider> singleProviders;
  
  public MultiCalendarProvider(URL... urls){
    singleProviders=new ArrayList<>();
    for(URL url : urls){
      singleProviders.add(new SingleCalendarProvider(url));
    }
    updateCalendar();
    
  }
  
  @Override
  protected void updateCalendar() {
  ArrayList<CalendarEvent> events=new ArrayList<>();
  for(SingleCalendarProvider p:singleProviders){
    p.updateCalendar();
    events.addAll(p.getEvents());
  }
    Collections.sort(events);
  this.events=events;
  //System.out.println(events);
  //System.exit(0);
  
  }
}

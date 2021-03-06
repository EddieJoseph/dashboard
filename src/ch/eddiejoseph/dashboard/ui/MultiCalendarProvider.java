package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MultiCalendarProvider extends CalendarProvider {
  private ArrayList<SingleCalendarProvider> singleProviders;
  
  public MultiCalendarProvider(Calendar from, Calendar to, URL... urls){
    singleProviders=new ArrayList<>();
    for(URL url : urls){
      if(url!=null) {
        try {
          HttpURLConnection testconn = (HttpURLConnection) url.openConnection();
          testconn.setRequestMethod("GET");
          testconn.connect();
          if(testconn.getResponseCode()>=400) {
            System.out.println("could not connect to "+url.toString()+" Error: "+testconn.getResponseCode() +" "+ testconn.getResponseMessage());
          }else {
            singleProviders.add(new SingleCalendarProvider(url, from, to));
          }
        }catch(IOException e){
          e.printStackTrace();
        }
        
      }
    }
    setFrom(from);
    setTo(to);
    updateCalendar();
  }
  
  
  
  public void setFrom(Calendar from){
    super.setFrom(from);
    for(CalendarProvider prov:singleProviders){
      prov.setFrom(from);
    }
  }
  
  public void setTo(Calendar to){
    super.setTo(to);
    for(CalendarProvider prov:singleProviders){
      prov.setTo(to);
    }
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
  }
}

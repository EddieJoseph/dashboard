package ch.eddiejoseph.dashboard.dataloader.calendar;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Timezone;
import biweekly.util.Duration;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EventLoader {
  ICalendar ical=null;
  URL url;
  
  public EventLoader(URL url){
    this.url=url;
  }
  
  
  
  public List<CalendarEvent> load(Date from, Date to){
  
    try {
      ical= Biweekly.parse(url.openStream()).first();
    } catch (IOException e) {
      System.out.println("Couldn't open stream to URL");
      e.printStackTrace();
      //System.exit(-1);
    }
    
    ArrayList<CalendarEvent> events= new ArrayList<>();
    for(VEvent e : ical.getEvents()){
      if(e.getRecurrenceRule()==null){
        
        if(between(e,from,to)){
          events.add(new CalendarEvent(e));
        }
      }else{
        if(e.getDuration()==null){
          e.setDuration(Duration.diff(e.getDateStart().getValue(),e.getDateEnd().getValue()));
        }
        
        DateIterator dit=e.getDateIterator(TimeZone.getDefault());
        if(dit.hasNext()){
          Date actD;
          do{
            actD=dit.next();
            //System.out.println(actD);
            //System.out.println(e.getDuration());
            if(between(actD,e.getDuration().getValue().add(actD),from,to)) {
              events.add(new CalendarEvent(e,actD));
            }
          }while(dit.hasNext()&&actD.before(to));
        }
      }
    }
    Collections.sort(events);
    return events;
  }
  public static boolean between(VEvent e,Date from, Date to){
    return between(e.getDateStart().getValue(),e.getDateEnd().getValue(),from,to);
  }
  
  public static boolean between(Date start, Date end,Date from,Date to){
    //System.out.println(start+"\t:\t"+from);
    return start.after(from)&&start.before(to)||end.after(from)&&end.before(to);
  }
  
  
  
}

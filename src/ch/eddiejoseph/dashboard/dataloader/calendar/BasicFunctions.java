package ch.eddiejoseph.dashboard.dataloader.calendar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BasicFunctions {
  public static void main(String args[]){
    String urltext=PropertiesFactory.getPropertie("calurle");
    URL cal1=null;
    try {
      cal1= new URL(urltext);
    } catch (MalformedURLException e) {
      System.out.println("Failed to generate URL");
      e.printStackTrace();
    }
    EventLoader loader=new EventLoader(cal1);
    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();
    from.set(2017,10-1,23,0,0);
    to.set(2017,10-1,30,0,0);
    List<CalendarEvent> events=loader.load(from.getTime(),to.getTime());
    
    System.out.println(events.size()+" events found in the given time frame.\n\n");
    
    for(CalendarEvent e:events){
      System.out.println(e);
    }
    
  }
  
  
}

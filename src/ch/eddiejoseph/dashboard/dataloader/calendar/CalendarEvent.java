package ch.eddiejoseph.dashboard.dataloader.calendar;

import biweekly.component.VEvent;

import java.util.Calendar;
import java.util.Date;

public class CalendarEvent implements Comparable<CalendarEvent>{
  public Boolean isReoccuring() {
    return reoccuring;
  }
  
  public Calendar getStartDate() {
    return startDate;
  }
  
  public Calendar getEndDate() {
    return endDate;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getLocation() {
    return location;
  }
  
  private Calendar startDate;
  private Calendar endDate;
  private Boolean reoccuring=false;
  private String title;
  private String description;
  private String location;
  
  public CalendarEvent(VEvent e, Date starting){
    Calendar start=Calendar.getInstance();
    start.setTime(starting);
    startDate=start;
    Calendar end=Calendar.getInstance();
    end.setTime(e.getDuration().getValue().add(starting));
    endDate=end;
    if(e.getRecurrenceRule()!=null){
      reoccuring=true;
    }
    title=e.getSummary().getValue();
    description=e.getDescription().getValue();
    location=e.getLocation().getValue();
  }
  
  
  public CalendarEvent(VEvent e){
    Calendar start=Calendar.getInstance();
    start.setTime(e.getDateStart().getValue());
    startDate=start;
    Calendar end=Calendar.getInstance();
    end.setTime(e.getDateEnd().getValue());
    endDate=end;
    if(e.getRecurrenceRule()!=null){
      reoccuring=true;
    }
    title=e.getSummary().getValue();
    if(e.getDescription()!=null) {
      description = e.getDescription().getValue();
    }
    if(e.getLocation()!=null) {
      location = e.getLocation().getValue();
    }
  }
  
  public int compareTo(CalendarEvent c){
    if(c.getStartDate().before(startDate)){
      return 1;
    }
    if(startDate.before(c.getStartDate())){
      return -1;
    }
    return 0;
  }
  
  public String toString(){
    String ret="";
    ret+=title+"\n";
    ret+=zeroLead(startDate.get(Calendar.DAY_OF_MONTH))+"."+zeroLead(startDate.get(Calendar.MONTH)+1)+"."+startDate.get(Calendar.YEAR)+"\t"+zeroLead(startDate.get(Calendar.HOUR_OF_DAY))+":"+zeroLead(startDate.get(Calendar.MINUTE))+"   -   ";
    ret+=zeroLead(endDate.get(Calendar.DAY_OF_MONTH))+"."+zeroLead(endDate.get(Calendar.MONTH)+1)+"."+endDate.get(Calendar.YEAR)+"\t"+zeroLead(endDate.get(Calendar.HOUR_OF_DAY))+":"+zeroLead(endDate.get(Calendar.MINUTE))+"\n";
    if(description!=null&&!description.equals("")) {
      ret +=description+"\n";
    }
    if(location!=null&&!location.equals("")){
      ret+=location+"\n";
    }
    return ret;
  }
  
  private String zeroLead(int min){
    if(min<10){
      return "0"+Integer.toString(min);
    }
    return Integer.toString(min);
  }
  
}

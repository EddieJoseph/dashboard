package ch.eddiejoseph.dashboard.ui;

import biweekly.component.VEvent;

import java.util.Calendar;
import java.util.Date;

public class Utils {
  public static boolean between(VEvent e, Date from, Date to){
    return between(e.getDateStart().getValue(),e.getDateEnd().getValue(),from,to);
  }
  
  public static boolean between(Date start, Date end,Date from,Date to){
    return start.after(from)&&start.before(to)||end.after(from)&&end.before(to)||start.before(from)&&end.after(to);
  }
  
  public static String zeroLead(int min){
    if(min<10){
      return "0"+Integer.toString(min);
    }
    return Integer.toString(min);
  }
  
  public static Calendar getStartOfWeek() {
    Calendar c= Calendar.getInstance();
    c.set(Calendar.MINUTE,0);
    c.set(Calendar.HOUR_OF_DAY,0);
    int dow=c.get(Calendar.DAY_OF_WEEK);
    if(dow!=2){
      if(dow<2){
        c.add(Calendar.DAY_OF_MONTH,-6);
      }else{
        c.add(Calendar.DAY_OF_MONTH,-(dow-2));
      }
    }
    return c;
  }
  
  public static String nameOfDay(int dayInWeek){
    switch (dayInWeek){
      case 1:return "Sonntag";
      case 2:return "Montag";
      case 3:return "Dienstag";
      case 4:return "Mittwoch";
      case 5:return "Donnerstag";
      case 6:return "Freitag";
      case 7:return "Samstag";
      default : return null;
    }
  }
}

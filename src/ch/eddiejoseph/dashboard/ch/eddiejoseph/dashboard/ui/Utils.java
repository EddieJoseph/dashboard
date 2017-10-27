package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import biweekly.component.VEvent;

import java.util.Date;

public class Utils {
  public static boolean between(VEvent e, Date from, Date to){
    return between(e.getDateStart().getValue(),e.getDateEnd().getValue(),from,to);
  }
  
  public static boolean between(Date start, Date end,Date from,Date to){
    //System.out.println(start+"\t:\t"+from);
    return start.after(from)&&start.before(to)||end.after(from)&&end.before(to);
  }
  
  public static String zeroLead(int min){
    if(min<10){
      return "0"+Integer.toString(min);
    }
    return Integer.toString(min);
  }
  
}

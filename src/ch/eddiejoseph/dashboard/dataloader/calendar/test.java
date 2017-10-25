package ch.eddiejoseph.dashboard.dataloader.calendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;

public class test {

	public static void main(String[] args) {
		URL cal1=null;
		try {
			//cal1= new URL("https://calendar.google.com/calendar/ical/fl5plch9aald7gs82de49nneas%40group.calendar.google.com/private-503ed6a6cda5b2bead3d5db1e606b653/basic.ics");
			cal1= new URL("https://calendar.google.com/calendar/ical/eddie.joseph93%40gmail.com/private-7f3f063d7abf62922d5ddceff22aabb2/basic.ics");
		} catch (MalformedURLException e) {
			System.out.println("Failed to generate URL");
			e.printStackTrace();
		}
		ICalendar ical=null;
		try {
			ical=Biweekly.parse(cal1.openStream()).first();
		} catch (IOException e) {
			System.out.println("Couldn't open stream to URL");
			e.printStackTrace();
		}
		//System.out.println(ical);
		//System.out.println(ical.getEvents().get(0).getRecurrenceRule());
		//System.out.println(ical.getEvents().get(1).getRecurrenceRule());
		
		for(VEvent e:ical.getEvents()){
			if(e.getRecurrenceRule()==null){
				//single event
				if(isThisWeek(e.getDateStart().getValue())){
					printEvent(e);
				}
			}else{
				//reoccuring
				//e.getDuration().getValue().
				if(hasInWeek(e.getDateIterator(TimeZone.getDefault()))!=null){
					printEvent(e);
				}
			}
			
			
		}
		
		
		
		//ical.getEvents().get(0).
		
		
		

	}
	
	private static void printEvent(VEvent e){
		Date tmp=(Date)e.getDateEnd().getValue();
		//Date tmp=(Date) e.getDateStart().getValue();
		Calendar cal=Calendar.getInstance();
		cal.setTime(tmp);
		System.out.println(cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR)+"\t"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+"\t"+e.getSummary().getValue() + e.getLocation());
	}
	
	private static Date hasInWeek(DateIterator dit){
		for(int c=0;c<1000&&dit.hasNext();c++){
			Date d=dit.next();
			if(isThisWeek(d)){
				return d;
			}
		}
		
		
		return null;
	}
	
	
	
	private static Boolean isThisWeek(Date d){
		Calendar c =Calendar.getInstance();
		c.setTime(d);
		Calendar now=Calendar.getInstance();
		if(c.get(Calendar.WEEK_OF_YEAR)==now.get(Calendar.WEEK_OF_YEAR)&&c.get(Calendar.YEAR)==now.get(Calendar.YEAR)){
			return true;
		}
				
		return false;
	}

}

package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarController implements ChangeListener<Number> {
  @FXML
  private Pane day0;
  @FXML
  private Pane day1;
  @FXML
  private Pane day2;
  @FXML
  private Pane day3;
  @FXML
  private Pane day4;
  @FXML
  private Pane day5;
  @FXML
  private Pane day6;
  
  private RunUI mainApp;
  
  private Pane[] days;
  
  private List<CalendarEvent> events;
  
  public void setMainApp(RunUI mainApp) {
    this.mainApp = mainApp;
  }
  
  private CalendarProvider provider;
  
  
  @FXML
  private void initialize(){
    days=new Pane[7];
    days[0]=day0;days[1]=day1;days[2]=day2;days[3]=day3;days[4]=day4;days[5]=day5;days[6]=day6;
    String []urlTexts=new String[7];
    urlTexts[0]= PropertiesFactory.getPropertie("calurle");
    urlTexts[1]= PropertiesFactory.getPropertie("calurlr");
    urlTexts[2]= PropertiesFactory.getPropertie("calurlu");
    urlTexts[3]= PropertiesFactory.getPropertie("calurla");
    urlTexts[4]= PropertiesFactory.getPropertie("calurlal");
    urlTexts[5]= PropertiesFactory.getPropertie("calurlp");
    urlTexts[6]= PropertiesFactory.getPropertie("calurlb");
  
    URL cal1[]=new URL[urlTexts.length];
    try {
      for (int count=0;count<urlTexts.length;count++) {
        cal1[count] = new URL(urlTexts[count]);
      }
    } catch (MalformedURLException e) {
      System.out.println("Failed to generate URL");
      e.printStackTrace();
    }
    Calendar from=Utils.getStartOfWeek();
    Calendar to =Utils.getStartOfWeek();
    to.add(Calendar.DAY_OF_MONTH,7);
    provider=new MultiCalendarProvider(from,to,cal1);
    Thread th = new Thread(provider);
    th.setDaemon(true);
    th.start();
    //draw(events);
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
          checkAndDraw(provider.getEvents());
      }
    };
    timer.start();
  }
  
  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    draw(provider.getEvents());
  }
  
  private List<CalendarEvent>[] sortWeek(List<CalendarEvent> events){
    int[] t=new int[7];
    List<CalendarEvent>[] week=new List[7];
    for(int i=0;i<7;i++){
      week[i]=new ArrayList<CalendarEvent>();
    }
    Calendar c = Utils.getStartOfWeek();
    for(int d =0;d<7;d++){
      Calendar day=Calendar.getInstance();
      day.setTime(c.getTime());
      day.add(Calendar.DAY_OF_MONTH,d);
      for(CalendarEvent e:events){
        if(e.eventOnDay(day)){
          week[d].add(e);
        }
      }
    }
    return week;
  }
  
  private String prev="";
  private boolean hasChanged(List<CalendarEvent> events){
    StringBuilder sb = new StringBuilder();
    for (CalendarEvent e:events){
      sb.append(e.toString());
    }
    String tmp=sb.toString();
    if(!tmp.equals(prev)){
      prev=tmp;
      return true;
    }
    return false;
  }
  
  public void checkAndDraw(List<CalendarEvent> events){
    if(hasChanged(events)) {
      draw(events);
    }
  }
  
  public void draw(List<CalendarEvent> events){
      List<CalendarEvent>[] eventsSorted = sortWeek(events);
      for (int c = 0; c < 7; c++) {
        days[c].getChildren().clear();
        double r = 0;
        for (CalendarEvent e : eventsSorted[c]) {
          AnchorPane pane = new UIEvent(e, days[0]).getRoot();
          r = r + days[c].getHeight() / 10;
          days[c].getChildren().add(pane);
        }
      }
  }
  
}

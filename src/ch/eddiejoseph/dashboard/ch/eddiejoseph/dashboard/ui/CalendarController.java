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
    String urltext= PropertiesFactory.getPropertie("calurle");
    URL cal1=null;
    try {
      cal1= new URL(urltext);
    } catch (MalformedURLException e) {
      System.out.println("Failed to generate URL");
      e.printStackTrace();
    }
    provider=new SingleCalendarProvider(cal1,this);
    //events=provider.getEvents();
    //System.out.println(events);
    Thread th = new Thread(provider);
    th.setDaemon(true);
    th.start();
    //draw(events);
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        //System.out.println(System.nanoTime());
        //if(provider.hasChanged()) {
          checkAndDraw(provider.getEvents());
        //}
      }
    };
    
    //mainApp.getScene().widthProperty().addListener(new ChangeListener<Number>() {
    //  @Override
    //  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    //    draw(provider.getEvents());
    //  }
    //});
    //mainApp.getScene().heightProperty().addListener(new ChangeListener<Number>() {
    //  @Override
    //  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    //    draw(provider.getEvents());
    //  }
    //});
    
    timer.start();
    
  }
  
  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    System.out.println("ll");
    draw(provider.getEvents());
  }
  
  private List<CalendarEvent>[] sortWeek(List<CalendarEvent> events){
    int[] t=new int[7];
    List<CalendarEvent>[] week=new List[7];
    for(int i=0;i<7;i++){
      week[i]=new ArrayList<CalendarEvent>();
    }
    Calendar c= Calendar.getInstance();
    int dow=c.get(Calendar.DAY_OF_WEEK);
    if(dow!=2){
      if(dow<2){
        c.add(Calendar.DAY_OF_MONTH,-6);
      }else{
        c.add(Calendar.DAY_OF_MONTH,-(dow-2));
      }
    }
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

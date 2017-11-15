package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarControllerEmpty  {
 @FXML
  private AnchorPane anchor;
  
  private RunUI mainApp;
  
  private Day[] days;
  
  private List<CalendarEvent> events;
  
  public void setMainApp(RunUI mainApp) {
    this.mainApp = mainApp;
    //mainApp.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
    //  for(UIEvent e:uievents){
    //    for(Day d:days) {
    //      d.getEventPane().setPrefWidth((newValue.doubleValue() - 50) / nrOfDays);
    //      d.getEventPane().setMaxWidth((newValue.doubleValue() - 50) / nrOfDays);
    //    }
    //    e.resizeh(newValue.doubleValue()-50);
    //  }
    //});
    //mainApp.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
    //  for(UIEvent e:uievents){
    //
    //    for(Day d:days) {
    //      d.getEventPane().setPrefHeight((newValue.doubleValue() - 50));
    //      d.getEventPane().setMaxHeight((newValue.doubleValue() - 50));
    //    }
    //    e.resizew((newValue.doubleValue()-50)/nrOfDays);
    //  }
    //});
    
  }
  
  private CalendarProvider provider;
  
  public static final int nrOfDays=5;
  
  public static boolean weekstart=false;
  
  public List<UIEvent> uievents;
  
  @FXML
  private void initialize(){
    days = new Day[nrOfDays];
    uievents=new ArrayList<>();
    GridPane grid=new GridPane();
    anchor.getChildren().add(grid);
    int gridIndex = anchor.getChildren().indexOf(grid);
    anchor.setBottomAnchor(grid,0.0);
    anchor.setTopAnchor(grid,0.0);
    anchor.setLeftAnchor(grid,0.0);
    anchor.setRightAnchor(grid,0.0);
    
    RowConstraints rc = new RowConstraints();
    rc.setFillHeight(true);
    rc.setVgrow(Priority.ALWAYS);
    grid.getRowConstraints().add(rc);
    
    grid.getStyleClass().add("hlines");
    for (int c=0;c<nrOfDays;c++){
      ColumnConstraints cc = new ColumnConstraints();
      cc.setFillWidth(true);
      //cc.setHgrow(Priority.ALLWAYS);
      cc.setPercentWidth(100.0/(double)nrOfDays);
      grid.getColumnConstraints().add(cc);
      days[c]=new Day(Integer.toString(c));
      grid.add(days[c].getWholeDay(),c,0);
    }
    
    grid.gridLinesVisibleProperty().setValue(true);
    grid.toFront();
    
    
    String []urlTexts=new String[7];
    urlTexts[0]= PropertiesFactory.getPropertie("eddie.Privat");
    urlTexts[1]= PropertiesFactory.getPropertie("eddie.Buisness");
    urlTexts[2]= PropertiesFactory.getPropertie("eddie.AL");
    urlTexts[3]= PropertiesFactory.getPropertie("eddie.Basis");
    urlTexts[4]= PropertiesFactory.getPropertie("eddie.Leitung");
    urlTexts[5]= PropertiesFactory.getPropertie("eddie.Studium");
    urlTexts[6]= PropertiesFactory.getPropertie("eddie.Pruefungen");
    //urlTexts=new String[0];
  
    URL cal1[]=new URL[urlTexts.length];
    int count=0;
    try {
      for (count=0;count<urlTexts.length;count++) {
        cal1[count] = new URL(urlTexts[count]);
      }
    } catch (MalformedURLException e) {
      System.out.println("Failed to generate URL for url["+count+"] urltext:"+urlTexts[count]);
      e.printStackTrace();
    }
    Calendar from=getStartDay();
    Calendar to=getStartDay();
    to.add(Calendar.DAY_OF_MONTH,nrOfDays);
    provider=new MultiCalendarProvider(from,to,cal1);
    Thread th = new Thread(provider);
    th.setDaemon(true);
    th.start();
    
    Calendar currentDate=Calendar.getInstance();
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        checkDay();
          checkAndDraw(provider.getEvents());
      }
      
      private void checkDay(){
        Calendar c=provider.getTo();
        Calendar from=getStartDay();
        if(from.get(Calendar.YEAR)==c.get(Calendar.YEAR)&&from.get(Calendar.MONTH)==c.get(Calendar.MONTH)&&from.get(Calendar.DAY_OF_MONTH)==c.get(Calendar.DAY_OF_MONTH)){
          provider.setFrom(from);
          Calendar to=getStartDay();
          to.add(Calendar.DAY_OF_MONTH,nrOfDays);
          provider.setTo(to);
        }
      }
      
    };
    timer.start();
  }
  
  
  private List<CalendarEvent>[] sortDay(List<CalendarEvent> events){
    int[] t=new int[nrOfDays];
    List<CalendarEvent>[] dayList=new List[nrOfDays];
    for(int i=0;i<nrOfDays;i++){
      dayList[i]=new ArrayList<CalendarEvent>();
    }
    Calendar c=getStartDay();
    for(int d =0;d<nrOfDays;d++){
      Calendar day=Calendar.getInstance();
      day.setTime(c.getTime());
      day.add(Calendar.DAY_OF_MONTH,d);
      for(CalendarEvent e:events){
        if(e.eventOnDay(day)){
          dayList[d].add(e);
        }
      }
    }
    return dayList;
  }
  
  private Calendar getStartDay(){
    if(weekstart){
      return Utils.getStartOfWeek();
    }else{
      Calendar c = Calendar.getInstance();
      c.set(Calendar.MINUTE,0);
      c.set(Calendar.HOUR_OF_DAY,0);
      return c;
      
    }
    
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
    
    
    
      List<CalendarEvent>[] eventsSorted = sortDay(events);
      uievents.clear();
      for (int c = 0; c < nrOfDays; c++) {
        Calendar titleDate=getStartDay();
        titleDate.add(Calendar.DAY_OF_MONTH,c);
        days[c].setTitle(Utils.nameOfDay(titleDate.get(Calendar.DAY_OF_WEEK))+"\n"+titleDate.get(Calendar.DAY_OF_MONTH)+" "+(titleDate.get(Calendar.MONTH)+1)+" "+titleDate.get(Calendar.YEAR));
        days[c].getEventPane().getChildren().clear();
        //double r = 0;
        for (CalendarEvent e : eventsSorted[c]) {
          UIEvent ev=new UIEvent(e, days[c].getEventPane());
          uievents.add(ev);
          AnchorPane pane = ev.getRoot();
          days[c].getEventPane().getChildren().add(pane);

        }
      }

  }
  
}

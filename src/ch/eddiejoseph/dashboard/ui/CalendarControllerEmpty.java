package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import ch.eddiejoseph.dashboard.dataloader.calendar.UrlLoader;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarControllerEmpty  {
 @FXML
  private AnchorPane anchor;
  
  private RunUI mainApp;
  
  private Day[] days;
  
  private List<CalendarEvent> events;
  
  private int bgcolsd [][]=null;
  private int txcolsd [][]=null;
  
  public void setMainApp(RunUI mainApp) {
    this.mainApp = mainApp;
  }
  
  private CalendarProvider[] provider;
  
  public static final int nrOfDays=Integer.parseInt(PropertiesFactory.getPropertie("nrOfDays"));
  
  public static boolean weekstart=Boolean.parseBoolean(PropertiesFactory.getPropertie("weekstart"));
  
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
    grid.getStyleClass().add("grid");
    for (int c=0;c<nrOfDays;c++){
      ColumnConstraints cc = new ColumnConstraints();
      cc.setFillWidth(true);
      cc.setPercentWidth(100.0/(double)nrOfDays);
      grid.getColumnConstraints().add(cc);
      days[c]=new Day(Integer.toString(c));
      grid.add(days[c].getWholeDay(),c,0);
    }
    grid.toFront();
    startProviders();
    initColors();
    AnimationTimer timer = new AnimationTimer() {
      long lastcheck=0;
      int nrtimes=0;
      boolean startup=true;
      @Override
      public void handle(long now) {
        if(now-lastcheck>120000000000l||startup) {
          lastcheck=now;
          checkDay();
          ArrayList<List<CalendarEvent>> evv = new ArrayList<>();
          for (CalendarProvider p : provider) {
            evv.add(p.getEvents());
          }
          List<CalendarEvent>[] allev = new List[evv.size()];
          for (int count = 0; count < evv.size(); count++) {
            allev[count] = evv.get(count);
          }
          if(nrtimes<10&&!startup) {
            checkAndDraw(allev);
            
            nrtimes++;
          }else {
            draw(allev);
            System.out.println(getDateString()+" force updated");
            nrtimes=0;
            startup=false;
          }
        }else{
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      
      private void checkDay(){
        Calendar c=provider[0].getTo();
        Calendar from=getStartDay();
        if(from.get(Calendar.YEAR)==c.get(Calendar.YEAR)&&from.get(Calendar.MONTH)==c.get(Calendar.MONTH)&&from.get(Calendar.DAY_OF_MONTH)==c.get(Calendar.DAY_OF_MONTH)){
          for(CalendarProvider p:provider) {
            p.setFrom(from);
            Calendar to = getStartDay();
            to.add(Calendar.DAY_OF_MONTH, nrOfDays);
            p.setTo(to);
          }
        }
      }
    };
    timer.start();
  }
  
  private String getDateString(){
    Date date = new Date();
    date.setTime(date.getTime());
    DateFormat form = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss aa");
    return form.format(date);
  }
  
  private void startProviders() {
    URL[][]cals= UrlLoader.getCalendars();
    Calendar from=getStartDay();
    Calendar to=getStartDay();
    to.add(Calendar.DAY_OF_MONTH,nrOfDays);
    provider=new MultiCalendarProvider[cals.length];
    for(int counter=0;counter<cals.length;counter++){
      provider[counter]=new MultiCalendarProvider(from,to,cals[counter]);
    }
    
    for (CalendarProvider m:provider) {
      Thread th = new Thread(m);
      th.setDaemon(true);
      th.start();
    }
  }
  
  private void initColors() {
    int [][] colorshifts= UrlLoader.getColors();
    double [][] colorsd = new double[colorshifts.length][3];
    bgcolsd =new int[colorshifts.length][3];
    txcolsd =new int[colorshifts.length][3];
    for (int c=0;c<colorshifts.length;c++){
      colorsd[c][0]=colorshifts[c][0]/255.0;
      colorsd[c][1]=colorshifts[c][1]/255.0;
      colorsd[c][2]=colorshifts[c][2]/255.0;
      int baseb=25;
      double factb=50;
      bgcolsd[c][0]=baseb+(int)(colorsd[c][0]*factb);
      bgcolsd[c][1]=baseb+(int)(colorsd[c][1]*factb);
      bgcolsd[c][2]=baseb+(int)(colorsd[c][2]*factb);
      int baset=80;
      double factt=70;
      txcolsd[c][0]=baset+(int)(colorsd[c][0]*factt);
      txcolsd[c][1]=baset+(int)(colorsd[c][1]*factt);
      txcolsd[c][2]=baset+(int)(colorsd[c][2]*factt);
    }
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
  
  String[]pr={};
  private boolean hasChanged(List<CalendarEvent>[] events){
    String[]tmpprev=new String[events.length];
    for(int counter=0;counter <events.length;counter++){
      StringBuilder sb = new StringBuilder();
      for (CalendarEvent e:events[counter]){
        sb.append(e.toString());
      }
      tmpprev[counter]=sb.toString();
    }
    if(tmpprev.length!=pr.length){
      pr=tmpprev;
      return true;
    }
    
    for(int counter=0;counter<tmpprev.length;counter++){
      if(!tmpprev[counter].equals(pr[counter])){
        pr=tmpprev;
        return true;
      }
    }
    return false;
  }
  
  
  public void checkAndDraw(List<CalendarEvent>[] events){
    if(hasChanged(events)) {
      draw(events);
      System.out.println(getDateString()+" updated");
    }
  }

  public void draw(List<CalendarEvent>[] events){
    uievents.clear();
    for (int c = 0; c < nrOfDays; c++) {
      Calendar titleDate = getStartDay();
      titleDate.add(Calendar.DAY_OF_MONTH, c);
      days[c].setTitle(Utils.nameOfDay(titleDate.get(Calendar.DAY_OF_WEEK)) + "\n" + titleDate.get(Calendar.DAY_OF_MONTH) + " " + (titleDate.get(Calendar.MONTH) + 1) + " " + titleDate.get(Calendar.YEAR));
      days[c].getEventPane().getChildren().clear();
    }
    for(int counter=0;counter<events.length;counter++) {
      List<CalendarEvent>[] eventsSorted = sortDay(events[counter]);
      for (int c = 0; c < nrOfDays; c++) {
        UIEvent.resetFullDayCount();
        for (CalendarEvent e : eventsSorted[c]) {
          UIEvent ev = new UIEvent(e, days[c].getEventPane(),counter,events.length,bgcolsd[counter],txcolsd[counter]);
          uievents.add(ev);
          AnchorPane pane = ev.getRoot();
          days[c].getEventPane().getChildren().add(pane);
        }
      }
    }
  }
}

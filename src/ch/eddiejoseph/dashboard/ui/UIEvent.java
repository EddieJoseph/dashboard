package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class UIEvent {
  @FXML
  private Label title;
  @FXML
  private AnchorPane root;
  
  private CalendarEvent event;
  private AnchorPane upperPane;
  
  private final double SHRINK=2.0;
  
  protected UIEvent(String title){
    this();
    this.title.setText(title);
  }
  
  public UIEvent(CalendarEvent e, AnchorPane p,int index, int nrOfCals){
    this(e);
    event=e;
    upperPane=p;
    if(Boolean.parseBoolean(PropertiesFactory.getPropertie("fullscreen"))) {
      resize(p.getHeight(),p.getWidth()/nrOfCals,p.getWidth()/nrOfCals*index);
    }else{
      resize(p.getHeight()-41,p.getWidth()/nrOfCals,p.getWidth()/nrOfCals*index);
    }
  }
  public UIEvent(CalendarEvent e, AnchorPane p,int index, int nrOfCals,int[] bgcol,int textcol[]){
    this(e);
    event=e;
    upperPane=p;
    if(Boolean.parseBoolean(PropertiesFactory.getPropertie("fullscreen"))) {
      resize(p.getHeight(),p.getWidth()/nrOfCals,p.getWidth()/nrOfCals*index);
    }else{
      resize(p.getHeight()-41,p.getWidth()/nrOfCals,p.getWidth()/nrOfCals*index);
    }
    root.setStyle("-fx-background-color: rgba("+bgcol[0]+", "+bgcol[1]+", "+bgcol[2]+", 0.7);");
    title.setStyle("-fx-text-fill: rgba("+textcol[0]+", "+textcol[1]+", "+textcol[2]+", 1);");
  }
  
  private void resize(double height, double width,double wofset){
    double minPx=height/(24*60);
    double dh=minPx* TimeUnit.MILLISECONDS.toMinutes(event.getEndDate().getTimeInMillis()-event.getStartDate().getTimeInMillis());
    if(dh>height){
      dh=height;
    }
    root.setPrefSize(width-2*SHRINK,dh);
    root.relocate(wofset+SHRINK,minPx*((event.getStartDate().get(Calendar.HOUR_OF_DAY))*60+event.getStartDate().get(Calendar.MINUTE)));
  }
  
  @Deprecated
  public double topAnchor(double max){
    double minPx=max/(24.0*60.0);
    return minPx*(event.getStartDate().get(Calendar.HOUR_OF_DAY)*60.0+event.getStartDate().get(Calendar.MINUTE));
  }
  @Deprecated
  public double bottomAnchor(double max){
    double minPx=max/(24.0*60.0);
    double minutes=event.getEndDate().get(Calendar.HOUR_OF_DAY)*60.0+event.getEndDate().get(Calendar.MINUTE);
    return max-minPx*minutes;
  }
  
  
  protected UIEvent(CalendarEvent e){
    this(e.getTitle());
  }
  
  protected UIEvent(){
    FXMLLoader loader=new FXMLLoader(getClass().getResource("UIEvent.fxml"));
    loader.setController(this);
    try{
      loader.load();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  public AnchorPane getRoot(){
    return root;
  }
  
  public void setTitle(String title){
    this.title.setText(title);
  }
  

}

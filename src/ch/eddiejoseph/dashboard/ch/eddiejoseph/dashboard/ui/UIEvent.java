package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class UIEvent {
  @FXML
  Label title;
  @FXML
  AnchorPane root;
  
  private final double SHRINK=2.0;
  
  protected UIEvent(String title){
    this();
    this.title.setText(title);
  }
  
  public UIEvent(CalendarEvent e, Pane p){
    this(e);
    double minPx=p.getHeight()/(24*60);
    root.setPrefSize(p.getWidth()-2*SHRINK,minPx* TimeUnit.MILLISECONDS.toMinutes(e.getEndDate().getTimeInMillis()-e.getStartDate().getTimeInMillis())-2*SHRINK);
    root.relocate(SHRINK,SHRINK+minPx*(e.getStartDate().get(Calendar.HOUR_OF_DAY)*60+e.getStartDate().get(Calendar.MINUTE)));
    
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

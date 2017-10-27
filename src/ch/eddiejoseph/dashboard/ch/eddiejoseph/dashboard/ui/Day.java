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

public class Day {
  @FXML
  Label title;
  @FXML
  AnchorPane root;
  
  public Day(String title){
    this();
    this.title.setText(title);
  }
  
  public Day(CalendarEvent e, Pane p){
    this(e);
    double minPx=p.getHeight()/(24*60);
    root.setPrefSize(p.getWidth(),minPx* TimeUnit.MILLISECONDS.toMinutes(e.getEndDate().getTimeInMillis()-e.getStartDate().getTimeInMillis()));
    root.relocate(0,minPx*(e.getStartDate().get(Calendar.HOUR_OF_DAY)*60+e.getStartDate().get(Calendar.MINUTE)));
    
  }
  
  public Day(CalendarEvent e){
    this(e.getTitle());
  }
  
  public Day(){
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Day.fxml"));
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

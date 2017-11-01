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
  private AnchorPane eventPane;
  @FXML
  private Label title;
  @FXML
  private AnchorPane titlePane;
  @FXML
  private AnchorPane wholeDay;
  
  public Day(String title){
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Day.fxml"));
    loader.setController(this);
    //loader.setRoot(this);
    try{
      loader.load();
    }catch(IOException e){
      e.printStackTrace();
    }
    
    
    this.title.setText(title);
  }
  
  public AnchorPane getWholeDay() {
    return wholeDay;
  }
  
  public AnchorPane getEventPane() {
    return eventPane;
  }
  
  public void setTitle(String title){
    this.title.setText(title);
  }
  
}

package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.CalendarEvent;
import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
  
  
  public UIEvent(CalendarEvent e, AnchorPane p){
    this(e);
    event=e;
    upperPane=p;
    //resize(p.getHeight()-50,p.getWidth());
    resize(Integer.parseInt(PropertiesFactory.getPropertie("height"))-50,p.getWidth());
  }
  public UIEvent(CalendarEvent e, AnchorPane p,int index, int nrOfCals){
    this(e);
    event=e;
    upperPane=p;
    //resize(p.getHeight()-50,p.getWidth());
    resize(Integer.parseInt(PropertiesFactory.getPropertie("height"))-85,p.getWidth()/nrOfCals,p.getWidth()/nrOfCals*index);
  }
  

  
  public void resizeh(double maxHeight){
    resize(maxHeight,upperPane.getWidth());
  }
  public void resizew(double width){
    System.out.println(upperPane.getHeight());
    resize(upperPane.getHeight(),width);
  }
  
  private void resize(double height, double width){
    resize(height,width,0);
  }
  
  private void resize(double height, double width,double wofset){
    double minPx=height/(24*60);
    root.setPrefSize(width-2*SHRINK,minPx* TimeUnit.MILLISECONDS.toMinutes(event.getEndDate().getTimeInMillis()-event.getStartDate().getTimeInMillis())-2*SHRINK);
    root.relocate(wofset+SHRINK,SHRINK+minPx*((event.getStartDate().get(Calendar.HOUR_OF_DAY))*60+event.getStartDate().get(Calendar.MINUTE)));
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

package ch.eddiejoseph.dashboard.ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.fx.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RunUI extends Application {
  
  public static void main(String[] args) {
    launch(args);
  }
  Stage primaryStage;
  private AnchorPane rootLayout;
  Scene scene;
  @Override
  public void start(Stage primaryStage) {
    this.primaryStage=primaryStage;
    this.primaryStage.setTitle("Dashboard");
    
    initRunUI();
    
    //showDays();
    
    //rootLayout.setBackground(new Background(new BackgroundFill(Color.web("#ACACE6"), null,null)));
    
    this.primaryStage.show();
    
  }
  
  public Scene getScene(){
    return scene;
  }
  
  public void initRunUI(){
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(RunUI.class.getResource("CalViewEmpty.fxml"));
      rootLayout = (AnchorPane) loader.load();
      scene=new Scene(rootLayout);
  
      CalendarControllerEmpty controller = loader.getController();
      controller.setMainApp(this);
      
      
      primaryStage.setScene(scene);
      
      scene.widthProperty().addListener(controller);
      scene.heightProperty().addListener(controller);
      primaryStage.show();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  
  public void showDays(){
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(RunUI.class.getResource("UIEvent.fxml"));
      Canvas day = (Canvas) loader.load();
      for(int c=0;c<7;c++) {
        System.out.println(rootLayout.getChildren().get(0));
        ((GridPane) rootLayout.getChildren().get(0)).add(day, c, 0);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  
}

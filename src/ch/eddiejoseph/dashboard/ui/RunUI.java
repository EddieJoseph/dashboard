package ch.eddiejoseph.dashboard.ui;

import ch.eddiejoseph.dashboard.dataloader.calendar.PropertiesFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.*;
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
    primaryStage.setFullScreen(Boolean.parseBoolean(PropertiesFactory.getPropertie("fullscreen")));
    if(Boolean.parseBoolean(PropertiesFactory.getPropertie("fullscreen"))) {
      primaryStage.setHeight(Integer.parseInt(PropertiesFactory.getPropertie("height")));
    }else{
      primaryStage.setHeight(Integer.parseInt(PropertiesFactory.getPropertie("height"))+50);
    }
    primaryStage.setWidth(Integer.parseInt(PropertiesFactory.getPropertie("width")));
    primaryStage.setResizable(false);
    primaryStage.setAlwaysOnTop(true);
    initRunUI();
    this.primaryStage.show();
    Platform.runLater(new Runnable(){
      public void run(){
        primaryStage.requestFocus();
        primaryStage.setIconified(true);
        primaryStage.setIconified(false);
      }
    }
    );
    
    scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("deteckted click");
        System.exit(0);
      }
    });
    scene.setOnTouchPressed(new EventHandler<TouchEvent>() {
      @Override
      public void handle(TouchEvent event) {
        System.out.println("deteckted touch");
        System.exit(0);
      }
    });
  }
  
  public Scene getScene(){
    return scene;
  }
  
  public void initRunUI(){
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(RunUI.class.getResource("CalViewEmpty.fxml"));
      rootLayout = (AnchorPane) loader.load();
      rootLayout.setPrefHeight(primaryStage.getHeight());
      rootLayout.setPrefWidth(primaryStage.getWidth());
      scene=new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
      CalendarControllerEmpty controller = loader.getController();
      controller.setMainApp(this);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  
}

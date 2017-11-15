package ch.eddiejoseph.dashboard.dataloader.calendar;

import java.io.*;
import java.util.Properties;

public class PropertiesFactory {
  private static Properties prop=null;
  private static void checkExists(){
    if(prop==null){
      prop=new Properties();
      InputStream in=null;
      in=PropertiesFactory.class.getResourceAsStream("calendar.properties");
      if(in==null){
        try {
          in= new FileInputStream(new File("calendar.properties"));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
      try {
        prop.load(in);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static Properties getProperties(){
    checkExists();
    return prop;
  }
  
  public static String getPropertie(String key){
    checkExists();
    return prop.getProperty(key);
  }
  
  public static boolean exists(String key){
    checkExists();
    return prop.containsKey(key);
  }
  
}

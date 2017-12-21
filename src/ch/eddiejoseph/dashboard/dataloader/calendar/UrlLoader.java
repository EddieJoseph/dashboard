package ch.eddiejoseph.dashboard.dataloader.calendar;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class UrlLoader {
  
  private static Document xmldoc=null;

private static void loadXML(){
  if(xmldoc==null){
    SAXReader reader=new SAXReader();
    URL calurl=UrlLoader.class.getResource("calendars.xml");
    if (calurl==null){
      File xlm=new File("calendars.xml");
      try {
        calurl = xlm.toURI().toURL();
      }catch(Exception e){
        e.printStackTrace();
        System.exit(1);
      }
    }
    try {
      xmldoc = reader.read(calurl);
    }catch (Exception e){
      e.printStackTrace();
      System.exit(1);
    }
    //Element root=xmldoc.getRootElement();
  }
}

public static  URL[][] getCalendars(){
  loadXML();
  
  Element root=xmldoc.getRootElement();
  List<Node> nodes=xmldoc.selectNodes("/body/calendar");
  URL[][] urls=new URL[nodes.size()][];
  String [] res=new String[nodes.size()];
  for (int c=0;c<res.length;c++){
    List<Node> components=nodes.get(c).selectNodes("component");
    URL [] tmp=new URL[components.size()];
    for (int c2=0;c2<components.size();c2++){
      try {
        tmp[c2] = new URL(components.get(c2).selectSingleNode("url").getText());
      }catch(Exception e){
        e.printStackTrace();
      }
    }
    urls[c]=tmp;
  }
  return urls;
  }

public static String[] getNames(){
  loadXML();
  Element root=xmldoc.getRootElement();
  List<Node> nodes=xmldoc.selectNodes("/body/calendar");
  String [] res=new String[nodes.size()];
  for (int c=0;c<res.length;c++){
  res[c]=nodes.get(c).selectSingleNode("owner").getText();
  }
  return res;
}

public static int[][] getColors(){
  loadXML();
  Element root=xmldoc.getRootElement();
  List<Node> nodes=xmldoc.selectNodes("/body/calendar");
  int [][] cols=new int[nodes.size()][3];
  for (int c=0;c<nodes.size();c++){
    cols[c][0]=Integer.parseInt(nodes.get(c).selectSingleNode("color").selectSingleNode("r").getText());
    cols[c][1]=Integer.parseInt(nodes.get(c).selectSingleNode("color").selectSingleNode("g").getText());
    cols[c][2]=Integer.parseInt(nodes.get(c).selectSingleNode("color").selectSingleNode("b").getText());
  }
  return cols;
}


}

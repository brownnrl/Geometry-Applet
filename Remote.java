import java.awt.*;
import java.applet.Applet;
import java.util.StringTokenizer;

public class Remote extends Applet {
  Color background;
  String mainName;
  Applet main;
  int stage;

  public String [][] getParameterInfo() {
    String[][] pinfo = {
	{"background",	"Color",		"background color"},
	{"stage",	"int",			"stage"}
    };
    return pinfo;
  }

  Color randomColor() {
    int c;
    float hue = (float)(Math.random()*1.0);
    if (Math.random()<0.2) 
      c = Color.HSBtoRGB(hue,
	(float)(1.0-Math.random()*Math.random()),  // saturation
	(float)(1.0-Math.random()*Math.random())   // brightness
      );
    else if (Math.random()<0.4)
      c = Color.HSBtoRGB(hue,1.0f,(float)(0.3+0.7*Math.random()));
    else
      c = Color.HSBtoRGB(hue,(float)(Math.random()),1.0f);
    return new Color(c);
  }

  Color parseColor (String str) {
    if ("none".equals(str)) return null;
    if ("random".equals(str)) return randomColor();
    if ("background".equals(str)) return background;
    if ("brighter".equals(str)) return background.brighter();
    if ("darker".equals(str)) return background.darker();
    StringTokenizer t = new StringTokenizer(str,",");
    if (!t.hasMoreTokens()) return null;
    try {
      float hue = (float)(Integer.parseInt(t.nextToken())/360.0);
      if (!t.hasMoreTokens()) return null;
      float sat = (float)(Integer.parseInt(t.nextToken())/100.0);
      if (!t.hasMoreTokens()) return null;
      float bri = (float)(Integer.parseInt(t.nextToken())/100.0);
      return new Color(Color.HSBtoRGB(hue,sat,bri));
    } catch (NumberFormatException exc) {
      return null;
  } }

  public void init() {
    String param = getParameter("main");
    mainName = (param != null) ? param : "main";
    param = getParameter("background");
    if (param != null) {
      background = parseColor(param);
      setBackground(background);
    }
    param = getParameter("stage");
    try {
      stage = (param == null) ? Integer.MAX_VALUE : Integer.parseInt(param);
    } catch (NumberFormatException exc) {
      stage = Integer.MAX_VALUE;
    }
    main = getAppletContext().getApplet(mainName);
  }

  public boolean handleEvent(Event evt) {
    if (main == null)
      main = getAppletContext().getApplet(mainName);
    return (main != null) ? main.handleEvent(evt) : false;
} }

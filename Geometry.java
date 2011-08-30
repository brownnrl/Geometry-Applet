
/*----------------------------------------------------------------------+
|	Title:	Geometry.java						|
|		Java Applet						|
|									|
|	Author:	David E. Joyce						|
|		Department of Mathematics and Computer Science   	|
|		Clark University					|
|		Worcester, MA 01610-1477				|
|		U.S.A.							|
|									|
|		http://aleph0.clarku.edu/~djoyce/home.html		|
|		djoyce@clarku.edu					|
|									|
|	Date:	Original: February, 1996.  Version 1.3.1: August, 1996	|
|		Version 2.0.0 May, 1997, 3d.				|
|		Version 2.2  Feb, 1998.					|
|		Version 2.3  Sep, 2000.					|
+----------------------------------------------------------------------*/

import java.awt.*;
import java.applet.Applet;
//import java.lang.*;
//import java.io.*;
import java.util.StringTokenizer;

public class Geometry extends Applet {

  boolean debug;
  String title;
  Color background;
  static Color orange = new Color(255,150,0);
  Slate slate;
  StringBuffer message = new StringBuffer(100);
  int parCount = 0;
  int init = 0;
  int defaultAlign = 0;
  boolean floating = false;
  ClientFrame floater;
  int stage;
  Button closeButton, resetButton, returnButton;
  Dimension baseSize;
  String font;
  int fontsize;

  public String getAppletInfo() {
    return "Geometry. Copyright 1996-98, David Joyce, Clark University. Version 2.2";
  }

  public String[][] getParameterInfo() {
    String[][] pinfo = {
	{"background",	"Color",		"background color"},
	{"e[i]", 	"element", 		"element information"},
	{"pivot",	"String",		"name of pivot point, if any"},
	{"debug",	"boolean",		"output debugging info"},
	{"init",	"int",			"initial stage"},
	{"title",	"String",		"title"},
	{"font",	"String",		"font name"},
	{"fontsize",	"int",			"font size"},
	{"align",	"String",		"label alignment"}
    };
    return pinfo;
  }

  Color randomColor() {
    int c = Color.HSBtoRGB ((float)(Math.random()*1.0),	// hue
			    (float)(Math.random()),	// sat
			    1.0f);			// bright
    return new Color(c);
  }

  static String colorName[] = {
	"black", "blue", "cyan", "darkGray", "gray", "green","lightGray", 
	"magenta", "orange", "pink", "red", "white", "yellow"};
  static Color constColor[] = {
	Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray,
	Color.green, Color.lightGray, Color.magenta, Color.orange,
	Color.pink, Color.red, Color.white, Color.yellow};

  Color parseColor (String str) {
    if (str==null || "none".equals(str) || "0".equals(str)) return null;
    if ("random".equals(str)) return randomColor();
    if ("background".equals(str)) return background;
    if ("brighter".equals(str)) return background.brighter();
    if ("darker".equals(str)) return background.darker();
    for (int i=0; i<colorName.length; ++i)
      if (colorName[i].equals(str)) return constColor[i];
    try { return new Color(Integer.parseInt(str,16));}
    catch (NumberFormatException exc) {}
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

  static String alignName[] = {
	"central", "left", "right", "above", "below"};
  static int constAlign[] = {
	Element.CENTRAL, Element.LEFT, Element.RIGHT, Element.ABOVE,
	Element.BELOW};

  int parseAlign (String param) {
    if (param == null) return 0;
    for (int i=0; i<alignName.length; ++i)
      if (alignName[i].equals(param)) return constAlign[i];
    return 0;
  };

  Element parseElement (String param, StringBuffer message) {
    StringTokenizer t = new StringTokenizer(param,";");
    if (!t.hasMoreTokens()) {
      message.append("Parameter is empty.");
      return null;
    }
    String name = t.nextToken();
    if (!t.hasMoreTokens()) {
      message.append("Parameter "+param+" missing an element class.");
      return null;
    }
    String elementClass = t.nextToken();
    if (!t.hasMoreTokens()) {
      message.append("Parameter "+param+" missing a construction.");
      return null;
    }
    String construction = t.nextToken();
    if (!t.hasMoreTokens()) {
      message.append("Parameter "+param+" missing construction data.");
      return null;
    }
    String data = t.nextToken();
    Element e = slate.constructElement(name,elementClass,construction,data,message);
    if (e == null) return null;
    if (t.hasMoreTokens())
      e.nameColor = parseColor(t.nextToken());
    else if (e.inClass("PointElement"))
      e.nameColor = Color.black;
    e.align = defaultAlign;
    if (t.hasMoreTokens())
      e.vertexColor = parseColor(t.nextToken());
    else if (e.dimension == 0)
      e.vertexColor = e.dragable ?
	(e.inClass("PlaneSlider") ? Color.red : orange)
	: Color.black;
    if (t.hasMoreTokens())
      e.edgeColor = parseColor(t.nextToken());
    else if (e.dimension > 0)
      e.edgeColor = Color.black;
    if (t.hasMoreTokens())
      e.faceColor = parseColor(t.nextToken());
    else if (e.dimension == 2)
      e.faceColor = background.brighter();
    return e;
  }

  public void init() {
    baseSize = getSize();
    parCount = 0;
    getAppletContext().showStatus("initializing Geometry applet");
    String param = getParameter("debug");
    debug = (param == null) ?
      false :
      (param.equalsIgnoreCase("yes") || param.equalsIgnoreCase("true"));
    param = getParameter("init");
    try {
      init = (param == null) ? Integer.MAX_VALUE : Integer.parseInt(param);
    } catch (NumberFormatException exc) {
      init = Integer.MAX_VALUE;
    }

    slate = new Slate(100);
    title = getParameter("title");
    if (title == null) title = "Geometry";
    font = getParameter("font");
    if (font == null) font = "TimesRoman";
    param = getParameter("fontsize");
    try {
      fontsize = (param == null) ? 18 : Integer.parseInt(param);
    } catch (NumberFormatException exc) {
      fontsize = 18;
    }
    slate.setFont(new Font(font,Font.ITALIC,fontsize));
    param = getParameter("align");
    defaultAlign = parseAlign(param);
    if (debug) System.out.println ("param="+param+" defaultAlign="+defaultAlign);

    param = getParameter("background");
    if (param != null) 
      background = parseColor(param);
    if (background == null)
      background = getBackground();
    else
      slate.setBackground(background);

    setLayout(new BorderLayout());
    add("Center",slate);
    stage = init;
    while (parCount != -1 && parCount < stage) {
      param = getParameter("e["+(parCount+1)+"]");
      if (debug)
        System.out.println("Parsing parameter e["+(parCount+1)+"]="+param);
      if (param == null)
        parCount = -1;
      else {
        Element e = parseElement(param,message);
        if (e == null) {
          System.out.println("Parameter e["+(parCount+1)+"]="+param
		+" not parsed. " + message.toString());
          parCount = -1;
        } else
	  ++parCount;
    } }
    param = getParameter("pivot");
    if (param != null)
      slate.setPivot(param);
    
    slate.updateCoordinates(0);
  }

  public boolean keyDown(Event evt, int key) {
    if (key=='u' || key=='U' || (key=='\n' && !floating)) {
      if (!floating) {
        floating=true;		// typing u or return starts floating window
	floater = new ClientFrame(title,this);
	remove(slate);
	if (resetButton == null)
	  resetButton = new Button("Reset");
	if (closeButton == null)
	  closeButton = new Button("Close");
	floater.add("Center",slate);
	Panel south = new Panel();
	south.add(resetButton);
	south.add(closeButton);
	floater.add("South",south);
	floater.resize(baseSize.width,baseSize.height+50);
	floater.show();
      }
      return true;
    } else if (key=='d' || key=='D' || key=='\n') {
      if (floating) {
        floating=false;		// typing d or return drops floating window
        floater.hide();
	floater.remove(slate);
	slate.resize(baseSize);
	slate.reshape(0, 0, baseSize.width, baseSize.height);
	add(slate);
	invalidate();
	layout();
//	floater.dispose();	dispose isn't recognized for some reason
	floater = null;
      }
      return true;
    } else return false;
  }

  public boolean action(Event evt, Object arg) {
    if (evt.target instanceof Button) {
      if (evt.target == closeButton || evt.target == returnButton) {
	if (floating) {
	  floating=false;
          floater.hide();
          floater.remove(slate);
	  slate.reshape(0, 0, baseSize.width, baseSize.height);
	  add(slate);
	  invalidate();
	  layout();
//	  floater.dispose();
	  floater = null;
	}
	return true;
      } else if (evt.target == resetButton) {
	slate.reset();		
	slate.repaint();
	return true;
      } else return false;
    } else return false;
} }





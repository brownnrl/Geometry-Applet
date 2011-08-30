/*----------------------------------------------------------------------+
|	Title:	Slate.java						|
|		Java class Slate extends Canvas				|
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
|	Date:	February, 1996. Version 2.0.0 May, 1997.		|
+----------------------------------------------------------------------*/

import java.awt.*;
import java.lang.String;
import java.util.StringTokenizer;

public class Slate extends Canvas {

  int eCount;
  Element element[];
  boolean preexists[];
  int picki = -1;
  PointElement pick = new PointElement();
  PlaneElement screen;

  Slate (int n) {
    // initialize arrays
    if (n < 15) n=15;
    element = new Element[n];
    preexists = new boolean[n];
    // set up the screen plane
    element[0] = new FixedPoint(0.0,0.0,0.0);
    element[0].name = "screenorigin";
    element[1] = new FixedPoint(1.0,0.0,0.0);
    element[0].name = "screenx";
    element[2] = new FixedPoint(0.0,1.0,0.0);
    element[0].name = "screeny";
    screen = new PlaneElement((PointElement)element[0],
      (PointElement)element[1],(PointElement)element[2]);
    screen.name = "screen";
    screen.isScreen = true;
    element[3] = screen;
    eCount = 4;
  }

  void extendArrays() {
    int len = element.length;
    Element newelement[] = new Element[2*len];
    boolean newpreexists[] = new boolean[2*len];
    for (int i=0; i<len; ++i) {
      newelement[i] = element[i];
      newpreexists[i] = preexists[i];
    }
    element = newelement;
    preexists = newpreexists;
  }
  
  Element lookupElement(String name) {
    for (int i=0; i<eCount; ++i)
      if (name.equals(element[i].name))
	return element[i];
    return null;
  }

  static String elementClassName[] = {
	"point", "line", "circle", "polygon", "sector", "plane", 
	"sphere", "polyhedron"};

  static int lookupElementClass (String s) {
    for (int i=0; i<elementClassName.length; ++i)
      if (elementClassName[i].equals(s)) return i;
    return -1;
  }

  static String constructionName[][] =
    { { // constructions for points
        "free",  		"midpoint",		"intersection",	
	"first",		"last",			"center",
	"lineSlider",		"circleSlider",		"circumcenter",
	"vertex",		"foot",			"cutoff",
	"extend",		"parallelogram",	"similar",
	"perpendicular",	"proportion",		"invert",
	"meanProportional",	"planeSlider",		"sphereSlider",
	"angleBisector",	"angleDivider",		"fixed",
	"lineSegmentSlider",	"harmonic"},

      { // constructions for lines
        "connect",		"angleBisector",	"angleDivider",
	"foot",			"chord",		"bichord",
	"perpendicular",	"cutoff",		"extend",
	"parallel",		"similar",		"proportion",
	"meanProportional"},

      { // constructions for circles
        "radius",		"circumcircle",		"invert",
        "intersection"},

      { // constructions for polygons
        "square", 		"triangle", 		"quadrilateral",
	"pentagon",		"hexagon",		"equilateralTriangle",
	"parallelogram",	"regularPolygon",	"starPolygon",
	"similar",		"application",		"octagon",
	"face"},

      { // constructions for sectors
        "sector",		"arc"},
      
      { // constructions for planes
        "3points",		"perpendicular",	"parallel",
        "ambient"},
      
      {  // construction for spheres
        "radius"},
        
      {  // constructions for polyhedra
        "tetrahedron",		"parallelepiped",	"prism",
        "pyramid"},
    };

  public static int lookupConstructionMethod (int eClass, String s) {
    for (int i=0; i<constructionName[eClass].length; ++i)
      if (constructionName[eClass][i].equals(s)) return i;
    return -1;
  }

  public static String constructionDataType[][][][] =
    { { // point constructions
	{ // 1. free point construction
	  {"Integer","Integer"} },	
	{ // 2. midpoint construction
	  {"PointElement","PointElement"} },
	{ // 3. intersection constructions
	  {"PointElement","PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PlaneElement"},
	  {"PlaneElement","PointElement","PointElement"} },
	{ // 4. first point of a line
	  {"PointElement", "PointElement"} },
	{ // 5. last point of a line
	  {"PointElement", "PointElement"} },
	{ // 6. center of a circle or sphere
	  {"CircleElement"},	
	  {"SphereElement"} },	
	{ // 7. point sliding along a line
	  {"PointElement","PointElement","Integer","Integer"},
	  {"PointElement","PointElement","Integer","Integer","Integer"} },
	{ // 8. point sliding along a circle
	  {"CircleElement","Integer","Integer"},
	  {"CircleElement","Integer","Integer","Integer"} },
	{ // 9. circumcenter
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"} },
	{ // 10. vertex of a polygon
	  {"PolygonElement","Integer"} },
	{ // 11. foot constructions
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PlaneElement"} }, // plane projection
	{ // 12. cutoff construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // 13. extend construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // 14. parallelogram construction
	  {"PointElement","PointElement","PointElement"} },
	{ // 15. similar triangle constructions
	  {"PointElement","PointElement","PointElement",
		"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement","PointElement",
		"PointElement","PointElement","PlaneElement"} },
	{ // 16. perpendicular constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement"},
	  {"PointElement","PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement","PointElement",
	   "PlaneElement"},
	  {"PointElement","PlaneElement","PointElement","PointElement"} },
	{ // 17. proportion construction
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PointElement","PointElement","PointElement","PointElement"} },
	{ // 18. invert in a circle
	  {"PointElement","CircleElement"} },
	{ // 19. meanProportional construction
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PointElement","PointElement"} },
	{ // 20. point sliding along a plane
	  {"PlaneElement","Integer","Integer","Integer"} },
	{ // 21. point sliding on a sphere
	  {"SphereElement","Integer","Integer","Integer"} },
 	{ // 22. angle bisector construction
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"} }, 
	{ // 23. angle divider construction
	  {"PointElement","PointElement","PointElement","Integer"},
	  {"PointElement","PointElement","PointElement","PlaneElement",
	   "Integer"} }, 
	{ // 24. fixed point  
	  {"Integer","Integer"},
	  {"Integer","Integer","Integer"} },
	{ // 25. point sliding along a line segment
	  {"PointElement","PointElement","Integer","Integer"},
	  {"PointElement","PointElement","Integer","Integer","Integer"} },
	{ // 26. fourth harmonic
	  {"PointElement","PointElement","PointElement"} },
     },

      { // line constructions
	{ // connect two points
	  {"PointElement","PointElement"} },	
 	{ // angle bisector construction
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"}}, 
	{ // angle divider construction
	  {"PointElement","PointElement","PointElement","Integer"},
	  {"PointElement","PointElement","PointElement","PlaneElement",
	   "Integer"} }, 
	{ // foot constructions
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PlaneElement"} }, // plane projection
	{ // chord construction
	  {"PointElement","PointElement","CircleElement"} },
	{ // bichord construction
	  {"CircleElement","CircleElement"} },
	{ // perpendicular constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement"},
	  {"PointElement","PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement","PointElement",
	   "PlaneElement"},
	  {"PointElement","PlaneElement","PointElement","PointElement"} },
	{ // cutoff construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // extend construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // parallel construction
	  {"PointElement","PointElement","PointElement"} },
	{ // similar triangle (angle) constructions
	  {"PointElement","PointElement","PointElement",
		"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement","PointElement",
		"PointElement","PointElement","PlaneElement"} },
	{ // proportion construction
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PointElement","PointElement","PointElement","PointElement"} },
	{ // meanProportional construction
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PointElement","PointElement"} },
      },

      { // circle constructions
	{ // center and radius constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"} },
	{ // circumcircle construction
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"} },
	{ // invert in another circle
	  {"CircleElement","CircleElement"} },
	{ // intersection construction
	  {"SphereElement","SphereElement"} },
      },

      { // polygon constructions
	{ // square constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement"} },
	{ // triangle construction
	  {"PointElement","PointElement","PointElement"} },
	{ // quadrilateral construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // pentagon construction
	  {"PointElement","PointElement","PointElement",
	   "PointElement","PointElement"} },
	{ // hexagon construction
	  {"PointElement","PointElement","PointElement",
	   "PointElement","PointElement","PointElement"} },
	{ // equilateral triangle constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement"} },
	{ // parallelogram construction
	  {"PointElement","PointElement","PointElement"} },
	{ // regular polygon constructions
	  {"PointElement","PointElement","Integer"},
	  {"PointElement","PointElement","PlaneElement","Integer"} },
	{ // star polygon constructions
	  {"PointElement","PointElement","Integer","Integer"},
	  {"PointElement","PointElement","PlaneElement","Integer","Integer"} },
	{ // similar triangle constructions
	  {"PointElement","PointElement","PointElement",
		"PointElement","PointElement"},
	  {"PointElement","PointElement","PlaneElement","PointElement",
		"PointElement","PointElement","PlaneElement"} },
	{ // application
	  {"PolygonElement","PointElement","PointElement","PointElement"} },
	{ // octagon construction
	  {"PointElement","PointElement","PointElement","PointElement",
	   "PointElement","PointElement","PointElement","PointElement"} },
	{ // face of a polyhedron
	  {"PolyhedronElement","Integer"} },
      },

      { // sector constructions
	{ // center and arc construction
	  {"PointElement","PointElement","PointElement"},
           {"PointElement","PointElement","PointElement","PlaneElement"} },
	{ // arc construction
	  {"PointElement","PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement","PlaneElement"} },
      },
      
      { // plane constructions
	{ // 3points construction
	  {"PointElement","PointElement","PointElement"} },
	{ // perpendicular construction
	  {"PointElement","PointElement"} },
	{ // parallel construction
	  {"PlaneElement","PointElement"} },
	{ // ambient planes
	  {"PointElement"},
	  {"CircleElement"} },
      },      	

      { // sphere constructions
	{ // radius constructions
	  {"PointElement","PointElement"},
	  {"PointElement","PointElement","PointElement"} },
      },      	

      { // polyhedron constructions
	{ // tetrahedorn construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // parallelepiped construction
	  {"PointElement","PointElement","PointElement","PointElement"} },
	{ // prism construction
	  {"PolygonElement","PointElement","PointElement"} },
	{ // pyramid construction
	  {"PolygonElement","PointElement"} },
    } };

  int selectDataChoice (String data, String datachoices[][], 
	PointElement p[], Element e[], int n[], StringBuffer message) {
    // parse the data string to look for a type match among the
    // various datachoices.  Store the resulting elements in the
    // e array and the resulting integers in the n array.
    StringTokenizer t = new StringTokenizer(data,",");
    int pcount=0, ecount=0, ncount=0;
    while (t.hasMoreTokens()) {
      String next = t.nextToken();
      try { // is it an integer?
	n[ncount] = Integer.parseInt(next);
	ncount++;
      } catch (NumberFormatException exc) { // is it an element?
	Element elt = lookupElement (next);
	if (elt == null) {
	  message.append("Data element "+next+" not found. ");
	  return -1;
	}
	if (elt.inClass("PointElement"))
	  p[pcount++] = (PointElement)elt;
	else if (elt.inClass("LineElement")) { // split into two points
	  p[pcount++] = ((LineElement)elt).A;
	  p[pcount++] = ((LineElement)elt).B;
	} else
	  e[ecount++] = elt;
    } }
    // now determine which choice is right
    int i,j;
    for (i=0; i<datachoices.length; ++i) {	// try the i'th choice
      if (datachoices[i].length != pcount+ncount+ecount) continue;
      int ps=0, es=0, ns=0;
      for (j=0; j<datachoices[i].length; ++j) {
	if (datachoices[i][j].equals("Integer")) {
	  if (ns >= ncount) break;
	  else ns++;
	} else if (datachoices[i][j].equals("PointElement")) {
	  if (ps >= pcount) break;
	  else ps++;
	} else {	// it's some kind of Element
	  if (es >= ecount) break;
	  else if (!e[es].inClass(datachoices[i][j])) break;
	  else es++; 
      } }
      if (j == datachoices[i].length) break;
    }
    if (i == datachoices.length) {
      message.append("Data does not fit construction method. ");
      return -1;
    } else return i;
  }

  void createElement(int c, int m, int choice, PointElement P[], Element E[],
                     int N[]) {
    switch (c) {
      case 0:	// point constructions
	switch (m) {
	  case 0:	// free point construction (slide on screen)
	    element[eCount] = new PlaneSlider(screen,N[0],N[1],0.0);
	    return;
	  case 1:	// midpoint constructions
	    element[eCount] = new Midpoint(P[0],P[1]);
	    return;
	  case 2:	// intersection constructions
	    switch (choice) {
	      case 0:
	        element[eCount] = new Intersection(P[0],P[1],P[2],P[3],screen);
		break;
	      case 1:
	        element[eCount] = new Intersection(P[0],P[1],P[2],P[3],
		  (PlaneElement)E[0]);
		break;
	      case 2:
	        element[eCount] = new IntersectionPL ((PlaneElement)E[0],
	          P[0],P[1]);
	        break;
	    }
	    return;
	  case 3:	// first point of a line
	    element[eCount] = P[0];
	    preexists[eCount] = true;
	    return;
	  case 4:	// last point of a line
	    element[eCount] = P[1];
	    preexists[eCount] = true;
	    return;
	  case 5:	// center of a circle or sphere
	    if (choice == 0) 
	      element[eCount] = ((CircleElement)E[0]).Center;
	    else
	      element[eCount] = ((SphereElement)E[0]).Center;
	    preexists[eCount] = true;
	    return;
	  case 6:	// point sliding along a line
	    if (choice == 0) N[2] = 0;
	      element[eCount] = new LineSlider(P[0],P[1],N[0],N[1],N[2],false);
	    return;
	  case 7:	// point sliding along a circle
	    if (choice == 0) N[2] = 0;
	    element[eCount] = new CircleSlider((CircleElement)E[0],N[0],N[1],N[2]);
	    return;
	  case 8:	// circumcenter given three points
	    if (choice == 0) E[0] = screen;
	    Circumcircle circ = new Circumcircle(P[0],P[1],P[2],(PlaneElement)E[0]);
	    element[eCount++] = circ;
	    element[eCount] = circ.Center;
	    preexists[eCount] = true;
	    return;
	  case 9:	// vertex of a polygon
	    element[eCount] = ((PolygonElement)E[0]).V[N[0]-1];
	    preexists[eCount] = true;
	    return;
	  case 10:	// foot constructions
	    if (choice == 0)
	      element[eCount] = new Foot(P[0],P[1],P[2]);
	    else
	      element[eCount] = new PlaneFoot(P[0],(PlaneElement)E[0]);
	    return;
	  case 11:	// cutoff construction
	    element[eCount] = new Layoff(P[0],P[0],P[1],P[2],P[3]);
	    return;
	  case 12:	// extend construction
	    element[eCount] = new Layoff(P[1],P[0],P[1],P[2],P[3]);
	    return;
	  case 13:	// parallelogram construction
	    element[eCount] = new Layoff(P[0],P[1],P[2],P[1],P[2]);
	    return;
	  case 14:	// similar triangle constructions
	    if (choice == 0) E[0] = E[1] = screen;
	    element[eCount] = new Similar(P[0],P[1],(PlaneElement)E[0],P[2],P[3],
	        P[4],(PlaneElement)E[1]);
	    return;
	  case 15:	// perpendicular constructions
	    if (choice == 0)
	      element[eCount] = new Perpendicular(P[0],P[1],screen,P[0],P[1]);
	    else if (choice == 1)
	      element[eCount] = new Perpendicular(P[0],P[1],(PlaneElement)E[0],P[0],P[1]);
	    else if (choice == 2)
	      element[eCount] = new Perpendicular(P[0],P[1],screen,P[2],P[3]);
	    else if (choice == 3)
	      element[eCount] = new Perpendicular(P[0],P[1],(PlaneElement)E[0],P[2],P[3]);
	    else
	      element[eCount] = new PlanePerpendicular(P[0],(PlaneElement)E[0],P[1],P[2]);
	    element[eCount+1] = ((LineElement)element[eCount]).B;
	    preexists[++eCount] = true;
	    return;
	  case 16:	// proportion construction
	    element[eCount] = new Proportion(P[0],P[1],P[2],P[3],P[4],P[5],P[6],P[7]);
	    return;
	  case 17:	// invert in a circle
	    element[eCount] = new InvertPoint(P[0],(CircleElement)E[0]);
	    return;
	  case 18:	// meanProportional construction
	    element[eCount] = new MeanProportional(P[0],P[1],P[2],P[3],P[4],P[5]);
	    return;
	  case 19:	// planeSlider construction
	    element[eCount] = new PlaneSlider((PlaneElement)E[0],N[0],N[1],N[2]);
	    return;
	  case 20:	// sphereSlider construction
	    element[eCount] = new SphereSlider((SphereElement)E[0],N[0],N[1],N[2]);
	    return;
	  case 21:	// angle bisector construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] = new AngleDivider(P[0],P[1],P[2],(PlaneElement)E[0],2);
	    return;
	  case 22:	// angle divider construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] = new AngleDivider(P[0],P[1],P[2],(PlaneElement)E[0],N[0]);
	    return;
	  case 23:	// fixed point
	    if (choice == 0) N[2] = 0;
	    element[eCount] = new FixedPoint(N[0],N[1],N[2]);
	    return;
	  case 24:	// point sliding along a line segment
	    if (choice == 0) N[2] = 0;
	    element[eCount] = new LineSlider(P[0],P[1],N[0],N[1],N[2],true);
	    return;
	  case 25:	// fourth harmonic
	    element[eCount] = new Harmonic(P[0],P[1],P[2]);
	    return;
	}

      case 1:	// line constructions
	switch (m) {
	  case 0:	// connect construction
	    element[eCount] = new LineElement(P[0],P[1]); 
	    return;
	  case 1:	// angle bisector construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] = new AngleDivider(P[0],P[1],P[2],(PlaneElement)E[0],2);
	    element[++eCount] = new LineElement(P[1],(PointElement)element[eCount-1]);
	    return;
	  case 2:	// angle divider construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] = new AngleDivider(P[0],P[1],P[2],(PlaneElement)E[0],N[0]);
	    element[++eCount] = new LineElement(P[1],(PointElement)element[eCount-1]);
	    return;
	  case 3:	// foot constructions
	    if (choice == 0)
	      element[eCount] = new Foot(P[0],P[1],P[2]);
	    else
	      element[eCount] = new PlaneFoot(P[0],(PlaneElement)E[0]);
	    element[++eCount] = new LineElement(P[0],
	        (PointElement)element[eCount-1]);
	    return;
	  case 4:	// chord construction
	    element[eCount] = new Chord(P[0],P[1],(CircleElement)E[0]);
	    return;
	  case 5:	// bichord construction
	    element[eCount] = new Bichord((CircleElement)E[0],(CircleElement)E[1]);
	    return;
	  case 6:	// perpendicular constructions
	    if (choice == 0)
	      element[eCount] = new Perpendicular(P[0],P[1],screen,P[0],P[1]);
	    else if (choice == 1)
	      element[eCount] = new Perpendicular(P[0],P[1],(PlaneElement)E[0],P[0],P[1]);
	    else if (choice == 2)
	      element[eCount] = new Perpendicular(P[0],P[1],screen,P[2],P[3]);
	    else if (choice == 3)
	      element[eCount] = new Perpendicular(P[0],P[1],(PlaneElement)E[0],P[2],P[3]);
	    else
	      element[eCount] = new PlanePerpendicular(P[0],(PlaneElement)E[0],P[1],P[2]);
	    return;
	  case 7:	// cutoff constructions
	    element[eCount] = new Layoff(P[0],P[0],P[1],P[2],P[3]);
	    element[++eCount] = new LineElement(P[0],(PointElement)element[eCount-1]);
	    return;
	  case 8:	// extend constructions
	    element[eCount] = new Layoff(P[1],P[0],P[1],P[2],P[3]);
	    element[++eCount] = new LineElement(P[1],(PointElement)element[eCount-1]);
	    return;
	  case 9:	// parallel constructions
	    element[eCount] = new Layoff(P[0],P[1],P[2],P[1],P[2]);
	    element[++eCount] = new LineElement(P[0],(PointElement)element[eCount-1]);
	    return;
	  case 10:	// similar triangle (angle) constructions
	    if (choice == 0) E[0] = E[1] = screen;
	    element[eCount] = new Similar(P[0],P[1],(PlaneElement)E[0],P[2],
		P[3],P[4],(PlaneElement)E[1]);
	    element[eCount+1] = new LineElement (P[0],(PointElement)element[eCount]);
	    preexists[++eCount] = true;
	    return;
	  case 11:	// proportion constructions
	    element[eCount] = new Proportion(P[0],P[1],P[2],P[3],P[4],P[5],P[6],P[7]);
	    element[++eCount] = new LineElement(P[6],(PointElement)element[eCount-1]);
	    return;
	  case 12:	// meanProportional constructions
	    element[eCount] = new MeanProportional(P[0],P[1],P[2],P[3],P[4],P[5]);
	    element[++eCount] = new LineElement(P[4],(PointElement)element[eCount-1]);
	    return;
	}

      case 2:	// circle constructions
	switch (m) {
	  case 0:	// radius construction
	    switch (choice) {
	      case 0:
	        element[eCount] =  new CircleElement(P[0],P[1],screen);
		return;
	      case 1:
	        element[eCount] =  new CircleElement(P[0],P[1],P[2],screen);
		return;
	      case 2:
	        element[eCount] =  new CircleElement(P[0],P[1],(PlaneElement)E[0]);
		return;
	      case 3:
	        element[eCount] =  new CircleElement(P[0],P[1],P[2],(PlaneElement)E[0]);
		return;
	    }
	  case 1:	// circumcircle construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] = new Circumcircle(P[0],P[1],P[2],(PlaneElement)E[0]);
	    return;
	  case 2:	// invert in another circle
	    element[eCount] = new InvertCircle((CircleElement)E[0],(CircleElement)E[1]);
	    return;
	  case 3:	// intersection construction
	    element[eCount] = new IntersectionSS((SphereElement)E[0],(SphereElement)E[1]);
	    return;
	}

      case 3:	// polygon constructions
	switch (m) {
	  case 0:	// square construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new RegularPolygon(P[0],P[1],(PlaneElement)E[0],4);
	    return;
	  case 1:	// triangle construction
	    element[eCount] = new PolygonElement(P[0],P[1],P[2]);
	    return;
	  case 2:	// quadrilateral construction
	    element[eCount] = new PolygonElement(P[0],P[1],P[2],P[3]);
	    return;
	  case 3:	// pentagon construction
	    element[eCount] = new PolygonElement(P[0],P[1],P[2],P[3],P[4]);
	    return;
	  case 4:	// hexagon construction
	    element[eCount] = new PolygonElement(P[0],P[1],P[2],P[3],P[4],P[5]);
	    return;
	  case 5:	// equilateral triangle constructions
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new RegularPolygon(P[0],P[1],(PlaneElement)E[0],3);
	    return;
	  case 6:	// parallelogram construction
	    Layoff fourth;
	    fourth = new Layoff(P[0],P[1],P[2],P[1],P[2]);
	    element[eCount] = fourth;
	    element[++eCount] = new PolygonElement(P[0],P[1],P[2],fourth);
	    return;
	  case 7:	// regular polygon constructions
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new RegularPolygon(P[0],P[1],(PlaneElement)E[0],N[0]);
	    return;
	  case 8:	// star polygon constructions
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new RegularPolygon(P[0],P[1],(PlaneElement)E[0],N[0],N[1]);
	    return;
	  case 9:	// similar triangle constructions
	    if (choice == 0) E[0] = E[1] = screen;
	    element[eCount] = new Similar(P[0],P[1],(PlaneElement)E[0],P[2],P[3],
	        P[4],(PlaneElement)E[1]);
	    element[eCount+1] = new PolygonElement(P[0],P[1],
	        (PointElement)element[eCount]);
            preexists[++eCount] = true;
            return;
	  case 10:	// application construction
	    element[eCount] = new Application((PolygonElement)E[0],P[0],P[1],P[2]);
	    return;
	  case 11:	// octagon construction
	    element[eCount] = new PolygonElement(P[0],P[1],P[2],P[3],P[4],P[5],P[6],P[7]);
	    return;	   
	  case 12:	// face of a polyhedron
	    element[eCount] = ((PolyhedronElement)E[0]).P[N[0]-1];
	    preexists[eCount] = true;
	    return;
    	}

      case 4:	// sector constructions
	switch (m) {
	  case 0:	// sector construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new SectorElement(P[0],P[1],P[2],(PlaneElement)E[0]);
	    return;
	  case 1:	// arc construction
	    if (choice == 0) E[0] = screen;
	    element[eCount] =  new Arc(P[0],P[1],P[2],(PlaneElement)E[0]);
	    return;
     	}

      case 5:	// plane constructions
	switch (m) {
	  case 0:	// 3points construction
	    element[eCount] =  new PlaneElement(P[0],P[1],P[2]);
	    return;
	  case 1:	// plane perpendicular to line
	    element[eCount] =  new PerpendicularPL(P[0], P[1]);
	    return;
	  case 2:	// parallel construction
	    element[eCount] =  new ParallelP((PlaneElement)E[0],P[0]);
	    return;
	  case 3:	// ambient planes
	    if (choice == 0) 
	      element[eCount] = P[0].AP;
	    else
	      element[eCount] = ((CircleElement)E[0]).AP;
	    preexists[eCount] = true;
	    return;
     	}

      case 6:	// sphere constructions
	switch (m) {
	  case 0:	// radius constructions
	    if (choice == 0)
	      element[eCount] =  new SphereElement(P[0],P[0],P[1]);
	    else
	      element[eCount] =  new SphereElement(P[0],P[1],P[2]);
	    return;
     	}

      case 7:	// polyhedron constructions
        PolygonElement Pol;
	switch (m) {
	  case 0:	// tetrahedron construction
	    Pol =  new PolygonElement(P[0],P[1],P[2]);
	    element[eCount++] = Pol;
	    element[eCount] = new Pyramid(Pol,P[3]);
	    return;
	  case 1:	// parallelepiped construction
	    Layoff fourth = new Layoff(P[1],P[0],P[2],P[0],P[2]);
	    element[eCount++] = fourth;
	    Pol = new PolygonElement(P[1],P[0],P[2],fourth);
	    element[eCount++] = Pol;
	    element[eCount] =  new Prism(Pol,P[0],P[3]);
	    return;
	  case 2:	// prism construction
	    element[eCount] =  new Prism((PolygonElement)E[0],P[0],P[1]);
	    return;
	  case 3:	// pyramid construction
	    element[eCount] =  new Pyramid((PolygonElement)E[0],P[0]);
	    return;
    }	}

    return;	// should never reach here, but the compiler complains
  }


  Element constructElement (String name, String elementClass,
	String constructionMethod, String data, StringBuffer message) {
    if (lookupElement(name) != null) {
      message.append("An element with the name " + name 
		     + " has already been created.");
      return null;
    }
    int c = lookupElementClass (elementClass);
    if (c == -1) {
      message.append("Element class " + elementClass + " is not known.");
      return null;
    }
    int m = lookupConstructionMethod (c, constructionMethod);
    if (m == -1) {
      message.append("ConstructionMethod " + constructionMethod 
	+ " is not known for " + " element class " + elementClass + ".");
      return null;
    } 
    PointElement P[] = new PointElement[8];  // just for points
    Element E[] = new Element[4];	// for any other kind of elements
    int N[] = new int[3];		// just for integers
    int choice = selectDataChoice (data, constructionDataType[c][m], 
				   P, E, N, message);
    if (choice == -1) {
      message.append("Construction method " + constructionMethod 
	+ " for " + " element class " + elementClass 
	+ " with data " + data
	+ " requires different data.");
      return null;
    }
    if (element.length < eCount+2)
      extendArrays();
    createElement (c, m, choice, P, E, N);
    element[eCount].name = name;
    return element[eCount++];
  }

  void setPivot (String param) {
    StringTokenizer t = new StringTokenizer(param,",");
    String name = t.nextToken();
    Element e = lookupElement(name);
    if (e == null || !e.inClass("PointElement")) return;
    if (!t.hasMoreTokens()) {
      ((PointElement)e).AP = screen;
      screen.pivot = (PointElement)e;
      return;
    }
    name = t.nextToken();
    Element p = lookupElement(name);
    if (p == null || !p.inClass("PlaneElement")) return;
    ((PlaneElement)p).pivot = (PointElement)e;
  }

  void reset() {
    for (int i=0; i<eCount; ++i)
      element[i].reset();
  }

  void updateCoordinates(int i) {
    // update coordinates starting with element[i+1]
    for (++i; i<eCount; ++i) {
      if (!element[i].defined()) 
        element[i].reset();
      element[i].update();
  } }

  void translateCoordinates(double dx, double dy) {
    // translate space by (dx,dy,0)
    for (int i=0; i<eCount; ++i)
      if (!preexists[i])
        element[i].translate(dx,dy);
  }

  void rotateCoordinates(int c, int d) {
    // rotate space according to how pick goes around pivot in the plane
    PointElement piv = pick.AP.pivot;
    // compute old and new pick's 3D coordinates relative to the pivot
    PointElement oldP = PointElement.difference(pick,piv);
    double newx = c-piv.x;
    double newy = d-piv.y;		//(newz is irrelevant)
    // find their 2D coordinates on the plane
    PointElement S = pick.AP.S;
    PointElement T = pick.AP.T;
    double olds = PointElement.dot(oldP,S);
    double oldt = PointElement.dot(oldP,T);
    double den  = S.x * T.y - S.y * T.x;
    double news = (newx*T.y - newy*T.x)/den;
    double newt = (newy*S.x - newx*S.y)/den;
    // compute the scale&rotation factors
    den = olds*olds + oldt*oldt;
    double ac = (news*olds + newt*oldt)/den;
    double as = (newt*olds - news*oldt)/den;
    // rotate all the elements  
    for (int i=0; i<eCount; ++i)
      if (!preexists[i])
        element[i].rotate(piv,ac,as);
  }

  public void drawElements (Graphics g) {
    g.setColor(getBackground());
    Dimension d = size();
    g.fillRect(0, 0, d.width, d.height);
    for (int i=0; i<eCount; ++i) 
      element[i].drawFace(g);
    for (int i=0; i<eCount; ++i) 
      element[i].drawEdge(g);
    for (int i=0; i<eCount; ++i) 
      element[i].drawVertex(g);
    for (int i=0; i<eCount; ++i) 
      element[i].drawName(g,d);
  }

  Image offscreen;
  Dimension offscreensize;
  Graphics offgraphics;

  public void update (Graphics g) {
    Dimension d = size();
    if ((offscreen == null) || (d.width != offscreensize.width) 
		|| (d.height != offscreensize.height)) {
      offscreen = createImage(d.width, d.height);
      offscreensize = d;
      offgraphics = offscreen.getGraphics();
      offgraphics.setFont(g.getFont());
    }
    drawElements(offgraphics);
    g.drawImage(offscreen, 0, 0, null);
  }

  public void paint(Graphics g) {repaint();}

  void movePick (int c, int d) {
    if (pick == null) {			// select a nearby visible point
      picki = -1;
      double bestdist2 = Double.POSITIVE_INFINITY;
      for (int i=0; i<eCount; ++i)
	if (element[i].inClass("PointElement") && element[i].vertexColor!=null) {
	  double x = ((PointElement)element[i]).x;
	  double y = ((PointElement)element[i]).y;
	  double dist2 = (x-c)*(x-c) + (y-d)*(y-d);
          if (dist2 < 100.0 && dist2 < bestdist2) {
	    picki = i;
	    bestdist2 = dist2;
    }   } }
    if (picki == -1) return;
    pick = (PointElement) element[picki];
    // adjust c and d to be on the image so pick doesn't get lost
    int w = size().width;
    if (c < 0) c = 0;
    else if (c > w) c = w;
    int h = size().height;
    if (d < 0) d = 0;
    else if (d > h) d = h;
    if (Math.abs(c-pick.x) + Math.abs(d-pick.y) < 1.0)
      return;				// no motion
    // now actually change the slate
    if (pick.dragable) {		// drag the point
      if (pick.drag(c,d)) 
        updateCoordinates(picki);
      else return;
    } else if (pick.AP != null && pick.AP.pivot != null 
                 && pick.AP.pivot != pick) // rotate around the pivot
      rotateCoordinates(c,d);
    else {		// translate all coordinates
      double dx = c - pick.x;
      double dy = d - pick.y;
      translateCoordinates(dx,dy);
    }		
    repaint();
  }

  public boolean keyDown(Event evt, int key) {
    if (key=='r' || key=='R' || key==' ') {
      reset();				// typing r or space resets the diagram
      repaint();
      return true;
    } else return false;
  }

  public boolean mouseDown(Event evt, int c, int d) {
    // determine which ball is closest to location (c,d).
    pick = null;
    movePick (c,d);
    return true;
  } 

  public boolean mouseDrag(Event evt, int c, int d) {
    movePick (c,d);
    return true;
  }

  public boolean mouseUp(Event evt, int c, int d) {
    if (pick == null) return true;
    movePick(c,d);
    pick = null;
    return true;
} }

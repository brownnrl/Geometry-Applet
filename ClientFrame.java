/*----------------------------------------------------------------------+
|	Title:	ClientFrame.java					|
|		Java class ClientFrame extends Frame			|
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
|	Date:	May, 1996.  						|
+----------------------------------------------------------------------*/

import java.awt.*;
import java.applet.Applet;

public class ClientFrame extends Frame {

  Applet app;

  public ClientFrame (String s, Applet appVal) {
    super(s);
    app = appVal;
  }

  public boolean handleEvent (Event evt) {
    return app.handleEvent(evt);
  }

}

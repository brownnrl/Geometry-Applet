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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.Integer;
import java.lang.StringBuffer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ClientFrame extends Frame implements ActionListener, MouseListener, MouseMotionListener {

	enum EntryMode
	{ SELECT, POINT, LINE, CIRCLE, SQUARE }
/**
	 * 
	 */
	private static final long serialVersionUID = 3294763193966833804L;
	Geometry app;
	Slate slate;
	Button closeButton, resetButton;
	Button selectButton, pointButton;
	EntryMode currentMode;

  public ClientFrame (String s) {
    super(s);
  }
  
  public ClientFrame (String s, Geometry app)
  {
	  super(s);
	  this.app = app;
	  this.slate = app.slate;
	  InitializeGUI();
  }
  
  private void InitializeGUI()
  {  
	  this.currentMode = EntryMode.SELECT;
	  closeButton = new Button("Close");
	  resetButton = new Button("Reset");
	  selectButton = new Button("Select");
	  pointButton  = new Button("Add Point");
      Panel south = new Panel();
      Panel west  = new Panel(new GridLayout(8,1));
      closeButton.addActionListener(this); 
      resetButton.addActionListener(this);
      south.add(resetButton);
      south.add(closeButton);
	  selectButton.addActionListener(this);
	  pointButton.addActionListener(this);
	  west.add(selectButton);
	  west.add(pointButton);
      

      add("South",south);
      add("Center",slate);
      add("West",west);
      
      slate.addMouseMotionListener(this);
      
      slate.addMouseListener(this);
      setSize(app.baseSize.width,app.baseSize.height+50);
  }

  public void actionPerformed(ActionEvent e)
  {
		 Object target = e.getSource();
		 
		 if (target==closeButton) {
			 	remove(slate);
			 	currentMode = EntryMode.SELECT;
				app.unFloatWindow();
		 }
		 else if (target == resetButton)
		 {
			 slate.reset();
			 slate.repaint();
		 }
		 else if (target == selectButton)
		 {
			 currentMode = EntryMode.SELECT;
		 }
		 else if (target == pointButton)
		 {
			 currentMode = EntryMode.POINT;
		 }
  }

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		switch(currentMode)
		{
			case SELECT:
				break;
			case POINT:
				
				String newPointName = JOptionPane.showInputDialog("Enter a name for the point.");
				
				newPointName = newPointName.replace(";", "");
				
				StringBuffer error = new StringBuffer();
				Element N = app.parseElement(newPointName + ";point;free;"+ Integer.toString(e.getX()) + "," + Integer.toString(e.getY()), error);
				slate.repaint();
				if(N==null) JOptionPane.showMessageDialog(this, error);
				break;
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{}
	
	@Override
	public void mouseExited(MouseEvent e) 
	{}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		Element elements[] = slate.element;
		if(currentMode == EntryMode.SELECT)
		{
			for(int i = 0; i < slate.eCount; i++)
			{
				if(elements[i].hitTest(e.getX(), e.getY()))
					elements[i].setHighlight(true);
				else
					elements[i].setHighlight(false);
			}
			
			slate.repaint();
		}
		
	}

}

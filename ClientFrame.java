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
import java.lang.Integer;
import java.lang.StringBuffer;
import javax.swing.JOptionPane;

import sun.awt.VerticalBagLayout;

public class ClientFrame extends Frame implements ActionListener, MouseListener {

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
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

}

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class firstprogram extends JFrame implements ActionListener
{
	JLabel l;
	JTextField t;
	JButton b;

	firstprogram()
	{
	l=new JLabel("Enter User Name :");
	t=new JTextField(15);
	b=new JButton("Submit");
	JPanel p=new JPanel();
	Container c=getContentPane();
	c.setLayout(null);
	c.add(p);
	setSize(300,200);
	setResizable(false);
	
	java.awt.Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	java.awt.Rectangle frame=getBounds();
	this.setLocation((screen.width-frame.width)/2,(screen.height-frame.height)/2);

	p.setBounds(10,10,300,300);
	l.setBounds(25,30,200,40);
	t.setBounds(130,30,120,40);
	b.setBounds(90,90,80,30);
	p.setLayout(null);
	p.add(l);
	p.add(t);
	p.add(b);
	b.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		String s=t.getText();
		String s1= "Hello  " ;	
				JOptionPane.showMessageDialog(this,s1+ s, "", JOptionPane.WARNING_MESSAGE);
	}
}

public class first 
{
@SuppressWarnings("deprecation")
public static void main(String ar[])
{
firstprogram ob=new firstprogram();
	ob.addWindowListener(new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	});
		ob.show();
}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainForm{
	public static ArrayList<Course> courses = new ArrayList<Course>();
	Course course = new Course((String) null, (String) null, 0);
	JFrame frame = new JFrame("MainForm");
	JPanel panel = new JPanel();
	JButton btnOK = new JButton("OK");
	ButtonListener btnListener = new ButtonListener(); 

	public MainForm(ArrayList<Course> courses){
		this.courses = courses;
		panel.add(btnOK);

		panel.setLayout(new GridLayout(4,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 

		btnOK.addActionListener(btnListener);
	}

	private class ButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			try{
				JOptionPane.showMessageDialog(null, courses);
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
			}
			// frame.destroy();
		}
	}
}
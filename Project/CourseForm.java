import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;

public class CourseForm{
	public static ArrayList<Course> courses = new ArrayList<Course>();
	JFrame frame = new JFrame("CourseForm");
	JPanel panel = new JPanel();
	JButton btnOK = new JButton("OK");
	final JTextField code = new JTextField(25);
	final JTextField title = new JTextField(25);
	final JTextField credit = new JTextField(25);
	ButtonListener btnListener = new ButtonListener(); 

	public CourseForm(ArrayList<Course> courses){
		this.courses = courses;

		panel.add(code);
		panel.add(title);
		panel.add(credit);
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
				if ( findCourse(code.getText()) == -1 ) {
					Course course = new Course(code.getText(), title.getText(), Integer.parseInt(credit.getText()));
					courses.add(course);
					JOptionPane.showMessageDialog(null, "Successful");
				}
				else{
					JOptionPane.showMessageDialog(null, "Exists");
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Failed: " + e);
			}
			// frame.destroy();
		}
	}

	public static int findCourse(String c){
		Iterator itr = courses.iterator();
		String code;
		for(int i=0;itr.hasNext();++i){
			try{
				code = courses.get(i).getCode().toLowerCase();
				if (code.equals(c.toLowerCase()))
					return i;
			}
			catch(Exception e){break;}
		}
		return -1;
	}
}
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class FetchData {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JTextField field = new JTextField();
	JLabel data = new JLabel();
	public FetchData(final ArrayList<Course> courses){
		field.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){}
    		public void keyReleased(KeyEvent e){
	        	ArrayList<Course> tempCourses = new ArrayList<Course>();
	        	for (Course c : courses) {
	        		if(field.getText().equalsIgnoreCase(c.getCode())){
	        			tempCourses.add(c);
	        		}
	        	}
	        	data.setText(tempCourses.toString());
	        }
		    public void keyTyped(KeyEvent e){}
	    });
		panel.add(field);
		panel.add(data);
		data.setText(courses.toString());
		panel.setLayout(new GridLayout(2,1));
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
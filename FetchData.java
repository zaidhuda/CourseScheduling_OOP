//+---------------------------------------+
//|			FetchData                     |
//+---------------------------------------+
//|+frame:JFrame                          |
//|+panel:JPanel                          |
//|+field:JTextField                      |
//|+data:JLabel                           |
//+---------------------------------------+
//|+(courses:ArrayList<Course>):FetchData |
//|+keyPressed(e:KeyEvent):void           |
//|+keyReleased(e:KeyEvent):void          |
//|+keyTyped(e:KeyEvent):void             |
//+---------------------------------------+

import courseschedule.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchData {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JTextField field = new JTextField();
    JLabel data = new JLabel();

    public FetchData(final ArrayList<Course> courses) {
        field.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                ArrayList<Course> tempCourses = new ArrayList<Course>();
                Pattern pattern = Pattern.compile(field.getText().toLowerCase());
                for (Course c : courses) {
                    Matcher matcher = pattern.matcher(c.getCode().toLowerCase() + c.getTitle().toLowerCase());
                    if (matcher.find()) {
                        tempCourses.add(c);
                    }
                }
                data.setText(tempCourses.toString());
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        panel.add(field);
        panel.add(data);
        data.setText(courses.toString());
        panel.setLayout(new GridLayout(2, 1));
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
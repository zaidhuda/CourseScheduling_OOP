package courseschedule.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class TimePicker extends JPanel {
    private int ROW = 2;
    private int COL = 6;
    private int SIZE = 100;
    private int day = -1;
    private int time = -1;
    private int [][] slots = null;

    private boolean[][] availability;
    private boolean selectMany;
	private boolean hasConflict = false;
    private boolean obeyConflict = true;

    private SButton[] button = null;

    private ButtonListener bl = new ButtonListener();

    public TimePicker(boolean[][] availability) {
        constructor(day, time, availability, null);
    }

    public TimePicker(int day, int time, int[][] slots) {
        constructor(day, time, null, slots);
    }

    private void constructor(int day, int time, boolean[][] availability, int[][] slots){
        this.day = day;
        this.time = time;

	    selectMany = availability != null;

        if (selectMany) {
            this.availability = new boolean[ROW][COL];
            this.availability[0] = Arrays.copyOf(availability[0], COL);
            this.availability[1] = Arrays.copyOf(availability[1], COL);
        } else {
            this.slots = new int[ROW][COL];
            this.slots[0] = Arrays.copyOf(slots[0], COL);
            this.slots[1] = Arrays.copyOf(slots[1], COL);
        }

        drawTable();
    }

    private void drawTable() {

        button = new SButton[ROW * COL];
	    String[] str = {"<html><center>Monday<br>Wednesday</center></html>",
			            "<html><center>Tuesday<br>Thursday</center></html>" };

        for (int i = 0; i < ROW; ++i) {
	        JPanel p = new JPanel();
	        p.setBackground(Color.darkGray);
	        p.add(new JLabel(str[i]), BorderLayout.CENTER);
	        add(p);
            for (int j = 0; j < COL; ++j) {
                int k = (i * 6) + j;
                button[k] = new SButton();
                button[k].putClientProperty("index", k);
                button[k].setPreferredSize(new Dimension(SIZE, SIZE));
                button[k].addActionListener(bl);

	            if (selectMany){
		            setColor(button[k], availability[i][j]? 0 : 3);
	            } else {
		            setColor(button[k], slots[i][j]);
	            }
	            button[k].putClientProperty("original", false);

	            JPanel btnPane = new JPanel();
                btnPane.add(button[k]);
                add(btnPane);
            }
        }

        if (!selectMany){
            button[(day * 6) + time].putClientProperty("original", true);
            button[(day * 6) + time].setBackground(Color.green);
            button[(day * 6) + time].setEnabled(false);
        }

        setLayout(new GridLayout(ROW, COL));
    }

    private void setColor(JButton btn, int i){
        btn.setEnabled(true);
        switch (i){
            case 0:
                btn.setBackground(Color.white);
                break;
            case 1:
                btn.setBackground(Color.orange);
                break;
            case 2:
                btn.setBackground(Color.cyan);
                break;
            case 3:
                btn.setBackground(Color.black);
                break;
        }
        btn.putClientProperty("availability", i);
    }

	private class SButton extends JButton {
		public SButton(){
			setFocusPainted(false);
			setBorderPainted(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton currentButton = (JButton) e.getSource();
            final int index = (int) currentButton.getClientProperty("index");
            day = (index < 6) ? 0 : 1;
            time = index % 6;

            if (selectMany) {
                Color btnBack = currentButton.getBackground();
                if (btnBack.equals(Color.white)) {
	                if ((int) currentButton.getClientProperty("availability") == 0)
	                    setColor(currentButton, 1);
	                else
		                setColor(currentButton, 3);
	                availability[day][time] = !availability[day][time];
                } else if (btnBack.equals(Color.orange)) {
	                setColor(currentButton, 0);
	                availability[day][time] = !availability[day][time];
                } else if (obeyConflict()){
	                currentButton.setEnabled(true);
	                currentButton.setBackground(Color.white);
	                availability[day][time] = !availability[day][time];
                }
                System.out.println(Arrays.deepToString(availability));
            } else if (obeyConflict()) {
                for (JButton btn : button) {
                    if ((boolean) btn.getClientProperty("original")){
                        btn.setBackground(Color.darkGray);
                        btn.setEnabled(true);
                    } else {
                        setColor(btn, (int) btn.getClientProperty("availability"));
                    }
                }
                currentButton.setBackground(Color.green);
                currentButton.setEnabled(false);
            }
        }
    }

    private boolean obeyConflict(){
	    hasConflict = false;
        int avail = (int) button[(day * 6) + time].getClientProperty("availability");
        if (avail != 0 && !(boolean) button[(day * 6) + time].getClientProperty("original")){
            String note = null;
            switch (avail) {
                case 1:
                    note = "Venue is in use by other section. Re-Schedule the section?";
                    break;
                case 2:
                    note = "Lecturer is having another section. Re-Schedule the section?";
                    break;
                case 3:
                    note = "A Section is set here. Re-Schedule the section?";
                    break;
            }
            if (obeyConflict){
	            hasConflict = JOptionPane.showConfirmDialog(null, note, "Warning", JOptionPane.YES_OPTION) == 0;
                return hasConflict;
            }
        }
        return true;
    }

    public void obeyConflict(boolean obey){
        obeyConflict = obey;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    public boolean[][] getAvailability() {
        return availability;
    }

	public boolean hasConflict(){
		return hasConflict;
	}

    public void setSize(int size) {
        if(size>=30 && size<150){
            this.SIZE = size;
            for (JButton btn : button)
                btn.setPreferredSize(new Dimension(SIZE, SIZE));
        }
    }
}
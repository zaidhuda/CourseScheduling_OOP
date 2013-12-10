package courseschedule;

import javax.swing.*;
import java.awt.*;
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

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                int k = (i * 6) + j;
                button[k] = new SButton();
                button[k].putClientProperty("index", k);
                button[k].setPreferredSize(new Dimension(SIZE, SIZE));
                button[k].addActionListener(bl);

	            if (selectMany){
		            setColor(button[k], availability[i][j]);
	            } else {
		            setColor(button[k], slots[i][j]);
		            button[k].putClientProperty("original", false);
	            }

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

    private void setColor(JButton btn, boolean bool){
        if (bool){
            btn.putClientProperty("availability", 0);
            btn.setBackground(Color.white);
        } else {
            btn.putClientProperty("availability", 3);
            btn.setBackground(Color.black);
            btn.setEnabled(false);
        }
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
			super();
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
                availability[day][time] = !availability[day][time];
                Color btnBack = currentButton.getBackground();
                if (btnBack.equals(Color.white)) {
                    for (JButton btn : button)
                        if ((int) btn.getClientProperty("availability") != 3) {
                            currentButton.setEnabled(true);
                            currentButton.setBackground(Color.orange);
                        }
                } else if (btnBack.equals(Color.orange)) {
                    currentButton.setBackground(Color.white);
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
        int avail = (int) button[(day * 6) + time].getClientProperty("availability");
        if (avail != 0 && !(boolean) button[(day * 6) + time].getClientProperty("original")){
            String note = null;
            switch (avail) {
                case 1:
                    note = "Venue is not available. Decide later?";
                    break;
                case 2:
                    note = "Lecturer is not available. Decide later?";
                    break;
                case 3:
                    note = "Another section is already set. Replace?";
                    break;
            }
            if(obeyConflict)
                return (JOptionPane.showConfirmDialog(null, note) == 0);
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

    public void setSize(int size) {
        if(size>=30){
            this.SIZE = size;
            for (JButton btn : button)
                btn.setPreferredSize(new Dimension(SIZE, SIZE));
        }
    }
}
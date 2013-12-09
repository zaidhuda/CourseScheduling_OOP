package courseschedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class BooleanTable extends JPanel {
    private int ROW = 2;
    private int COL = 6;
    private int SIZE = 100;
    private int day = -1;
    private int time = -1;
    private int [][] slots = null;

    private boolean[][] availability;
    private boolean selectMany;
    private boolean obeyConflict = true;

    private JButton[] button = null;

    private ButtonListener bl = new ButtonListener();

    public BooleanTable(int day, int time, boolean[][] availability) {
        constructor(day, time, availability, null);
    }

    public BooleanTable(int day, int time, int[][] slots) {
        constructor(day, time, null, slots);
    }

    private void constructor(int day, int time, boolean[][] availability, int[][] slots){
        this.day = day;
        this.time = time;

        if (availability != null) {
            this.availability = new boolean[ROW][COL];
            selectMany = true;
            this.availability[0] = Arrays.copyOf(availability[0], COL);
            this.availability[1] = Arrays.copyOf(availability[1], COL);
        }
        if (slots != null) {
            this.slots = new int[ROW][COL];
            selectMany = false;
            this.slots[0] = Arrays.copyOf(slots[0], COL);
            this.slots[1] = Arrays.copyOf(slots[1], COL);
        }

        drawTable();
    }

    private void drawTable() {

        button = new JButton[ROW * COL];
        JPanel[] btnPane = new JPanel[ROW * COL];

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                int i2 = (i * 6) + j;
                btnPane[i2] = new JPanel();
                button[i2] = new JButton();
                button[i2].putClientProperty("index", i2);
                button[i2].putClientProperty("original", false);
                button[i2].setFocusPainted(false);
                button[i2].setBorderPainted(false);
                button[i2].setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (selectMany) setColor(button[i2], availability[i][j]);
                else setColor(button[i2], slots[i][j]);
                button[i2].setPreferredSize(new Dimension(SIZE, SIZE));
                button[i2].addActionListener(bl);
                btnPane[i2].add(button[i2]);
                add(btnPane[i2]);
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
            else
                return !obeyConflict;
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
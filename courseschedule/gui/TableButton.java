package courseschedule.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TableButton extends HoveringButton {

    private String[] label;                      // The Button's text.
    private double[] columnWidth;
    private Dimension dimension = new Dimension(900,40);
    private CustomFont font = new CustomFont();

    /*
    constructors
     */
    public TableButton(String[] label, double[] columnWidth) {
        this.label = label;
        this.columnWidth = columnWidth;
        setBorder(BorderFactory.createEmptyBorder());
        setFont(font.getFontAbel(15));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    /*
    paints the SquareButton
     */
    public void paintComponent(Graphics g) {
	    g.setColor(CustomColour.SilverClouds);
	    g.drawRect(0, 0, getWidth(), getHeight());
	    g.fillRect(0, 0, getWidth(), getHeight());

	    if (hovered){
		    g.setColor(CustomColour.lighterblue);
	    } else {
		    g.setColor(CustomColour.nightblue);
	    }
        // draw the label according to design in the button
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            Graphics2D g2d = (Graphics2D) g;
        	
            for(int i=0; i<label.length; i++) {
	            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g2d.drawString(label[i], (int)columnWidth[i]-(fm.stringWidth(label[i])/2), (getHeight()/2)+5);
            }
        }
    }

    public void setPreferredSize(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension getPreferredSize() {
        return dimension;
    }
}
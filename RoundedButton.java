import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoundedButton extends JComponent {

    ActionListener actionListener; 
    String label;                    
    int code;
    protected boolean hovered = false; 

    /*
    Constructors
    */
    public RoundedButton() {
        this("");
    }

    public RoundedButton(String label) {
        this(label, 0);
    }

    public RoundedButton(String label, int code) {
        this.label = label;
        this.code = code;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /*
    paints the RoundedButton
     */
    public void paintComponent(Graphics g) {
        // paint the interior of the button
        if(hovered) {
            if(code == 0)
                g.setColor(CustomColour.lighterred.brighter());
            if(code == 1)
                g.setColor(CustomColour.lighterblue.brighter());            
        }
        else {
            if(code == 0)
                g.setColor(CustomColour.lighterred);
            if(code == 1)
                g.setColor(CustomColour.lighterblue);
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        // draw the perimeter of the button
        if(hovered) {
            if(code == 0)
                g.setColor(CustomColour.lighterred.brighter());
            if(code == 1)
                g.setColor(CustomColour.lighterblue.brighter());            
        }
        else {
            if(code == 0)
                g.setColor(CustomColour.lighterred);
            if(code == 1)
                g.setColor(CustomColour.lighterblue);
        }
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        // draw the label centered in the button
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(CustomColour.silverclouds);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2d.drawString(label, (getWidth()/2)-(fm.stringWidth(label)/2), getHeight()/2 + 5);
        }
    }

    /*
    Setter methods
    */
    public void setLabel(String label) {
        this.label = label;
        invalidate();
        repaint();
    }

    /*
    Getter methods
    */
    public String getLabel() {
        return label;
    }

    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(141, 42);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                break;

            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null)
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, label));
                break;

            case MouseEvent.MOUSE_ENTERED:
                hovered = true;

                repaint();
                break;

            case MouseEvent.MOUSE_EXITED:
                if (hovered == true) {
                    hovered = false;
                    repaint();
                }
                break;
        }
        super.processMouseEvent(e);
    }
}
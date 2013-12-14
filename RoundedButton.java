import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoundedButton extends JComponent {

    ActionListener actionListener;     // Post action events to listeners
    String label;                      // The Button's text
    int code;
    private CustomColour color = new CustomColour();
    protected boolean hovered = false; // true if the button is detented.

    /**
     * Constructs a RoundedButton with no label.
     */
    public RoundedButton() {
        this("");
    }

    /**
     * Constructs a RoundedButton with the specified label.
     *
     * @param label the label of the button
     */
    public RoundedButton(String label) {
        this.label = label;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public RoundedButton(String label, int code) {
        this.label = label;
        this.code = code;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * gets the label
     *
     * @see setLabel
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label
     *
     * @see getLabel
     */
    public void setLabel(String label) {
        this.label = label;
        invalidate();
        repaint();
    }

    /**
     * paints the RoundedButton
     */
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        // paint the interior of the button
        if(hovered) {
            if(code == 0)
                g.setColor(color.getLighterRed().brighter());
            if(code == 1)
                g.setColor(color.getLighterBlue().brighter());            
        }
        else {
            if(code == 0)
                g.setColor(color.getLighterRed());
            if(code == 1)
                g.setColor(color.getLighterBlue());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        // draw the perimeter of the button
        if(hovered) {
            if(code == 0)
                g.setColor(color.getLighterRed().brighter());
            if(code == 1)
                g.setColor(color.getLighterBlue().brighter());            
        }
        else {
            if(code == 0)
                g.setColor(color.getLighterRed());
            if(code == 1)
                g.setColor(color.getLighterBlue());
        }
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        // draw the label centered in the button
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2d.drawString(label, (getWidth()/2)-(fm.stringWidth(label)/2), getHeight()/2 + 5);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(141, 42);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    /**
     * Adds the specified action listener to receive action events from this
     * button.
     *
     * @param listener the action listener
     */
    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * Removes the specified action listener so it no longer receives action
     * events from this button.
     *
     * @param listener the action listener
     */
    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    /**
     * Determine if click was inside round button.
     */
    @Override
    public boolean contains(int x, int y) {
        int mx = getSize().width / 2;
        int my = getSize().height / 2;
        return (((mx - x) * (mx - x) + (my - y) * (my - y)) <= mx * mx);
    }

    /**
     * Paints the button and distribute an action event to all listeners.
     */
    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:

                break;

            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, label));
                }
                // render myself normal again
                break;

            case MouseEvent.MOUSE_ENTERED:
                // render myself inverted....
                hovered = true;

                // Repaint might flicker a bit. To avoid this, you can use
                // double buffering (see the Gauge example).
                repaint();
                break;

            case MouseEvent.MOUSE_EXITED:
                if (hovered == true) {
                    // Cancel! Don't send action event.
                    hovered = false;

                    // Repaint might flicker a bit. To avoid this, you can use
                    // double buffering (see the Gauge example).
                    repaint();
                }
                break;
        }
        super.processMouseEvent(e);
    }
}
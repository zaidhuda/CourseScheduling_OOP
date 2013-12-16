
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SquareButton extends JComponent {

    private ActionListener actionListener;
    private String label1, label2;
    private Font[] font = new Font[2];
    private Color color;
    private CustomColour custom = new CustomColour();
    protected boolean hovered = false;

    /*
    Constructors
     */
    public SquareButton() {
        this("","");
    }

    public SquareButton(String label) {
        this(label,"");
    }

    public SquareButton(String label, String label2) {
        label1 = label;
        this.label2 = label2;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /*
    paints the SquareButton
     */
    public void paintComponent(Graphics g) {

        // paint the interior of the button
        if (hovered)
            g.setColor(getCustomColor().brighter()); 
        else
            g.setColor(getCustomColor()); 

        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

        // draw the perimeter of the button
        if (hovered)
            g.setColor(getCustomColor().brighter()); 
        else
            g.setColor(getCustomColor()); 
        
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // draw the label according to design in the button
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            g.setColor(Color.WHITE);

            Graphics2D g2d = (Graphics2D)g;

            g2d.setFont(getFontUpperLabel());
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2d.drawString(label1, getWidth()-fm.stringWidth(label1)-10, getHeight()-(getHeight()/4));

            g2d.setFont(getFontLowerLabel());
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2d.drawString(label2, getWidth()-fm.stringWidth(label2), getHeight()-(getHeight()/4)+10);
        }
    }

    /*
    Setter methods
    */
    public void setFontUpperLabel(Font font) {
        this.font[0] = font;
    }

    public void setFontLowerLabel(Font font) {
        this.font[1] = font;
    }

    public void setCustomColor(Color c) {
        color = c;
    }

    /*
    Getter methods
    */
    public Font getFontUpperLabel() {
        return font[0];
    }

    public Font getFontLowerLabel() {
        return font[1];
    }

    public Color getCustomColor() {
        return color;
    }

    public Dimension getPreferredSize() {
        return new Dimension(170, 196);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    @Override
    public boolean contains(int x, int y) {
        int mx = getSize().width / 2;
        int my = getSize().height / 2;
        return (((mx - x) * (mx - x) + (my - y) * (my - y)) <= mx * mx);
    }

    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:

                break;

            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, label1));
                }

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
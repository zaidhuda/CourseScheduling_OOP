package courseschedule.gui;

import java.awt.*;

public class SquareButton extends HoveringButton {

    private String label1, label2;                      // The Button's text.
    private Font[] font = new Font[2];
    private Color color;

    /*
    constructors
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

    public void setFontUpperLabel(Font font) {
        this.font[0] = font;
    }

    public void setFontLowerLabel(Font font) {
        this.font[1] = font;
    }

    public void setCustomColor(Color c) {
        color = c;
    }

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
}
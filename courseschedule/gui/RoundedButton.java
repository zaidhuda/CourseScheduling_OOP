package courseschedule.gui;

import java.awt.*;

public class RoundedButton extends HoveringButton {

	String label;                      // The Button's text
	Color c;

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
	}

	public RoundedButton(String label, int code) {
		this.label = label;
		c = (code == 0) ? CustomColour.lighterred : CustomColour.lighterblue;
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
		if (hovered) {
			g.setColor(c.brighter());
		} else {
			g.setColor(c);
		}
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

		// draw the label centered in the button
		Font f = getFont();
		if (f != null) {
			FontMetrics fm = getFontMetrics(getFont());
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.WHITE);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2d.drawString(label, (getWidth() / 2) - (fm.stringWidth(label) / 2), getHeight() / 2 + 5);
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(141, 42);
	}
}
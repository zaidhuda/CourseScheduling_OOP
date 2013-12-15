package courseschedule.gui;

import javax.swing.*;
import java.awt.*;

public class FieldButton extends HoveringButton {
	private CustomFont font = new CustomFont();

	private String label;

	public FieldButton(String label) {
		super();
		this.label = label;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBackground(CustomColour.silvergray);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		setAlignmentX(JTextField.CENTER_ALIGNMENT);
		setFont(font.getFontAbel(25, -0.05));
		setForeground(CustomColour.silverclouds);
	}

	public void setLabel(String label) {
		this.label = label;
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		// super.paintComponent(g);

		// paint the interior of the button
		g.setColor(CustomColour.silvergray);
		g.drawRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, getWidth(), getHeight());

		if (hovered) {
			g.setColor(CustomColour.silver);
		} else {
			g.setColor(CustomColour.silverclouds);
		}
		// draw the label centered in the button
		Font f = getFont();
		if (f != null) {
			FontMetrics fm = getFontMetrics(getFont());
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2d.drawString(label, 10, 34);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(287, 50);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}
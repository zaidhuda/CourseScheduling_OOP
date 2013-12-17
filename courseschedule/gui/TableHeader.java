package courseschedule.gui;

import javax.swing.*;
import java.awt.*;

public class TableHeader extends JPanel {

	private String[] label;
	private double[] columnWidth;
	private Dimension dimension = new Dimension(900, 40);

	/*
	Constructors
	 */
	public TableHeader(String[] label, double[] columnWidth) {
		this.label = label;
		this.columnWidth = columnWidth;
		CustomFont font = new CustomFont();
		setFont(font.getFontAbel(15));
	}

	public void paintComponent(Graphics g) {
		g.setColor(CustomColour.darkerblue);
		g.drawRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, getWidth(), getHeight());
		// draw the label according to design in the button
		g.setColor(CustomColour.silverclouds);
		Font f = getFont();
		if (f != null) {
			FontMetrics fm = getFontMetrics(getFont());
			Graphics2D g2d = (Graphics2D) g;

			for (int i = 0; i < label.length; i++) {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				g2d.drawString(label[i], (int) columnWidth[i] - (fm.stringWidth(label[i]) / 2), (getHeight() / 2) + 5);
			}
		}
	}

	public void setPreferredSize(Dimension dimension) {
		this.dimension = dimension;
	}

	/*
	Getter methods
	*/
	public Dimension getPreferredSize() {
		return dimension;
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}
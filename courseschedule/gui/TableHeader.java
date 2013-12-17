package courseschedule.gui;

import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;

public class TableHeader extends JPanel {

	private String[] label;
	private Dimension dimension = new Dimension(900, 40);
	OffsetFinder of;

	/*
	Constructors
	 */
	public TableHeader(String[] label, OffsetFinder of) {
		this.label = label;
		this.of = of;
		CustomFont font = new CustomFont();
		setFont(font.getFontAbel(15));
	}

	public void paintComponent(Graphics g) {
		int newWidth = getPreferredSize().width;
		int newHeight = getPreferredSize().height;
		g.setColor(CustomColour.DarkBlue);
		g.drawRect(0, 0, newWidth, newHeight);
		g.fillRect(0, 0, newWidth, newHeight);
		// draw the label according to design in the button
		g.setColor(CustomColour.silverclouds);
		Font f = getFont();
		if (f != null) {
			FontMetrics fm = getFontMetrics(getFont());
			Graphics2D g2d = (Graphics2D) g;

			for (int i = 0; i < label.length; i++) {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				g2d.drawString(label[i], of.getOffset(label[i], i, this), (getHeight() / 2) + 5);
			}
		}
	}

	public void setOffset(OffsetFinder of){
		this.of = of;
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
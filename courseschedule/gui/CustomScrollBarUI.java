package courseschedule.gui;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
	private int scrollBar = 0;

	public CustomScrollBarUI(int i) {
		super();
		scrollBar = i;
	}

	public JButton createZeroButton() {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0, 0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

	@Override
	public JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	public JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(CustomColour.silver);
		switch (scrollBar) {
			case 1:
				g.fillRect(0, 0, 5, (int) trackBounds.getHeight());
				break;
			case 2:
				g.fillRect(0, 0, (int) trackBounds.getWidth(), 5);
				break;
			default:
				super.paintTrack(g, c, trackBounds);
				break;
		}
	}

	@Override
	public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.setColor(CustomColour.nightblue);
		switch (scrollBar) {
			case 1:
				g.fillRect(thumbBounds.x, thumbBounds.y, 5, (int) thumbBounds.getHeight());
				break;
			case 2:
				g.fillRect(thumbBounds.x, thumbBounds.y, (int) thumbBounds.getWidth(), 5);
				break;
			default:
				super.paintTrack(g, c, thumbBounds);
				break;
		}
	}
}
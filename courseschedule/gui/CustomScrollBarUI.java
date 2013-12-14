package courseschedule.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
	private int scrollbar = 0;

	public CustomScrollBarUI(int i) {
		super();
		scrollbar = i;
	}

	public JButton createZeroButton() {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0,0);
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
        switch(scrollbar) {
        	case 1: g.fillRect(0, 0, 5, (int)trackBounds.getHeight()); break;
        	case 2: g.fillRect(0, 0, (int)trackBounds.getWidth(), 5); break;
        	default: super.paintTrack(g,c,trackBounds); break;
        }
    }

    @Override
    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(CustomColour.nightblue);
        switch(scrollbar) {
        	case 1: g.fillRect(thumbBounds.x, thumbBounds.y, 5, (int)thumbBounds.getHeight()); break;
        	case 2: g.fillRect(thumbBounds.x, thumbBounds.y, (int)thumbBounds.getWidth(), 5); break;
        	default: super.paintTrack(g,c,thumbBounds); break;
        }
    }
}
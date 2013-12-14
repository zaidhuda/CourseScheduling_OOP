import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
	private int scrollbar = 0;
	private CustomColour color = new CustomColour();

	protected CustomScrollBarUI(int i) {
		super();
		scrollbar = i;
	}

	protected JButton createZeroButton() {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0,0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(color.getSilver());
        switch(scrollbar) {
        	case 1: g.fillRect(0, 0, 5, (int)trackBounds.getHeight()); break;
        	case 2: g.fillRect(0, 0, (int)trackBounds.getWidth(), 5); break;
        	default: super.paintTrack(g,c,trackBounds); break;
        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(color.getNightBlue());
        switch(scrollbar) {
        	case 1: g.fillRect(thumbBounds.x, thumbBounds.y, 5, (int)thumbBounds.getHeight()); break;
        	case 2: g.fillRect(thumbBounds.x, thumbBounds.y, (int)thumbBounds.getWidth(), 5); break;
        	default: super.paintTrack(g,c,thumbBounds); break;
        }
    }
}
package courseschedule.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Diaz
 * Package: courseschedule.gui
 * Time: 2:26 PM
 * Date: 12/15/13.
 */
public class HoveringButton extends JComponent {
	ActionListener actionListener;
	protected boolean hovered = false;

	public HoveringButton() {
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
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
	 * Paints the button and distribute an action event to all listeners.
	 */
	@Override
	public void processMouseEvent(MouseEvent e) {
		switch (e.getID()) {
			case MouseEvent.MOUSE_PRESSED:

				break;

			case MouseEvent.MOUSE_RELEASED:
				if (actionListener != null) {
					actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
				if (hovered) {
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

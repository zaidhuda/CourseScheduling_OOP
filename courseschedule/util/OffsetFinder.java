package courseschedule.util;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Diaz
 * Package: courseschedule
 * Time: 5:06 PM
 * Date: 12/11/13.
 */
public class OffsetFinder {
	private double[] max = null;
	private int SIZE = -1;
	int theContainerSize;
	private double[] offset = null;
	private JComponent theContainer;

	public OffsetFinder(String[][] theTable, JComponent theContainer) {
		theContainerSize = theContainer.getWidth();
		if (theContainerSize == 0)
			theContainerSize = (int) theContainer.getPreferredSize().getWidth();
		this.theContainer = theContainer;
		findWidth(theTable);
	}

	public OffsetFinder(String[] theStrings, JComponent theContainer) {
		theContainerSize = theContainer.getWidth();
		if (theContainerSize == 0)
			theContainerSize = (int) theContainer.getPreferredSize().getWidth();
		String[][] theTable = new String[1][theStrings.length];
		findWidth(theTable);
	}

	public OffsetFinder(String[][] theTable, int theContainerSize) {
		this.theContainerSize = theContainerSize;
		findWidth(theTable);
	}

	public OffsetFinder(String[] theStrings, int theContainerSize) {
		this.theContainerSize = theContainerSize;
		String[][] theTable = new String[1][theStrings.length];
		findWidth(theTable);
	}

	private void findWidth(String[][] theTable) {
		max = new double[theTable[0].length];
		Arrays.fill(max, 8);
		for (String[] aTheTable : theTable) {
			for (int j = 0; j < aTheTable.length; j++) {
				int length = aTheTable[j].length();
				if (length > max[j])
					max[j] = length;
			}
		}
		SIZE = max.length;
		findOffset();
	}

	public void findOffset() {
		int total = 0;
		theContainerSize -= (theContainerSize*0.02);
		offset = new double[SIZE];
		for (double i : max)
			total += i;
		for (int j = 0; j < SIZE; j++) {
			max[j] = max[j] / total * theContainerSize;
			offset[j] = max[j] / 2 + 5;
		}
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < i; j++) {
				offset[i] += max[j];
			}
			if (offset[i] < 0)
				offset[i] = 0.0;
		}
	}

	public double[] getOffset() {
		return offset;
	}

	public int getOffset(String str, int index, JComponent component) {
		Font font = component.getFont();
		FontMetrics fm = component.getFontMetrics(font);
		return (int) offset[index] - fm.stringWidth(str) / 2;
	}
}

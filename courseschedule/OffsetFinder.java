package courseschedule;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Arrays;

/**
 * Created by Diaz
 * Package: courseschedule
 * Time: 5:06 PM
 * Date: 12/11/13.
 */
public class OffsetFinder {
	private int[] width = null;
	private int SIZE = -1;
	private double[] offset = null;
	private double theContainerSize = 0.0;

	public OffsetFinder(String[][] theTable, JComponent theContainer){
		if (theContainer != null) theContainerSize = theContainer.getWidth();
		for (int i=0;i<theTable.length;i++){
			findWidth(theTable[i]);
		}
		SIZE = width.length;
		findOffset();
	}

	public OffsetFinder(String[] theStrings, JComponent theContainer){
		if (theContainer != null) theContainerSize = theContainer.getWidth();
		findWidth(theStrings);
		SIZE = width.length;
		findOffset();
	}

	private void findWidth(String[] theTable){
		if (width == null) width = new int[theTable.length];
		for (int i=0;i<theTable.length;i++){
			int length = theTable[i].length();
			if (length > width[i]){
				width[i] = length;
			}
		}
	}

	public void findOffset(){
		int total = 0;
		double[] size = new double[SIZE];
		offset = new double[SIZE];
		theContainerSize = (double) 900;
		for (int i : width)
			total += i;
		for (int i=0;i<SIZE;i++){
			size[i] = width[i]*theContainerSize/total;
			offset[i] = size[i]/2-width[i]/2;
		}
		for (int i=0;i<SIZE;i++){
			for(int j=0;j<i;j++)
				offset[i] += size[j];
		}
	}

	public double[] getOffset(){
		return offset;
	}

	public int[] getWidth() {
		return width;
	}
}

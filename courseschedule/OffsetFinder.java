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
	private double[] offset = null;
	private double theObjectSize = 0.0;

	public OffsetFinder(String[][] theTable, JComponent theObject){
		for (int i=0;i<theTable.length;i++){
			findWidth(theTable[i], theObject);
		}
	}

	public OffsetFinder(String[] theStrings, JComponent theObject){
		findWidth(theStrings, theObject);
	}

	private void findWidth(String[] theTable, JComponent theObject){
		if (width == null) width = new int[theTable.length];
		for (int i=0;i<theTable.length;i++){
			int length = theTable[i].length();
			if (length > width[i]){
				width[i] = length;
			}
		}
	}

	public double[] getOffset(){
		int SIZE = width.length;
		int total = 0;
		double[] size = new double[SIZE];
		offset = new double[SIZE];
		theObjectSize = (double) 900;
		for (int i : width)
			total += i;
		for (int i=0;i<SIZE;i++){
			size[i] = width[i]*theObjectSize/total;
			offset[i] = size[i]/2-width[i]/2;
		}
		for (int i=0;i<SIZE;i++){
			for(int j=0;j<i;j++)
				offset[i] += size[j];
		}

		return offset;
	}

	public int[] getWidth() {
		return width;
	}
}

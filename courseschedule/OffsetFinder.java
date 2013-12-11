package courseschedule;

import javax.swing.JComponent;
import java.awt.*;
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
	private JComponent theContainer;

	public OffsetFinder(String[][] theTable, JComponent theContainer){
		this.theContainer = theContainer;
		for (String[] theStrings : theTable){
			findWidth(theStrings);
		}
		SIZE = width.length;
		findOffset();
	}

	public OffsetFinder(String[] theStrings, JComponent theContainer){
		this.theContainer = theContainer;
		findWidth(theStrings);
		SIZE = width.length;
		findOffset();
	}

	private void findWidth(String[] theTable){
		if (width == null) width = new int[theTable.length];
		Font font = new Font(Font.SERIF, Font.PLAIN, 20);
		FontMetrics fm = theContainer.getFontMetrics(font);
		for (int i=0;i<theTable.length;i++){
			int length = theTable[i].length();
			if (length > width[i]){
				width[i] = length;
			}
			if (width[i]<5) width[i] = 5;
		}
	}

	public void findOffset(){
		if (theContainer != null) theContainerSize = theContainer.getWidth();
		int total = 0;
		double[] size = new double[SIZE];
		offset = new double[SIZE];
		for (int i : width)
			total += i;
		for (int i=0;i<SIZE;i++){
			size[i] = width[i]*theContainerSize/total;
			offset[i] = size[i]/2;
		}
		for (int i=0;i<SIZE;i++){
			for(int j=0;j<i;j++)
				offset[i] += size[j];
			if (offset[i] < 0)
				offset[i] = 0.0;
		}
	}

	public double[] getOffset(){
		return offset;
	}
}

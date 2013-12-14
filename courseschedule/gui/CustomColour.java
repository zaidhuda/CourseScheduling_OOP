package courseschedule.gui;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class CustomColour {
	private int intValue;
	private ArrayList<Color> custom = new ArrayList<Color>();

	public CustomColour() {
		intValue = Integer.parseInt("e74c3c", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("3498db", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("ecf0f1", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("d3dadf", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("bdc3c7", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("2c3e50", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("2ecc71", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("1abc9c", 16);
		custom.add(new Color(intValue));

		intValue = Integer.parseInt("2980b9", 16);
		custom.add(new Color(intValue));
	}

	public Color getLighterRed() { return custom.get(0); }
	public Color getLighterBlue() { return custom.get(1); }
	public Color getSilverClouds() { return custom.get(2); }
	public Color getSilverGray() { return custom.get(3); }
	public Color getSilver() { return custom.get(4); }
	public Color getNightBlue() { return custom.get(5); }
	public Color getLighterGreen() { return custom.get(6); }
	public Color getTurqoise() { return custom.get(7); }
	public Color getDarkerBlue() { return custom.get(8); }
}
package courseschedule.gui;

import java.awt.Color;

public class CustomColour {

	public static Color lighterred = new Color(Integer.parseInt("e74c3c", 16));
	public static Color LighterRed = lighterred;

	public static Color lighterblue = new Color(Integer.parseInt("3498db", 16));
	public static Color LighterBlue = lighterblue;

	public static Color silverclouds = new Color(Integer.parseInt("ecf0f1", 16));
	public static Color SilverClouds = silverclouds;

	public static Color silvergray = new Color(Integer.parseInt("d3dadf", 16));
	public static Color SilverGray = silvergray;

	public static Color silver = new Color(Integer.parseInt("bdc3c7", 16));
	public static Color Silver = silver;

	public static Color nightblue = new Color(Integer.parseInt("2c3e50", 16));
	public static Color NightBlue = nightblue;

	public static Color lightergreen = new Color(Integer.parseInt("2ecc71", 16));
	public static Color LighterGreen = lightergreen;

	public static Color turqoise = new Color(Integer.parseInt("1abc9c", 16));
	public static Color Turqoise = turqoise;

	public static Color darkerblue = new Color(Integer.parseInt("2980b9", 16));
	public static Color DarkBlue = darkerblue;

	public static Color get(String hex) {
		return new Color(Integer.parseInt(hex, 16));
	}

	public static Color getLighterRed() {
		return lighterred;
	}

	public static Color getLighterBlue() {
		return lighterblue;
	}

	public static Color getSilverClouds() {
		return silverclouds;
	}

	public static Color getSilverGray() {
		return silvergray;
	}

	public static Color getSilver() {
		return silver;
	}

	public static Color getNightBlue() {
		return nightblue;
	}

	public static Color getLighterGreen() {
		return lightergreen;
	}

	public static Color getTurqoise() {
		return turqoise;
	}

	public static Color getDarkerBlue() {
		return darkerblue;
	}
}
package courseschedule.gui;

import java.awt.*;
import java.awt.font.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CustomFont {
	private Font font = new Font("serif", Font.PLAIN, 24);

	public Font getFontAbel(int size) {
		return getFontAbel(size, Font.PLAIN, 0);
	}

	public Font getFontAbel(int size, int style) {
		return getFontAbel(size, style, 0);
	}

	public Font getFontAbel(int size, double letterSpacing) {
		return getFontAbel(size, Font.PLAIN, letterSpacing);
	}

	public Font getFontAbel(int size, int style, double letterSpacing) {
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, letterSpacing);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\Abel-Regular.ttf"));
			font = font.deriveFont(style, size);
			font = font.deriveFont(attributes);
		}
		catch(IOException ex) {
			try {
				URL url = new URL("https://dl.dropboxusercontent.com/u/17401106/Abel-Regular.ttf");
				font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
				font = font.deriveFont(style, size);
				font = font.deriveFont(attributes);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(FontFormatException ff) {
				ff.printStackTrace();
			}

			ex.printStackTrace();
		}
		catch(FontFormatException ffe) {
			ffe.printStackTrace();
		}
		//font = new Font("Lucida Sans Unicode", style, size);

		return font;
	}

	public Font getFontSegoe(int size) {
		return getFontSegoe(size, Font.PLAIN, 0);
	}

	public Font getFontSegoe(int size, int style) {
		return getFontSegoe(size, style, 0);
	}

	public Font getFontSegoe(int size, double letterSpacing) {
		return getFontSegoe(size, Font.PLAIN, letterSpacing);
	}

	public Font getFontSegoe(int size, int style, double letterSpacing) {
		//Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		//attributes.put(TextAttribute.TRACKING, letterSpacing);
		//
		//try {
		//	font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\segoeuil_3.ttf"));
		//	font = font.deriveFont(style, size);
		//	font = font.deriveFont(attributes);
		//}
		//catch(IOException ex) {
		//	try {
		//		URL url = new URL("https://dl.dropboxusercontent.com/u/17401106/segoeuil_3.ttf");
		//		font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		//		font = font.deriveFont(style, size);
		//		font = font.deriveFont(attributes);
		//	}
		//	catch(IOException e) {
		//		e.printStackTrace();
		//	}
		//	catch(FontFormatException ff) {
		//		ff.printStackTrace();
		//	}
		//
		//	ex.printStackTrace();
		//}
		//catch(FontFormatException ffe) {
		//	ffe.printStackTrace();
		//}
		font = new Font("Segoe", style, size);

		return font;
	}

	public Font getFontPTSans(int size) {
		return getFontPTSans(size, Font.PLAIN, 0);
	}

	public Font getFontPTSans(int size, int style) {
		return getFontPTSans(size, style, 0);
	}

	public Font getFontPTSans(int size, double letterSpacing) {
		return getFontPTSans(size, Font.PLAIN, letterSpacing);
	}

	public Font getFontPTSans(int size, int style, double letterSpacing) {
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, letterSpacing);

		try {
			if(style == Font.BOLD) {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\PT_SansBold.ttf"));
				font = font.deriveFont(Font.PLAIN, size);
			}
			else {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\PT_SansRegular.ttf"));
				font = font.deriveFont(style, size);
			}
			font = font.deriveFont(attributes);
		}
		catch(IOException ex) {
			try {
				if(style == Font.BOLD) {
					URL url = new URL("https://dl.dropboxusercontent.com/u/17401106/PT_SansBold.ttf");
					font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
					font = font.deriveFont(Font.PLAIN, size);
				}
				else {
					URL url = new URL("https://dl.dropboxusercontent.com/u/17401106/PT_SansRegular.ttf");
					font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
					font = font.deriveFont(style, size);
				}
				font = font.deriveFont(attributes);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(FontFormatException ff) {
				ff.printStackTrace();
			}

			ex.printStackTrace();
		}
		catch(FontFormatException ffe) {
			ffe.printStackTrace();
		}
		//font = new Font("Verdana", style, size);

		return font;
	}
}
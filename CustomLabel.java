import java.awt.*;
import javax.swing.*;

public class CustomLabel extends JLabel{
	private CustomFont font = new CustomFont();
	private CustomColour color = new CustomColour();
	private String label;
    private Dimension dimension = new Dimension(287,20);

	public CustomLabel(String label) {
		// super(label); 
		this.label = label;

		setForeground(color.getSilver());
		setAlignmentX(CENTER_ALIGNMENT);
		setFont(font.getFontAbel(20,-0.05));
	}

	@Override
	public void paint(Graphics g) {
        FontMetrics fm = getFontMetrics(getFont());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(label, 0, getHeight()/2 + 5);
	}

    @Override
    public void setPreferredSize(Dimension dimension) {
        this.dimension = dimension;
    }

	@Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
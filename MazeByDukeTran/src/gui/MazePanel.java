package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5Panel  {
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	//private static final Color greenWM = Color.decode("#115740");
	static final int greenWM = 1136448;
	//private static final Color goldWM = Color.decode("#916f41");
	static final int goldWM = 9531201;
	static final int yellowWM = 16777113;
	
	static final int WHITE = 16777215;
	static final int LIGHT_GRAY = 13421772;
	static final int GRAY = 10066329;
	static final int RED = 16711680;
	static final int YELLOW = 16776960;
	
	private int currentColor;
	private Font currentFont;
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				
			}
		}
		return graphics;
	}
	
	public double[] getPreferredSize(CompassRose compass, int size) {
		Dimension dim = new Dimension();
        /* original code
        int min = Math.min(dim.width, dim.height);
        */
        int min = size; // simply use given size
        dim.setSize(min, min);
        double[] dimensions = {dim.getWidth(), dim.getHeight()};
        return dimensions;
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOperational() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(int rgb) {
		currentColor = rgb;
		graphics.setColor(new Color(rgb));
	}

	@Override
	public int getColor() {
		return currentColor;
	}
	
	public void setFont(String font) {
		currentFont = Font.decode(font);
	}

	public Font getFont() {
		return currentFont;
	}

	@Override
	public int getWallColor(int distance, int cc, int extensionX) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addBackground(float percentToExit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);		
	}

	@Override
	public void addFilledOval(int x, int y, int width, int height) {
        graphics.fillOval(x, y, width, height);		
	}

	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void addMarker(float x, float y, String str) {
		//GlyphVector gv = markerFont.createGlyphVector(g2.getFontRenderContext(), str);
        GlyphVector gv = getFont().createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
	}

	@Override
	public void setRenderingHint(RenderingHints hintKey, RenderingHints hintValue) {
		java.awt.RenderingHints.Key convertedKey;
		Object convertedValue = null;
		
		switch (hintKey) {
		case KEY_ANTIALIASING:
			convertedKey = java.awt.RenderingHints.KEY_ANTIALIASING;
			break;
		case KEY_INTERPOLATION:
			convertedKey = java.awt.RenderingHints.KEY_INTERPOLATION;
			break;
		case KEY_RENDERING:
			convertedKey = java.awt.RenderingHints.KEY_RENDERING;
			break;
		default:
			convertedKey = null;
			break;
		}
		
		switch (hintValue) {
		case VALUE_ANTIALIAS_ON:
			convertedValue = java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
			break;
		case VALUE_INTERPOLATION_BILINEAR:
			convertedValue = java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
			break;
		case VALUE_RENDER_QUALITY:
			convertedValue = java.awt.RenderingHints.VALUE_RENDER_QUALITY;
			break;
		default:
			convertedValue = null;
			break;
		}
		
		graphics.setRenderingHint(convertedKey, convertedValue);
	}
}

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
	
	private int viewWidth;
	private int viewHeight;
	
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
	
	/**
	 * Method to draw the buffer image on an inputed graphics object.
	 */
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
	
	/**
	 * Provides the preferred dimensions for the CompassRose
	 * @param size preferred for the Compass
	 * @return an array of doubles containing the preferred dimensions
	 */
	public double[] getPreferredSize(int size) {
		Dimension dim = new Dimension();
        /* original code
        int min = Math.min(dim.width, dim.height);
        */
        int min = size; // simply use given size
        dim.setSize(min, min);
        double[] dimensions = {dim.getWidth(), dim.getHeight()};
        return dimensions;
	}

	/**
	 * Commits all accumulated drawings to the UI.
	 * Substitute for MazePanel.update method. 
	 */
	@Override
	public void commit() {
		update();
	}

	/**
	 * Tells if instance is able to draw. This ability depends on the
	 * context, for instance, in a testing environment, drawing
	 * may be not possible and not desired.
	 * Substitute for code that checks if graphics object for drawing is not null.
	 * @return true if drawing is possible, false if not.
	 */
	@Override
	public boolean isOperational() {
		if (null == getBufferGraphics()) {
            System.out.println("Can't get graphics object to draw on, skipping draw operation") ;
            return false;
        }
		return true;
	}

	/**
	 * Sets the color for future drawing requests. The color setting
	 * will remain in effect till this method is called again and
	 * with a different color.
	 * Substitute for Graphics.setColor method.
	 * @param rgb gives the red green and blue encoded value of the color
	 */
	@Override
	public void setColor(int rgb) {
		currentColor = rgb;
		graphics.setColor(new Color(rgb));
	}

	/**
     * Returns the RGB value for the current color setting. 
     * @return integer RGB value
     */
	@Override
	public int getColor() {
		return currentColor;
	}
	
	/**
	 * Sets the current font for the graphics
	 * @param font name
	 */
	public void setFont(String font) {
		currentFont = Font.decode(font);
	}

	/**
	 * Provides the current font of the graphics
	 */
	public Font getFont() {
		return currentFont;
	}

	/**
     * Determines the color for a wall.
     * Supports color determination for the Wall.initColor method.
     * @param distance is the distance to the exit
     * @param cc is an obscure parameter used in Wall for color determination, just passed in here
     * @param extensionX is the wall's length and direction (sign), horizontal dimension
     * @return the rgb value for the color of the wall
     */
	@Override
	public int getWallColor(int distance, int cc, int extensionX) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Sets the view dimensions for the FirstPersonView
	 */
	public void setViewDimensions(int width, int height) {
		viewWidth = width;
		viewHeight = height;
	}

	/**
	 * Draws two solid rectangles to provide a background.
	 * Note that this also erases any previous drawings.
	 * The color setting adjusts to the distance to the exit to 
	 * provide an additional clue for the user.
	 * Colors transition from black to gold and from grey to green.
	 * Substitute for FirstPersonView.drawBackground method.
	 * @param percentToExit gives the distance to exit
	 */
	@Override
	public void addBackground(float percentToExit) {
		// black rectangle in upper half of screen
		// graphics.setColor(Color.black);
		// dynamic color setting: 
		setColor(getBackgroundColor(percentToExit, true));
		addFilledRectangle(0, 0, viewWidth, viewHeight/2);
		// grey rectangle in lower half of screen
		// graphics.setColor(Color.darkGray);
		// dynamic color setting: 
		setColor(getBackgroundColor(percentToExit, false));
		addFilledRectangle(0, viewHeight/2, viewWidth, viewHeight/2);
	}
	
	/**
	 * Determine the background color for the top and bottom
	 * rectangle as a blend between starting color settings
	 * of black and grey towards gold and green as final
	 * color settings close to the exit
	 * @param percentToExit
	 * @param top is true for the top triangle, false for the bottom
	 * @return the color to use for the background rectangle
	 */
	private int getBackgroundColor(float percentToExit, boolean top) {
		return top ? blend(MazePanel.yellowWM, MazePanel.goldWM, percentToExit) : 
			blend(MazePanel.LIGHT_GRAY, MazePanel.greenWM, percentToExit);
	}
	/**
	 * Calculates the weighted average of the two given colors
	 * @param c0 the one color
	 * @param c1 the other color
	 * @param weight0 of c0
	 * @return blend of colors c0 and c1 as weighted average
	 */
	private int blend(int c0, int c1, double weight0) {
		if (weight0 < 0.1)
			return c1;
		if (weight0 > 0.95)
			return c0;
		String hex0 = String.format("%06X", c0);
		String hex1 = String.format("%06X", c1);

		int r = (int) (weight0 * ((int)Long.parseLong(hex0.substring(0, 2), 16)) +
    		(1-weight0) * ((int)Long.parseLong(hex1.substring(0, 2), 16)));
	    int g = (int) (weight0 * ((int)Long.parseLong(hex0.substring(2, 4), 16)) + 
	    	(1-weight0) * ((int)Long.parseLong(hex1.substring(2, 4), 16)));
	    int b = (int) (weight0 * ((int)Long.parseLong(hex0.substring(4, 6), 16)) + 
	    	(1-weight0) * ((int)Long.parseLong(hex1.substring(4, 6), 16)));

	    String newHex = String.format("%02X%02X%02X", r, g, b);  
	    return Integer.parseInt(newHex,16);
	  }

	 /**
     * Adds a filled rectangle. 
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the 
     * x-axis and the height for the y-axis.
     * Substitute for Graphics.fillRect() method
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the rectangle
     * @param height is the height of the rectangle
     */
	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}

	/**
     * Adds a filled polygon. 
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have 
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.fillPolygon() method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}

	/**
     * Adds a polygon.
     * The polygon is not filled. 
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have 
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.drawPolygon method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	/**
     * Adds a line. 
     * A line is described by {@code (x,y)} coordinates for its 
     * starting point and its end point. 
     * Substitute for Graphics.drawLine method
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX is the x-coordinate of the end point
     * @param endY is the y-coordinate of the end point
     */
	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);		
	}

	/**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the 
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle.
     * Substitute for Graphics.fillOval method  
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the oval
     * @param height is the height of the oval
     */
	@Override
	public void addFilledOval(int x, int y, int width, int height) {
        graphics.fillOval(x, y, width, height);		
	}

	/**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees, 
     * using the current color. Angles are interpreted such that 0 degrees 
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise 
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is 
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide 
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of 
     * the bounding rectangle such that 45 degrees always falls on the 
     * line from the center of the ellipse to the upper right corner of 
     * the bounding rectangle. As a result, if the bounding rectangle is 
     * noticeably longer in one axis than the other, the angles to the start 
     * and end of the arc segment will be skewed farther along the longer 
     * axis of the bounds.
     * Substitute for Graphics.drawArc method 
     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn.
     * @param height the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
     * Adds a string at the given position.
     * Substitute for CompassRose.drawMarker method 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param str the string
     */
	@Override
	public void addMarker(float x, float y, String str) {
		//GlyphVector gv = markerFont.createGlyphVector(g2.getFontRenderContext(), str);
        GlyphVector gv = getFont().createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
	}

	 /**
     * Sets the value of a single preference for the rendering algorithms. 
     * Hint categories include controls for rendering quality
     * and overall time/quality trade-off in the rendering process.
     * Refer to the awt RenderingHints class for definitions of some common keys and values.
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified hint category.
     */
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

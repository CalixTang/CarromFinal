package main;
import java.io.Serializable;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**Represents a circular piece. It is an interactive, moving piece. 
 * 
 * @author Calix
 *
 */
public abstract class Piece implements Serializable {
	protected double initialX;
	protected double initialY;
	protected double x;
	protected double y;
	protected double velX;
	protected double velY;
	protected double radius;
	protected int R, G, B;
	protected double friction;
	
	private static final long serialVersionUID = 4L;
	
	/**An epsilon representing the threshold for motion to be considered not motion.
	 * 
	 */
	public static final double NEGLIGIBLE_VEL = 1.5;
	/**An epsilon used for double comparisons.
	 * 
	 */
	public static final double EPSILON = 1E-3;
	/**Instantiates a Piece.
	 * 
	 * @param x initial x pos
	 * @param y initial y pos
	 * @param radius radius
	 * @param friction friction value to be used
	 */
	public Piece(double x, double y, double radius,double friction) {
		this.x = x;
		this.y = y;
		this.initialX = x;
		this.initialY = y;
		this.radius = radius;
		this.velX = 0;
		this.velY = 0;
		this.R = 0;
		this.G = 0;
		this.B = 0;
		this.friction = friction;
	}
	
	/**Instantiates a piece with color.
	 * 
	 * @param x initial x pos
	 * @param y initial y pos
	 * @param radius radius
	 * @param friction friction value to be used
	 * @param R R value in RGB [0,255] inclusive
	 * @param G G value in RGB [0,255] inclusive
	 * @param B B value in RGB [0,255] inclusive
	 */
	public Piece(double x, double y, double radius,double friction, int R, int G, int B) {
		this.x = x;
		this.y = y;
		this.initialX = x;
		this.initialY = y;
		this.radius = Math.abs(radius);
		this.velX = 0;
		this.velY = 0;
		this.R = R;
		this.G = G;
		this.B = B;
		this.friction = friction;
	}
	
	/**Sets the color of the Piece.
	 * 
	 * @param R red value
	 * @param G green value
	 * @param B blue value
	 */
	public void setColor(int R, int G, int B) {
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public double getVel() {
		return Math.sqrt(Math.pow(velY, 2)+Math.pow(velX, 2));
	}
	/**Mass times velocity.
	 * 
	 * @return Momentum of the piece
	 */
	public double getMomentum() {
		return Math.sqrt(Math.pow(velY, 2)+Math.pow(velX, 2)) * Math.pow(radius, 1);
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getVelX() {	
		return this.velX;
	}
	public double getVelY() {
		return this.velY;
	}
	public double getRadius() {
		return this.radius;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public double getInitialX() {
		return initialX;
	}
	public double getInitialY() {
		return initialY;
	}
	public void setInitialX(double x) {
		this.initialX = x;
	}
	public void setInitialY(double y) {
		this.initialY = y;
	}
	/**Sets the designated location of the piece in case it must be 'respawned'
	 * 
	 * @param x x coordinate 
	 * @param y y coodrinate
	 */
	public void setInitLoc(double x, double y) {
		setInitialX(x);
		setInitialY(y);
	}
	/** Sets location of the piece in given boundaries. If outside, it will move to the closest valid point (inside the boundaries).
	 * 
	 * @param x the new x location
	 * @param y the new y location
	 * @param minX the left boundary
	 * @param minY the top boundary
	 * @param maxX the right boundary
	 * @param maxY the bottom boundary
	 */
	public void setLoc(double x, double y, double minX, double minY, double maxX, double maxY) {
		if(x-radius < minX) {
			x = minX + radius;
		}else if(x+radius > maxX) {
			x = maxX - radius;
		}else {
			this.x = x;
		}
		if(y-radius < minY) {
			y = minY + radius;
		}else if(y+radius > maxY) {
			y = maxY - radius;
		}else {
			this.y = y;
		}
	}
	/**Set the location of this piece without restrictions.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void setLoc(double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	/**Tells if the piece is in motion or not
	 * 
	 * @return a boolean representing whether the piece is in motion or not
	 */
	public boolean isMoving() {
		return (Math.abs(velX)>0 || Math.abs(velY)>0);
	}
	
	/**Enacts motion in the X dimension.
	 * 
	 * @param minX The minimum x boundary
	 * @param maxX The maximum x boundary
	 */
	public void moveX(double minX, double maxX) {
		x+=velX;
		if(Math.sqrt(Math.pow(velX, 2)+Math.pow(velY, 2))<NEGLIGIBLE_VEL) {
			velX = 0;
		}else {
			velX *= friction;
		}
		if(this.x-this.radius < minX) {
			x = this.radius + minX;
			velX*=-friction;
		}else if(this.x+this.radius > maxX) {
			x = maxX - this.radius;
			velX*=-friction;
		}
	}
	
	/**Enacts motion in the Y dimension.
	 * 
	 * @param minY The minimum y boundary
	 * @param maxY The maximum y boundary
	 */
	public void moveY(double minY, double maxY) {
		y+=velY;
		if(Math.sqrt(Math.pow(velX, 2)+Math.pow(velY, 2))<NEGLIGIBLE_VEL) {
			velY = 0;
		}else {
			velY *= friction;
		}
		if(this.y-this.radius < minY) {
			y = minY + this.radius;
			velY*=-friction;
		}else if(this.y+this.radius > maxY) {
			y = maxY - this.radius;
			velY*=-friction;
		}
		
	}
	
	
	/**Enacts motion in general
	 * 
	 * @param minX The minimum x boundary
	 * @param minY The maximum x boundary
	 * @param maxX The minimum y boundary
	 * @param maxY The maximum y boundary
	 */
	public void move(double minX, double minY, double maxX, double maxY) {
		moveX(minX,maxX);
		moveY(minY,maxY);
	}
	
	/**Draws the piece on the PApplet
	 * 
	 * @param p the PApplet window to draw on
	 */
	public void draw(PApplet p) {
		p.pushStyle();
		p.fill(R, G, B);
		p.ellipse((float)x, (float)y, (float)radius * 2, (float)radius * 2);
		p.popStyle();
	}
	/**Draws the piece on the PApplet with an image
	 * 
	 * @param p the PApplet window to draw on
	 * @param i the image that will be drawn for the Piece.
	 */
	public void draw(PApplet p, PImage i) {
		p.pushStyle();
		p.fill(R, G, B);
		p.imageMode(PConstants.CENTER); 
		p.image(i, (float)x, (float)y, (float)radius * 2, (float)radius * 2);
		p.popStyle();
	}
	
	/**This method will determine if two pieces are in contact with one another.
	 * 
	 * @param that The piece to check collision with
	 * @return a boolean representing whether the two pieces are colliding or not.
	 */
	public boolean isColliding(Piece that) {
		return Math.sqrt(Math.pow(this.x-that.x, 2)+Math.pow(this.y-that.y, 2)) < this.radius + that.radius;
	}
	
	/**This method will make one piece collide with many others
	 * 
	 * @param others the ArrayList of Pieces that this piece should collide with
	 * @param minX left boundary
	 * @param minY top boundary
	 * @param maxX right boundary
	 * @param maxY bottom boundary
	 */
	public void collide(ArrayList<Piece> others,double minX, double minY, double maxX, double maxY) {
		for(Piece that : others) {
			this.collide(that, minX, minY, maxX, maxY);
		}
	}
	
	/**Makes one piece collide with another within a certain set of bounds.
	 * 
	 * @param that the other piece
	 * @param minX left boundary
	 * @param minY top boundary
	 * @param maxX right boundary
	 * @param maxY bottom boundary
	 */
	public void collide(Piece that, double minX, double minY, double maxX, double maxY) {
		if(this==that) {//literally equality checking
			return;
		}
		if(this.isColliding(that) && (this.isMoving() || that.isMoving())) {
			if(this.getMomentum()>=that.getMomentum()) {
				//this.unCollide(that);
				double thisMass = this.radius;
				double thatMass = that.radius;
				
				double dX = this.x - that.x;
				double dY = this.y - that.y;
				double dXYsq = Math.pow(dX, 2)+Math.pow(dY, 2);
			
				double dvX = this.velX - that.velX;
				double dvY = this.velY - that.velY;
				//double dV = Math.pow(dvX, 2)+Math.pow(dvY, 2);
				
				
				that.velX = (dX*dvX+dY*dvY)/dXYsq*dX*thisMass/thatMass;
				that.velY = (dX*dvX+dY*dvY)/dXYsq*dY*thisMass/thatMass;
				this.velX = (dvX-((dX*dvX+dY*dvY)/dXYsq*dX))*thatMass/thisMass;
				this.velY = (dvY-((dX*dvX+dY*dvY)/dXYsq*dY))*thatMass/thisMass;
				
				that.move(minX, minY, maxX, maxY);
				this.move(minX, minY, maxX, maxY);
				this.unCollide(that);
				
			}else {
				that.collide(this, minX, minY, maxX, maxY);
			}
		}else {
			this.unCollide(that);
		}
	}
	
	/**Unclips two pieces in a given boundary
	 * 
	 * @param that the other piece to unclip this piece from
	 */
	public void unCollide(Piece that) {
		if(this==that) {
			return;
		}
		if(this.isColliding(that)) {
			
			if(Math.abs(this.x-that.x)<EPSILON && Math.abs(this.y-that.y)<EPSILON) {
				if(this.isMoving() || that.isMoving()) {
					while(this.isColliding(that)) {
						this.x-=this.velX;
						this.y-=this.velY;
						that.x-=that.velX;
						that.y-=that.velY;
					}
				}else {
					this.x+=2*this.radius;
					this.y+=2*this.radius;
					this.unCollide(that);
				}
			}else {
				
				double dX = that.x - this.x;
				double dY = that.y - this.y;
				double dXY = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
				double realD = this.radius + that.radius;
				double realDX = dX * realD / dXY;
				double realDY = dY * realD / dXY;
				this.x -= (1.01)*(realDX - dX);
				this.y -= (1.01)*(realDY - dY);
				/*
				double midX = (this.x+that.x)/2;
				double midY = (this.y+that.y)/2;
				double thisDist = Math.sqrt(Math.pow(this.x-midX, 2)+Math.pow(this.y-midY, 2));
 				double thatDist = Math.sqrt(Math.pow(that.x-midX, 2)+Math.pow(that.y-midY, 2));
				this.setLoc(this.x+(this.radius/thisDist*(this.x-midX)), this.y+(this.radius/thisDist*(this.y-midY)));
				this.setLoc(that.x+(that.radius/thisDist*(that.x-midX)), that.y+(that.radius/thisDist*(that.y-midY)));
				*/
			}

		}	
	}
	
	/**Gives the score of a piece at where it is, given the bounds of the board and radius of corner holes.
	 * 
	 * @param minX left boundary
	 * @param minY top boundary
	 * @param maxX right boundary
	 * @param maxY top boundary
	 * @param radius radius of the hole in the corner
	 * @return The score that the piece should net at its current XY location in those bounds
	 */
	public abstract int score(double minX, double minY, double maxX, double maxY, double radius);
}

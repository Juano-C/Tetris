package gameTetris;

import java.awt.Color;
import java.awt.Graphics;

public class Shape
{
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int BLOCK_SIZE = 30;
	
	private int x=4, y=0;
	private int normal = 600;
	private int fast = 50;
	private double delayTimeForMovement = normal;
	private long beginTime; 
	
	private int deltaX = 0;
	private boolean collision = false;
	
	private int [][] coords;
	private Board board;
	private Color color;
	
	public Shape(int[][] coords, Board board, Color color)
	{
        this.coords = coords;
        this.board = board;
        this.color = color;
        deltaX = 0;
        x = 4;
        y = 0;
//        delay = normal;
//        time = 0;
//        lastTime = System.currentTimeMillis();
//        reference = new int[coords.length][coords[0].length];
//
//        System.arraycopy(coords, 0, reference, 0, coords.length);
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void reset()
	{
		this.x = 4;
		this.y = 0;
		collision = false;
	}
	
	public void update()
    {

    	if(collision)
    	{
    		//fill the color for board
    		for(int row = 0; row < coords.length; row++) {
    			for(int col = 0; col < coords[0].length; col++) {
    				if(coords[row][col] != 0)
    				{
    					board.getBoard() [y + row][x + col] = color;
    				}
    			}
    		}
    		board.setCurrentShape();
    		return;
    	}
    		
    	
    	//check moving horizontal
    	if(! (x + deltaX + coords[0].length > 10) && !(x + deltaX < 0))
    	{
    		x+=deltaX;
    	}

		deltaX=0;
		
    	if(System.currentTimeMillis() - beginTime > delayTimeForMovement)
    	{
    		if(! (y + 1 + coords.length > BOARD_HEIGHT))
    		{
    			y++;
    		}
    		else
    		{
    			collision = true;
    		}

    		beginTime = System.currentTimeMillis();
    	}
        
    }
	
	public void render(Graphics g)
	{
		//draw Shape
				for(int row = 0; row<coords.length; row++){
					for(int col = 0; col<coords[0].length; col++)
					{
						if(coords[row][col] != 0) {
							g.setColor(Color.red);
							g.fillRect(col*BLOCK_SIZE + x*BLOCK_SIZE, (row-1)*BLOCK_SIZE + y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
						}
					}
				}
	}
	
	public void speedUp()
	{
		delayTimeForMovement = fast;
	}
	
	public void speedDown()
	{
		delayTimeForMovement = normal;
	}
	
	public void moveRight()
	{
		deltaX = 1;
	}
	
	public void moveLeft()
	{
		deltaX = -1;
	}
}

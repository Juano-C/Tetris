package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class Shape
{
    private Color color;
    private int x, y;
    private long time, lastTime;
    private int normal = 500, fast = 60;
    private int difficultNormal = 500, difficultFast = 400,  difficultUltraFast = 300, difficultMegaFast = 200, difficultInsane = 100;
    private int insta = 10;
    private int delay;
    private int[][] coords;
    private int[][] reference;
    private int deltaX;
    private Board board;
    
    private boolean collision = false, moveX = false;
    
    private int timePassedFromCollision = -1;
    private int filasEliminadas =0;
    private static int difficultMode = 0;

    public Shape(int[][] coords, Board board, Color color)
    {
        this.coords = coords;
        this.board = board;
        this.color = color;
        deltaX = 0;
        x = 4;
        y = 0;
        modifyDifficult();
        time = 0;
        lastTime = System.currentTimeMillis();
        reference = new int[coords.length][coords[0].length];

        System.arraycopy(coords, 0, reference, 0, coords.length);
    }

    long deltaTime;

    private void modifyDifficult()
    {
    	if(refreshSpeed() < 10)
    	{
    		delay = difficultNormal;
    		difficultMode = 0;
    	}
    	
    	else if(refreshSpeed() >= 10 && refreshSpeed() < 20)
    	{
    		delay = difficultFast;
    		difficultMode = 1;
    	}
    	
    	else if(refreshSpeed() >= 20 && refreshSpeed() < 30)
    	{
    		delay = difficultUltraFast;
    		difficultMode= 2;
    	}
    	
    	else if(refreshSpeed() >= 30 && refreshSpeed() < 40)
    	{
    		delay = difficultMegaFast;
    		difficultMode= 3;
    	}
    	
    	else if(refreshSpeed() >= 40)
    	{
    		delay = difficultInsane;
    		difficultMode= 4;
    	}
    }
    
    public static int DifficultMode()
    {
    	return difficultMode;
    }
    
    private int refreshSpeed()
    {
    	return Board.getScore();
    }
    
    public void update()
    {
        moveX = true;

        deltaTime = System.currentTimeMillis() - lastTime;
        time += deltaTime;
        lastTime = System.currentTimeMillis();

        if (collision && timePassedFromCollision > 50)
        {
            for (int row = 0; row < coords.length; row++)
            {
                for (int col = 0; col < coords[0].length; col++)
                {
                    if (coords[row][col] != 0)
                        board.getBoard()[y + row][x + col] = color;
                }
            }
            
            checkLineToScore();

            board.setCurrentShape();
            timePassedFromCollision = -1;
        }

        // check moving horizontal
        if (!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0))
        {
            for (int row = 0; row < coords.length; row++)
            {
                for (int col = 0; col < coords[row].length; col++)
                {
                    if (coords[row][col] != 0)
                        if (board.getBoard()[y + row][x + deltaX + col] != null)
                            moveX = false;
                }
            }
            if (moveX)
                x += deltaX;
        }

        // Check position + height(number of row) of shape
        if (timePassedFromCollision == -1)
        {
            if (!(y + 1 + coords.length > 20))
            {
                for (int row = 0; row < coords.length; row++)
                {
                    for (int col = 0; col < coords[row].length; col++)
                    {
                        if (coords[row][col] != 0)
                            if (board.getBoard()[y + 1 + row][x + col] != null)
                                collision();
                    }
                }
                if (time > delay)
                {
                    y++;
                    time = 0;
                }
            }
            else
                collision();
        } 
        
        else
            timePassedFromCollision += deltaTime;

        deltaX = 0;
    }

    // check lines for add score
    private void checkLineToScore()
    {
        int filasEliminadas = checkLine();
        
        if (filasEliminadas == 0)
        	board.addScore();
        
        if (filasEliminadas == 1)
        {
        	board.addScore();
        	board.addScore();
        }
        
        else if (filasEliminadas == 2)
        {
        	board.addScore();
        	board.addScore();
        	board.addScore();
        }
        
        else if (filasEliminadas == 3)
        {
        	board.addScore();
        	board.addScore();
        	board.addScore();
        	board.addScore();
        }
        
        else if (filasEliminadas == 4)
        {
        	board.addScore();
        	board.addScore();
        	board.addScore();
        	board.addScore();
        	board.addScore();
        }
    }

    // check lines to delete
    private int checkLine()
    {
        int size = board.getBoard().length - 1;
        int filasEliminadas = 0; // Local variable to count the rows deleted in this call to checkLine()

        for (int i = board.getBoard().length - 1; i > 0; i--)
        {
            int count = 0;
            for (int j = 0; j < board.getBoard()[0].length; j++)
            {
                if (board.getBoard()[i][j] != null)
                	count++;

                board.getBoard()[size][j] = board.getBoard()[i][j];
            }
            
            if (count < board.getBoard()[0].length)
                size--;
            
            else
                filasEliminadas++; // Incrementing the counter of deleted rows
        }
        this.setFilasEliminadas(this.getFilasEliminadas() + filasEliminadas); // Add deleted rows to the global counter
        return filasEliminadas; // Return the number of rows deleted in this call to checkLine()
    }

    private void collision()
    {
        collision = true;
        timePassedFromCollision = 0;
    }
    
    public void render(Graphics g)
    {
        g.setColor(color);
        for (int row = 0; row < coords.length; row++)
        {
            for (int col = 0; col < coords[0].length; col++)
            {
                if (coords[row][col] != 0)
                    g.fillRect(col * 30 + x * 30, row * 30 + y * 30, Board.blockSize, Board.blockSize);
            }
        }
    }

    // rotate Shape
    public void rotateShape()
    {
        int[][] rotatedShape = null;

        rotatedShape = transposeMatrix(coords);

        rotatedShape = reverseRows(rotatedShape);

        if ((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20))
            return;

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++)
            {
                if (rotatedShape[row][col] != 0)
                    if (board.getBoard()[y + row][x + col] != null)
                        return;
            }
        }
        coords = rotatedShape;
    }
    private int[][] transposeMatrix(int[][] matrix)
    {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }
    private int[][] reverseRows(int[][] matrix)
    {
        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++)
        {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }

    //modify speed shape
    public void speedUp()
    {
        delay = fast;
    }
    public void speedinsta()
    {
        delay = insta;
    }
    public void speedDown()
    {
        delay = normal;
    }

    // Getters and Setters
    public void setDeltaX(int deltaX)
    {
        this.deltaX = deltaX;
    }
    public Color getColor()
    {
        return color;
    }
    public int[][] getCoords()
    {
        return coords;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
	public int getFilasEliminadas()
	{
		return filasEliminadas;
	}
	public void setFilasEliminadas(int filasEliminadas)
	{
		this.filasEliminadas = filasEliminadas;
	}
}

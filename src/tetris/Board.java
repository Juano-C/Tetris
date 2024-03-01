package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.util.Random;

import controller.WindowController;

public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
    private static final long serialVersionUID = 1L;

    // icons png
    private BufferedImage pause, refresh, music2;
    private Rectangle stopBounds, refreshBounds, musicBounds;

	// board dimensions (the playing area)
    private final int boardHeight = 20, boardWidth = 10;

	// block size
    public static final int blockSize = 30;

	// field
    private Color[][] board = new Color[boardHeight][boardWidth];
    private Color[] colors = 
		 {Color.decode("#ed1c24")
		, Color.decode("#ff7f27") 
		, Color.decode("#fff200") 
		, Color.decode("#22b14c") 
		, Color.decode("#00a2e8") 
		, Color.decode("#a349a4") 
		, Color.decode("#3f48cc")};

	// array with all the possible shapes
    private Shape[] shapes = new Shape[7];

	// currentShape
    private static Shape currentShape, nextShape;

	// game loop
    private Timer looper;
    private int FPS = 60;
    private int delay = 1000 / FPS;

	// mouse events variables
    private int mouseX, mouseY;
    private boolean leftClick = false;

    private boolean gamePaused = false;
    private static boolean gameOver = false;
    private static boolean gameMusic = true;

    private Random random = new Random();

    // buttons press lapse
    private Timer buttonLapse = new Timer(300, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            buttonLapse.stop();
        }
    });

    // Difficult TextFields (normal, fast, ultrafast, megafast, insane)
    private JLabel lblDifficultNormal = new JLabel();
    private JLabel lblDifficultFast = new JLabel();
    private JLabel lblDifficultUltraFast = new JLabel();
    private JLabel lblDifficultMegaFast = new JLabel();
    private JLabel lblDifficultInsane = new JLabel();

	// score
    private static int score = 0;

    private static Reproducer reproducer;
    
    public Board()
    {
        pause = ImageLoader.loadImage("/pause.png");
        refresh = ImageLoader.loadImage("/refresh.png");
        music2 = ImageLoader.loadImage("/music2.png");

        mouseX = 0;
        mouseY = 0;

        reproducer = new Reproducer();
        stopBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(), refresh.getHeight() + refresh.getHeight() / 2);
        musicBounds = new Rectangle(350, 500 - refresh.getHeight() - 85, refresh.getWidth(), music2.getHeight() + music2.getHeight() / 2);

		// create game looper
        looper = new Timer(delay, new GameLooper());

        // I shape
        shapes[0] = new Shape(new int[][]{{1, 1, 1, 1}}, this, colors[0]);
        
        // T shape
        shapes[1] = new Shape(new int[][]{{1, 1, 1}, {0, 1, 0}}, this, colors[1]);
        
        // L shape
        shapes[2] = new Shape(new int[][]{{1, 1, 1},{1, 0, 0}}, this, colors[2]);
        
        // J shape
        shapes[3] = new Shape(new int[][]{{1, 1, 1}, {0, 0, 1}}, this, colors[3]);

        // S shape
        shapes[4] = new Shape(new int[][]{{0, 1, 1}, {1, 1, 0}}, this, colors[4]);

        // Z shape
        shapes[5] = new Shape(new int[][]{{1, 1, 0}, {0, 1, 1}}, this, colors[5]);

        // O shape
        shapes[6] = new Shape(new int[][]{{1, 1}, {1, 1}}, this, colors[6]);
        
        setLayout(null);

        lblDifficultNormal.setBounds(306, 160, 109, 50);
        lblDifficultNormal.setFont(new Font("Segoe Script", Font.PLAIN, 25));
        lblDifficultNormal.setText("Normal");
        add(lblDifficultNormal);
        
        lblDifficultFast.setBounds(306, 160, 109, 50);
        lblDifficultFast.setFont(new Font("Segoe Script", Font.PLAIN, 25));
        lblDifficultFast.setText("Fast");
        add(lblDifficultFast);
        
        lblDifficultUltraFast.setBounds(306, 160, 109, 50);
        lblDifficultUltraFast.setFont(new Font("Segoe Script", Font.PLAIN, 25));
        lblDifficultUltraFast.setText("Ultra Fast");
        add(lblDifficultUltraFast);
        
        lblDifficultMegaFast.setBounds(306, 160, 109, 50);
        lblDifficultMegaFast.setFont(new Font("Segoe Script", Font.PLAIN, 25));
        lblDifficultMegaFast.setText("Mega Fast");
        add(lblDifficultMegaFast);
        
        lblDifficultInsane.setBounds(306, 160, 109, 50);
        lblDifficultInsane.setFont(new Font("Segoe Script", Font.PLAIN, 25));
        lblDifficultInsane.setText("Insane");
        add(lblDifficultInsane);

    }

    private void update()
    {
        if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver)
        {
            buttonLapse.start();
            gamePaused = !gamePaused;
            reproducer.reproduce();
        }

        if (refreshBounds.contains(mouseX, mouseY) && leftClick)
            startGame();
        
        if (musicBounds.contains(mouseX, mouseY) && leftClick)
        {
    		if(reproducer.getPlay())
    		{
    			reproducer.stop();
    			gameMusic = false;
    		}

    		else if( !(reproducer.getPlay()))
    		{
    			reproducer.loadSound("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\music\\soundtrack.wav");
    			reproducer.reproduce();
    			gameMusic = true;
    		}
        }
      
        if (gamePaused || gameOver)
        {
        	reproducer.stop();
        	return;
        }
        
        setDifficultMode();
        	
        currentShape.update();
    }

	// select difficult to show
    private void setDifficultMode()
    {
        if(Shape.DifficultMode() == 0)
        {
        	lblDifficultNormal.setVisible(true);
        	lblDifficultFast.setVisible(false);
        	lblDifficultUltraFast.setVisible(false);
        	lblDifficultMegaFast.setVisible(false);
        	lblDifficultInsane.setVisible(false);
        }
        
        if(Shape.DifficultMode() == 1)
        {
        	lblDifficultNormal.setVisible(false);
        	lblDifficultFast.setVisible(true);
        	lblDifficultUltraFast.setVisible(false);
        	lblDifficultMegaFast.setVisible(false);
        	lblDifficultInsane.setVisible(false);
        }
        
        if(Shape.DifficultMode() == 2)
        {
        	lblDifficultNormal.setVisible(false);
        	lblDifficultFast.setVisible(false);
        	lblDifficultUltraFast.setVisible(true);
        	lblDifficultMegaFast.setVisible(false);
        	lblDifficultInsane.setVisible(false);
        }
        
        if(Shape.DifficultMode() == 3)
        {
        	lblDifficultNormal.setVisible(false);
        	lblDifficultFast.setVisible(false);
        	lblDifficultUltraFast.setVisible(false);
        	lblDifficultMegaFast.setVisible(true);
        	lblDifficultInsane.setVisible(false);
        }
        
        if(Shape.DifficultMode() == 4)
        {
        	lblDifficultNormal.setVisible(false);
        	lblDifficultFast.setVisible(false);
        	lblDifficultUltraFast.setVisible(false);
        	lblDifficultMegaFast.setVisible(false);
        	lblDifficultInsane.setVisible(true);
        }
	}

	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                if (board[row][col] != null)
                {
                    g.setColor(board[row][col]);
                    g.fillRect(col * blockSize, row * blockSize, blockSize, blockSize);
                }
            }
        }

        g.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoords().length; row++)
        {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++)
            {
                if (nextShape.getCoords()[row][col] != 0)
                    g.fillRect(col * 30 + 320, row * 30 + 50, Board.blockSize, Board.blockSize);
            }
        }
        currentShape.render(g);

        //icon pause
        if (stopBounds.contains(mouseX, mouseY))
            g.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
        
        else
            g.drawImage(pause, stopBounds.x, stopBounds.y, null);

        //icon refresh
        if (refreshBounds.contains(mouseX, mouseY))
            g.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3, BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
        
        else
            g.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
        
        //icon music
        if (musicBounds.contains(mouseX, mouseY))
            g.drawImage(music2.getScaledInstance(music2.getWidth() + 3, music2.getHeight() + 3, BufferedImage.SCALE_DEFAULT), musicBounds.x + 3, musicBounds.y + 3, null);
        
        else
            g.drawImage(music2, musicBounds.x, musicBounds.y, null);


        if (gamePaused)
        {
        	gameMusic = false;
            String gamePausedString = "GAME PAUSED";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Georgia", Font.BOLD, 30));
            g.drawString(gamePausedString, 35, StartGame.HEIGHT / 2);
        }

        if (gameOver)
        {
        	reproducer.stop();
        	WindowController.FinishedGameWindow();
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString("SCORE", StartGame.WIDTH - 125, StartGame.HEIGHT / 2);
        g.drawString(score + "", StartGame.WIDTH - 125, StartGame.HEIGHT / 2 + 30);

        for (int i = 0; i <= boardHeight; i++)
        {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        for (int j = 0; j <= boardWidth; j++)
        {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * 30);
        }
    }
	
	// select a random shape
    public void setNextShape()
    {
        int index = random.nextInt(shapes.length);
        setColorShape(index);
    }

	public void setCurrentShape()
    {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++)
        {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++)
            {
                if (currentShape.getCoords()[row][col] != 0)
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null)
                        gameOver = true;
            }
        }
    }
    
    // Set same colors to the exact shapes
    private void setColorShape(int index) 
    {
    	if(index == 0)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 1)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 2)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 3)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 4)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 5)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    	else if(index == 6)
    		nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
	}

    public void startGame()
    {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();
    }
    public void stopGame()
    {
        score = 0;

        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                board[row][col] = null;
            }
        }
        looper.stop();
    }

    public void addScore()
    {
        score++;
    }
    public static int getScore()
    {
    	return score;
    }

    public static void setGameMode(boolean change)
    {
    	gameOver = change;
    }

    public Color[][] getBoard()
    {
        return board;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            currentShape.rotateShape();

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentShape.setDeltaX(1);

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            currentShape.setDeltaX(-1);

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedUp();
        
        if (e.getKeyCode() == KeyEvent.VK_G)
            currentShape.speedinsta();
        
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedDown();
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    class GameLooper implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            update();
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftClick = true;
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftClick = false;
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

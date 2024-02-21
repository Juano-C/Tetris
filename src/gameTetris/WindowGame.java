package gameTetris;

import javax.swing.JFrame;

public class WindowGame {
	
	public static final int WIDTH = 445, HEIGHT = 640;
	
	private Board board; 
	private JFrame window;
	
	public WindowGame() {
		window = new JFrame("Tetris");
		window.setSize(WIDTH,HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(window);
		
		board = new Board();
		window.add(board);
		window.addKeyListener(board);
		
		window.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		new WindowGame();
	}
}

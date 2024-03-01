package frontend;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import controller.WindowController;
import tetris.Reproducer;

public class MenuWindow extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private static Reproducer reproducer;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                	MenuWindow window = new MenuWindow();
                    window.setBounds(500, 500, 500, 500);
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public MenuWindow()
    {
    	getContentPane().setBackground(new Color(0, 0, 0));
    	getContentPane().setForeground(new Color(0, 0, 0));
        setTitle("Tetris Game Menu");
        setSize(500, 500);

        getContentPane().setLayout(null);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        reproducer = new Reproducer();
        
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 0, 484, 461);
        getContentPane().add(panel2);
        
        //------------------EXIT BUTTON--------------------
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(144, 251, 184, 72);
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        panel2.setLayout(null);
        btnExit.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        panel2.add(btnExit);
        
        //------------------START BUTTON--------------------
        JButton btnStart = new JButton("Start");
        btnStart.setBounds(144, 31, 184, 72);
        btnStart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        			reproducer.stop();
        			WindowController.StartGameWindow();
        	}
        });
        btnStart.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        panel2.add(btnStart);
        
        //------------------RECORD BUTTON--------------------
        JButton btnRecordsButton = new JButton("Records");
        btnRecordsButton.setBounds(144, 140, 184, 72);
        btnRecordsButton.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        btnRecordsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(btnRecordsButton, "En proceso");
            }
        });
        panel2.add(btnRecordsButton);
  
        //------------------SOUND BUTTON--------------------
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(206, 368, 71, 80);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e)
        	{	
        		if(reproducer.getPlay())
        			reproducer.stop();

        		else if( !(reproducer.getPlay()))
        		{
        			reproducer.loadSound("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\music\\soundtrack.wav");
        			reproducer.reproduce();
        		}
        	}
        });
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\data\\music.png"));
        panel2.add(btnNewButton);


        
        //------------------GIF TETRIS DOWN--------------------
        JLabel lblGifDown = new JLabel("");
        lblGifDown.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\gifs\\gifTetrisDown.gif"));
        lblGifDown.setHorizontalAlignment(SwingConstants.CENTER);
        lblGifDown.setBounds(10, 11, 124, 437);
        panel2.add(lblGifDown);

        //------------------GIF TETRIS UP--------------------
        JLabel lblGifUp = new JLabel("");
        lblGifUp.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\gifs\\gifTetrisUp.gif"));
        lblGifUp.setHorizontalAlignment(SwingConstants.CENTER);
        lblGifUp.setBounds(338, 11, 136, 437);
        panel2.add(lblGifUp);
   
        //------------------BACKGROUND PIC--------------------
        JLabel lblBoardBackground = new JLabel("");
        lblBoardBackground.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\data\\tetrisBoardClean.png"));
        lblBoardBackground.setHorizontalAlignment(SwingConstants.CENTER);
        lblBoardBackground.setBounds(0, 0, 484, 461);
        panel2.add(lblBoardBackground);

        initialize();
    }

    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(500, 500, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

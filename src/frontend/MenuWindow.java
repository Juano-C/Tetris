package frontend;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.WindowController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class MenuWindow extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JFrame frame;

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
        setTitle("Main Window");
        setSize(500, 500);

        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 484, 461);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        //------------------EXIT BUTTON--------------------
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        btnExit.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        btnExit.setBounds(158, 366, 184, 72);
        panel.add(btnExit);
        
        //------------------START BUTTON--------------------
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		WindowController.StartGameWindow();
        	}
        });
        btnStart.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        btnStart.setBounds(10, 237, 184, 72);
        panel.add(btnStart);
        
        //------------------RECORD BUTTON--------------------
        JButton btnRecordsButton = new JButton("Records");
        btnRecordsButton.setBounds(290, 237, 184, 72);
        btnRecordsButton.setFont(new Font("Segoe Print", Font.PLAIN, 25));
        btnRecordsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(btnRecordsButton, 
                        "En proceso");
            }
        });
        panel.add(btnRecordsButton);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tutorial_Tetris_Java\\src\\moving-formation.gif"));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 484, 461);
        panel.add(lblNewLabel);
        

        initialize();
    }

    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(500, 500, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

package frontend;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;


import controller.WindowController;

public class FinishedGameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinishedGameWindow frame = new FinishedGameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FinishedGameWindow()
	{
		setTitle("Tetris Game");
		setAlwaysOnTop(true);
		setBackground(new Color(0, 0, 64));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 430);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
        //------------------MENU BUTTON--------------------
		JButton btnMenuButton = new JButton("Menu");
		btnMenuButton.setBounds(10, 239, 160, 53);
		btnMenuButton.setFont(new Font("Segoe Print", Font.PLAIN, 25));
		btnMenuButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                WindowController.MenuWindow();
            }
        });
		contentPane.add(btnMenuButton);
		
        //------------------EXIT BUTTON--------------------
		JButton btnExitButton = new JButton("Exit");
		btnExitButton.setBounds(139, 327, 160, 53);
		btnExitButton.setFont(new Font("Segoe Print", Font.PLAIN, 25));
		btnExitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	System.exit(0);
            }
        });
		contentPane.add(btnExitButton);
		
        //------------------RECORDS BUTTON--------------------
		JButton btnRecordsButton = new JButton("Records");
		btnRecordsButton.setBounds(259, 239, 160, 53);
		btnRecordsButton.setFont(new Font("Segoe Print", Font.PLAIN, 25));
		btnRecordsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                WindowController.RecordsWindow();
            }
        });
		contentPane.add(btnRecordsButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 409, 217);
		contentPane.add(panel);
		panel.setLayout(null);
		
        //------------------GAME OVER LABEL--------------------
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 409, 217);
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ChAuV\\eclipse-workspace\\Tetris\\gifs\\gameover.gif"));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}
}

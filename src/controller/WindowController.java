package controller;

import frontend.FinishedGameWindow;
import frontend.MenuWindow;
import tetris.StartGameWindow;

public class WindowController
{

	private static MenuWindow _menuWindow = new MenuWindow();
	private static FinishedGameWindow _finishedGameWindow = new FinishedGameWindow();
	
    public static void MenuWindow()
    {
       	_menuWindow.setVisible(true);
    	_menuWindow.setLocationRelativeTo(null);
    	_finishedGameWindow.setVisible(false);
    }

    public static void StartGameWindow()
    {
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	new StartGameWindow();
    	_finishedGameWindow.setVisible(false);
    }
    
    public static void FinishedGameWindow()
    {
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	_finishedGameWindow.setVisible(true);
    }

	public static void RecordsWindow()
	{
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	_finishedGameWindow.setVisible(true);
	}
}

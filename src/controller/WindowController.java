package controller;

import frontend.FinishedGameWindow;
import frontend.MenuWindow;
import tetris.StartGame;

public class WindowController
{
	private static MenuWindow _menuWindow = new MenuWindow();
	private static FinishedGameWindow _finishedGameWindow = new FinishedGameWindow();
	private static StartGame _startGameWindow = new StartGame();
	
    public static void MenuWindow()
    {
       	_menuWindow.setVisible(true);
    	_menuWindow.setLocationRelativeTo(null);
    	_startGameWindow.setVisible(false);
    	_finishedGameWindow.setVisible(false);
    	
    	
    }

    public static void StartGameWindow()
    {
    	_startGameWindow = new StartGame();
    	
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	_startGameWindow.setVisible(true);
    	_finishedGameWindow.setVisible(false);
    }
    
    public static void FinishedGameWindow()
    {
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	_startGameWindow.setVisible(false);
    	_finishedGameWindow.setVisible(true);
    }

	public static void RecordsWindow()
	{
    	_menuWindow.setVisible(false);
    	_menuWindow.setLocationRelativeTo(null);
    	_startGameWindow.setVisible(false);
    	_finishedGameWindow.setVisible(true);
	}
}


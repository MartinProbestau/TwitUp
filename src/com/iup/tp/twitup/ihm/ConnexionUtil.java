package com.iup.tp.twitup.ihm;

import java.awt.Component;
import java.util.List;

import javax.swing.JMenuItem;

public class ConnexionUtil {
	
	public static void setConnection(boolean isConnected, List<Component> menuConnecte, List<Component> menuDeconnecte ) {
		for(Component item : menuConnecte) {
			item.setVisible(isConnected);
		}
		for(Component item : menuDeconnecte) {
			item.setVisible(!isConnected);
		}
	}

}

package com.iup.tp.twitup.model;

import java.io.File;

import com.iup.tp.twitup.controller.TwitController;
import com.iup.tp.twitup.controller.UserController;
import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.ihm.TwitupMainView;

public class Model {
	
	private final Twitup twitup;
	
	public Model(IDatabase mDatabase, EntityManager mEntityManager, Twitup twitup) {
		UserController userController = new UserController(mDatabase, mEntityManager);
		TwitController twitController = new TwitController(mDatabase, mEntityManager);
		TwitupMainView mainView = new TwitupMainView(userController, twitController, this);
		this.twitup = twitup;
		mainView.showGUI();
	}
	
	public void changeExchangeDirectoryPath(File file) {
		this.twitup.initDirectory(file.getAbsolutePath());
	}
	
}

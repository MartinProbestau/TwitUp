package com.iup.tp.twitup.model;

import com.iup.tp.twitup.controller.TwitController;
import com.iup.tp.twitup.controller.UserController;
import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.ihm.TwitupMainView;

public class Model {
	
	public Model(IDatabase mDatabase, EntityManager mEntityManager) {
		UserController userController = new UserController(mDatabase, mEntityManager);
		TwitController twitController = new TwitController(mDatabase, mEntityManager);
		TwitupMainView mainView = new TwitupMainView(userController, twitController);
		mainView.showGUI();
	}
	
}

package com.iup.tp.twitup.controller;

import java.awt.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ConnexionUtil;

public class UserController {

	/**
	 * Base de donenes de l'application.
	 */
	protected IDatabase mDatabase;

	protected User mConnecte;
	
	protected EntityManager mEntityManager;
	
	public UserController(IDatabase db, EntityManager mEntityManager) {
		mDatabase = db;
		this.mEntityManager = mEntityManager;
	}
	
	public Object[][] listUser(){
		List<User> liste = mDatabase.getUsers().stream().collect(Collectors.toList());
		Object[][] listeUser = new Object[liste.size()][];
		
		for(int i = 0; i < liste.size(); i++) {
			Object[] table = liste.get(i).toRow();
			listeUser[i] = table;
			boolean abonne = this.mConnecte.getFollows().contains(table[1]);
			if(abonne) {
				table[3] = "Se desabonner";
			}
		}
		return listeUser;
	}

	public Optional<User> connecter(String tag, String pass, List<Component> menuConnecte, List<Component> menuDeconnecte){
		List<User> users = mDatabase.getUsers()
				.stream()
				.filter(
						user -> user.getUserTag().equals(tag)
						&&user.getUserPassword().equals(pass))
				.collect(Collectors.toList());
		Optional<User> user = users.stream().findAny();
		if(user.isPresent()) {
			mConnecte = user.get();
			ConnexionUtil.setConnection(true, menuConnecte, menuDeconnecte);
		}
		return user;
	}
	
	public User getUserConnecte() {
		return this.mConnecte;
	}
	
	
	public boolean verification(String tag) {
		return mDatabase.getUsers().stream().anyMatch(user -> user.getUserTag().equals(tag));
	}

	public void addUser(User user) {
		mDatabase.addUser(user);
		mEntityManager.sendUser(user);
	}
	
	public String abonnerDesabonner(String tag) {
		if(!this.isAbonne(tag)) {
			this.mConnecte.addFollowing(tag);
			mEntityManager.sendUser(getUserConnecte());
			return "Vous vous etes abonne.";
		}
		this.mConnecte.removeFollowing(tag);
		mEntityManager.sendUser(getUserConnecte());
		return "Vous vous etes desabonne.";
	}
	
	public boolean isAbonne(String tag) {
		return mConnecte.getFollows().contains(tag);
	}
	
	public Optional<User> getUserByTag(String tag) {
		return mDatabase.getUsers()
						.stream()
						.filter(user -> user.getUserTag().equals(tag))
						.collect(Collectors.toList())
						.stream()
						.findAny();
	}

}

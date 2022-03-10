package com.iup.tp.twitup.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

public class TwitController {
	
	protected IDatabase mDatabase;
	
	protected EntityManager mEntityManager;
	
	public TwitController(IDatabase database, EntityManager mEntityManager) {
		this.mDatabase = database;
		this.mEntityManager = mEntityManager;
	}

	public void addTwit(User twiter, String content) {
		Twit twit = new Twit(twiter, content);
		mDatabase.addTwit(twit);
		mEntityManager.sendTwit(twit);
	}
	
	public List<Twit> getTwitByUser(User user){
		return this.mDatabase.getTwitsWithTag(user.getUserTag()).stream().collect(Collectors.toList());
	}
	
	public List<Twit> getAllTwit(){
		return this.mDatabase.getTwits().stream().collect(Collectors.toList());
	}

	public Object[][] listTwits(){
		List<Twit> liste = mDatabase.getTwits().stream().collect(Collectors.toList());
		liste.sort(Comparator.comparing(Twit::getEmissionDate).reversed());
		Object listeTwits[][] = new Object[liste.size()][];
		for(int i = 0; i < liste.size(); i++) {
			Object table[] = liste.get(i).toRow();
			listeTwits[i] = table;
		}
		return listeTwits;
	}

}

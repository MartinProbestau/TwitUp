package com.iup.tp.twitup.datamodel;

public class DatabaseObserver implements IDatabaseObserver {
	
	@Override
	public void notifyTwitAdded(Twit addedTwit) {
		//S'enclenche quand un twit est ajoute
	}

	@Override
	public void notifyTwitDeleted(Twit deletedTwit) {
		//S'enclenche quand un twit est supprime
	}

	@Override
	public void notifyTwitModified(Twit modifiedTwit) {
		//S'enclenche quand un twit est modifie
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		//S'enclenche quand un user est ajoute
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		//S'enclenche quand un user est supprime
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		//S'enclenche quand un user est modifie
	}

}

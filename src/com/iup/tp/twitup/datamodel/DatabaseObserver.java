package com.iup.tp.twitup.datamodel;

public class DatabaseObserver implements IDatabaseObserver {
	
	@Override
	public void notifyTwitAdded(Twit addedTwit) {
		System.out.println("Twit ajoute !");
	}

	@Override
	public void notifyTwitDeleted(Twit deletedTwit) {
		System.out.println("Twit supprime !");
	}

	@Override
	public void notifyTwitModified(Twit modifiedTwit) {
		System.out.println("Twit modifie !");
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		System.out.println("Utilisateur " + addedUser.getName() + " ajoute !");
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		System.out.println("Utilisateur " + deletedUser.getName() + " ajoute !");		
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		System.out.println("Utilisateur " + modifiedUser.getName() + " ajoute !");		
	}

}

package com.iup.tp.twitup.datamodel;

public class DatabaseObserver implements IDatabaseObserver {
	
	@Override
	public void notifyTwitAdded(Twit addedTwit) {
		System.out.println("Twit ajouté !");
	}

	@Override
	public void notifyTwitDeleted(Twit deletedTwit) {
		System.out.println("Twit supprimé !");
	}

	@Override
	public void notifyTwitModified(Twit modifiedTwit) {
		System.out.println("Twit modifié !");
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		System.out.println("Utilisateur " + addedUser.getName() + " ajouté !");
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		System.out.println("Utilisateur " + deletedUser.getName() + " ajouté !");		
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		System.out.println("Utilisateur " + modifiedUser.getName() + " ajouté !");		
	}

}

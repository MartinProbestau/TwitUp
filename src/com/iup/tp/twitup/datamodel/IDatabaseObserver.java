package com.iup.tp.twitup.datamodel;

/**
 * Interface des observateurs des modifications de la base de donnees.
 * 
 * @author S.Lucas
 */
public interface IDatabaseObserver {
	/**
	 * Notification lorsqu'un Twit est ajoute en base de donnees.
	 * 
	 * @param addedTwit
	 */
	void notifyTwitAdded(Twit addedTwit);

	/**
	 * Notification lorsqu'un Twit est supprime de la base de donnees.
	 * 
	 * @param deletedTwit
	 */
	void notifyTwitDeleted(Twit deletedTwit);

	/**
	 * Notification lorsqu'un Twit est modifie en base de donnees.
	 * 
	 * @param modifiedTwit
	 */
	void notifyTwitModified(Twit modifiedTwit);

	/**
	 * Notification lorsqu'un utilisateur est ajoute en base de donnees.
	 * 
	 * @param addedUser
	 */
	void notifyUserAdded(User addedUser);

	/**
	 * Notification lorsqu'un utilisateur est supprime de la base de donnees.
	 * 
	 * @param deletedUser
	 */
	void notifyUserDeleted(User deletedUser);

	/**
	 * Notification lorsqu'un utilisateur est modifie en base de donnees.
	 * 
	 * @param modifiedUser
	 */
	void notifyUserModified(User modifiedUser);
}

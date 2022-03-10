package com.iup.tp.twitup.datamodel;

import java.util.Set;

/**
 * Interface de la base de donnees de l'application.
 * 
 * @author S.Lucas
 */
public interface IDatabase {

	/**
	 * Ajoute un observateur sur les modifications de la base de donnees.
	 * 
	 * @param observer
	 */
	void addObserver(IDatabaseObserver observer);

	/**
	 * Supprime un observateur sur les modifications de la base de donnees.
	 * 
	 * @param observer
	 */
	void deleteObserver(IDatabaseObserver observer);

	/**
	 * Retourne la liste des utilisateurs
	 */
	Set<User> getUsers();

	/**
	 * Retourne la liste des twits
	 */
	Set<Twit> getTwits();

	/**
	 * Ajoute un twit à la base de donnees.
	 * 
	 * @param twitToAdd
	 */
	void addTwit(Twit twitToAdd);

	/**
	 * Supprime un twit de la base de donnees.
	 * 
	 * @param twitToRemove
	 */
	void removeTwit(Twit twitToRemove);

	/**
	 * Modification d'un twit de la base de donnees. <br/>
	 * <i>Normalement peu probable qu'un twit soit modifie...</i>
	 * 
	 * @param twitToModify
	 */
	void modifiyTwit(Twit twitToModify);

	/**
	 * Ajoute un utilisateur à la base de donnees.
	 * 
	 * @param userToAdd
	 */
	void addUser(User userToAdd);

	/**
	 * Supprime un utilisateur de la base de donnees.
	 * 
	 * @param userToRemove
	 */
	void removeUser(User userToRemove);

	/**
	 * Modification d'un utilisateur de la base de donnees.
	 * 
	 * @param userToModify
	 */
	void modifiyUser(User userToModify);

	/**
	 * Supprime l'integralite des twits enregistres.
	 */
	void clearTwits();

	/**
	 * Supprime l'integralite des utilisateurs enregistres.
	 */
	void clearUsers();

	/**
	 * Supprime l'integralite des donnees.
	 */
	void clear();

	/**
	 * Retourne tous les Twits presents en base ayant le tag donne
	 * 
	 * @param tag
	 *            , tag à rechercher.
	 */
	Set<Twit> getTwitsWithTag(String tag);

	/**
	 * Retourne tous les Twits presents en base ayant le tag utilisateur donne
	 * 
	 * @param userTag
	 *            , tag utilisateur à rechercher.
	 */
	Set<Twit> getTwitsWithUserTag(String userTag);

	/**
	 * Retourne tous les Twits d'un utilisateur.
	 * 
	 * @param user
	 *            , utilisateur dont les twits sont à rechercher.
	 */
	Set<Twit> getUserTwits(User user);

	/**
	 * Retourne tous les utilisateurs suivant l'utilisateur donnee
	 * 
	 * @param user
	 *            , utilisateur dont les followers sont à rechercher.
	 */
	Set<User> getFollowers(User user);

	/**
	 * Retourne le nombre de followers pour l'utilisateur donne.
	 * 
	 * @param user
	 *            , utilisateur dont le nombre de followers est à rechercher.
	 */
	int getFollowersCount(User user);

	/**
	 * Retourne l'utilisateur inconnu du systeme.
	 */
	public User getUnknowUser();

}

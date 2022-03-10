package com.iup.tp.twitup.events.file;

import java.io.File;
import java.util.Set;

/**
 * Interface des observeurs de repertoires
 * 
 * @author S.Lucas
 */
public interface IWatchableDirectoryObserver {

	/**
	 * Notification de la liste des fichiers presents initialement dans le
	 * repertoire.
	 * 
	 * @param presentFiles
	 *            , Liste des fichiers presents initialement.
	 */
	void notifyPresentFiles(Set<File> presentFiles);

	/**
	 * Notification de la liste des nouveaux fichiers dans le repertoire.
	 * 
	 * @param newFiles
	 *            , Liste des nouveaux fichiers.
	 */
	void notifyNewFiles(Set<File> newFiles);

	/**
	 * Notification de la liste des fichiers supprimes dans le repertoire.
	 * 
	 * @param deletedFiles
	 *            , Liste des fichiers supprimes.
	 */
	void notifyDeletedFiles(Set<File> deletedFiles);

	/**
	 * Notification de la liste des fichiers modifies dans le repertoire.
	 * 
	 * @param modifiedFiles
	 *            , Liste des fichiers modifies.
	 */
	void notifyModifiedFiles(Set<File> modifiedFiles);
}

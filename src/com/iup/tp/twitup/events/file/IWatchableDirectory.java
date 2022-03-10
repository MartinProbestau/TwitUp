package com.iup.tp.twitup.events.file;

/**
 * Interface de l'entite responsable de la surveillance d'un repertoire.
 * 
 * @author S.Lucas
 */
public interface IWatchableDirectory {

	/**
	 * Initialisation de la surveillance du repertoire. <br/>
	 * <i> Les observeurs sont premierement avertis du contenu initial du
	 * repertoire, puis avertis des modifications (ajout/suppression)</i>
	 */
	public void initWatching();

	/**
	 * Arret de la surveillance du repertoire.
	 */
	public void stopWatching();

	/**
	 * Changement du repertoire de surveillance. <br/>
	 * Les fichiers presents seront consideres comme 'supprimes' donc les
	 * observateurs seront notifies comme tel.<br/>
	 * Un appel à la methode {@link #initWatching()} est necessaire pour
	 * relancer la surveillance.
	 * 
	 * @param directoryPath
	 *            , nouveau repertoire à surveiller.
	 */
	public void changeDirectory(String directoryPath);

	/**
	 * Ajout un observateur qui sera notifie des changements dans le repertoire
	 * surveille.
	 * 
	 * @param observer
	 */
	public void addObserver(IWatchableDirectoryObserver observer);

	/**
	 * Supprime un observateur de la liste (il ne sera plus notifies des
	 * changements).
	 * 
	 * @param observer
	 */
	public void removeObserver(IWatchableDirectoryObserver observer);

}

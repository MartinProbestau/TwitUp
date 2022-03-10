package com.iup.tp.twitup.events.file;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe responsable de la surveillance d'un repertoire (avec notification des
 * {@link IWatchableDirectoryObserver} lors des modifications)
 * 
 * @author S.Lucas
 */
public class WatchableDirectory implements IWatchableDirectory {

	/**
	 * Temps (en ms) entre deux verification du repertoire.
	 */
	protected static final int POLLING_TIME = 1000;

	/**
	 * Chemin d'acces au repertoire à surveiller.
	 */
	protected String mDirectoryPath;

	/**
	 * Repertoire surveille.
	 */
	protected File mDirectory;

	/**
	 * Liste des fichiers presents.
	 */
	protected Set<File> mPresentFiles;

	/**
	 * Map permettant de stocker les dates de modifications des fichiers.
	 */
	protected Map<String, Long> mFileModificationMap;

	/**
	 * Thread de surveillance du repertoire.
	 */
	protected Thread mWatchingThread;

	/**
	 * Liste des observeurs sur le contenu du repertoire.
	 */
	protected final Set<IWatchableDirectoryObserver> mObservers;

	/**
	 * Constructeur.
	 * 
	 * @param directoryPath
	 *            , Chemin d'acces au repertoire à surveiller.
	 */
	public WatchableDirectory(String directoryPath) {
		this.mDirectoryPath = directoryPath;
		this.mPresentFiles = new HashSet<File>();
		this.mFileModificationMap = new HashMap<String, Long>();
		this.mObservers = new HashSet<IWatchableDirectoryObserver>();
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void changeDirectory(String directoryPath) {
		// Clonage de la liste pour notification
		HashSet<File> presentFiles = new HashSet<File>(this.mPresentFiles);

		// Arret de la surveillance en cours
		this.stopWatching();

		// Notification de la suppression des fichiers
		if (presentFiles.isEmpty() == false) {
			this.notifyDeletedFiles(presentFiles);
		}

		// Reinit du repertoire de surveillance.
		this.mDirectoryPath = directoryPath;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void initWatching() {
		// Chargement du repertoire
		mDirectory = new File(mDirectoryPath);

		// Si le repertoire est valide
		if (mDirectory.exists() && mDirectory.isDirectory()) {
			// Initialisation des fichiers presents
			this.initPresentFiles();

			// Demarrage de la surveillance
			this.startPolling();
		} else {
			mDirectory = null;
			System.err.println("Erreur lors du demarrage de la surveillance du repertoire : " + mDirectoryPath);
		}
	}

	/**
	 * Ajoute un fichier à la liste des ficheirs presents et stock sa date de
	 * modification.
	 */
	protected void addPresentFile(File fileToAdd) {
		// Ajout du fichier
		this.mPresentFiles.add(fileToAdd);

		// Stockage de la date de modification
		this.mFileModificationMap.put(fileToAdd.getName(), fileToAdd.lastModified());
	}

	/**
	 * Initialisation de la liste des fichiers presents (et notification aux
	 * interesses)
	 */
	protected void initPresentFiles() {
		if (mDirectory != null) {
			// Parcours de la liste des fichiers present
			for (File presentFile : mDirectory.listFiles()) {
				// Ajout du fichier courant
				this.addPresentFile(presentFile);
			}

			// Notification de la liste des fichiers presents
			if (this.mPresentFiles.isEmpty() == false) {
				this.notifyPresentFiles(this.mPresentFiles);
			}
		}
	}

	/**
	 * Demarrage de la surveillance du repertoire
	 */
	protected void startPolling() {
		mWatchingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Attente avant la prochaine verification
					Thread.sleep(POLLING_TIME);

					// Verification des changements
					watchDirectory();

					// Relancement automatique
					startPolling();
				} catch (InterruptedException e) {
					System.err.println("Surveillance du repertoire interrompue.");
				}
			}
		});

		mWatchingThread.start();
	}

	/**
	 * Lancement d'une etape de surveillance (avec notification des changements
	 * aux observeurs).
	 */
	protected void watchDirectory() {
		if (mDirectory != null) {
			Set<File> presentFiles = new HashSet<File>();
			Set<File> newFiles = new HashSet<File>();
			Set<File> deletedFiles = new HashSet<File>();
			Set<File> modifiedFiles = new HashSet<File>();
			Set<File> oldFiles = new HashSet<File>(this.mPresentFiles);

			// Recuperation de fichiers actuellement present
			for (File file : mDirectory.listFiles()) {
				presentFiles.add(file);
			}

			// Recuperation des nouveaux fichiers
			for (File presentFile : presentFiles) {
				// Si le fichier n'etait pas present avant
				if (oldFiles.contains(presentFile) == false) {
					// C'est un nouveau fichier
					newFiles.add(presentFile);
				}
			}

			// Recuperation des fichiers supprimes
			for (File oldFile : oldFiles) {
				// Si le fichier n'est plus present
				if (presentFiles.contains(oldFile) == false) {
					// C'est un fichier supprime
					deletedFiles.add(oldFile);
				}
			}

			// Recuperation des fichiers modifies
			for (File presentFile : presentFiles) {
				// Si le fichiers n'est pas nouveau
				if (newFiles.contains(presentFile) == false) {
					// Recuperation de la date de modification de la version
					// precedente
					Long savedLastModification = mFileModificationMap.get(presentFile.getName());

					if (savedLastModification != null) {
						// Si le fichier a ete modifie depuis
						if (savedLastModification < presentFile.lastModified()) {
							// Stockage du fichier comme ayant ete modifie
							modifiedFiles.add(presentFile);
						}
					}
				}
			}

			// Notification des fichiers supprimes
			if (deletedFiles.isEmpty() == false) {
				this.notifyDeletedFiles(deletedFiles);
			}

			// Notification des fichiers ajoutes
			if (newFiles.isEmpty() == false) {
				this.notifyNewFiles(newFiles);
			}

			// Notification des fichiers modifies
			if (modifiedFiles.isEmpty() == false) {
				this.notifyModifiedFiles(modifiedFiles);
			}

			// Mise à jour de la liste des fichiers presents
			this.mPresentFiles.clear();
			this.mFileModificationMap.clear();
			for (File file : presentFiles) {
				this.addPresentFile(file);
			}
		}
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void stopWatching() {
		if (this.mWatchingThread != null) {
			this.mWatchingThread.interrupt();
		}
		this.mPresentFiles.clear();
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void addObserver(IWatchableDirectoryObserver observer) {
		// Notification initiale du contenu
		if (this.mPresentFiles.isEmpty() == false) {
			observer.notifyPresentFiles(this.mPresentFiles);
		}

		// Ajout du nouvel observeur
		this.mObservers.add(observer);
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void removeObserver(IWatchableDirectoryObserver observer) {
		this.mObservers.remove(observer);
	}

	/**
	 * Notification de la liste des fichiers presents initialement dans le
	 * repertoire.
	 * 
	 * @param presentFiles
	 */
	protected void notifyPresentFiles(Set<File> presentFiles) {
		// Clonage de la liste pour eviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyPresentFiles(presentFiles);
		}
	}

	/**
	 * Notification de la liste des nouveaux fichiers dans le repertoire.
	 * 
	 * @param newFiles
	 *            , Liste des nouveaux fichiers.
	 */
	protected void notifyNewFiles(Set<File> newFiles) {
		// Clonage de la liste pour eviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyNewFiles(newFiles);
		}
	}

	/**
	 * Notification de la liste des fichiers supprimes dans le repertoire.
	 * 
	 * @param deletedFiles
	 *            , Liste des fichiers supprimes.
	 */
	protected void notifyDeletedFiles(Set<File> deletedFiles) {
		// Clonage de la liste pour eviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyDeletedFiles(deletedFiles);
		}
	}

	/**
	 * Notification de la liste des fichiers modifies dans le repertoire.
	 * 
	 * @param modifiedFiles
	 *            , Liste des fichiers modifies.
	 */
	protected void notifyModifiedFiles(Set<File> modifiedFiles) {
		// Clonage de la liste pour eviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyModifiedFiles(modifiedFiles);
		}
	}
}
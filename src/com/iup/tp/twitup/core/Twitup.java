package com.iup.tp.twitup.core;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.iup.tp.twitup.datamodel.Database;
import com.iup.tp.twitup.datamodel.DatabaseObserver;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.IDatabaseObserver;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.model.Model;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup {
	
	/**
	 * Base de donnees.
	 */
	protected IDatabase mDatabase;
	/**
	 * Observeur de la base de donnees.
	 */
	protected IDatabaseObserver mDatabaseObserver;

	/**
	 * Gestionnaire des entites contenu de la base de donnees.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected Model model;

	/**
	 * Classe de surveillance de repertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Repertoire d'echange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Idnique si le mode bouchone est active.
	 */
	protected boolean mIsMockEnabled = false;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	/**
	 * Constructeur.
	 */
	public Twitup() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de la base de donnees
		this.initDatabase();

		if (this.mIsMockEnabled) {
			// Initialisation du bouchon de travail
		}

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du repertoire d'echange
		this.initDirectory();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		model = new Model(mDatabase, mEntityManager, this);
	}

	/**
	 * Initialisation du repertoire d'echange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir ete saisi et etre valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		this.initDirectory("src/resources");
	}

	/**
	 * Indique si le fichier donne est valide pour servire de repertoire
	 * d'echange
	 * 
	 * @param directory
	 *            , Repertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si repertoire disponible en lecture et ecriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation de la base de donnees
	 */
	protected void initDatabase() {
		mDatabase = new Database();
		mDatabaseObserver = new DatabaseObserver();
		mDatabase.addObserver(mDatabaseObserver);
		mEntityManager = new EntityManager(mDatabase);
	}

	/**
	 * Initialisation du repertoire d'echange.
	 * 
	 * @param directoryPath
	 */
	public void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	public void show() {
		// ... setVisible?
	}
}

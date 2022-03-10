package com.iup.tp.twitup.common;

import java.util.UUID;

/**
 * Classe de contantes de l'appli.
 * 
 * @author S.Lucas
 */
public interface Constants {
	/**
	 * Extension des fichiers XML des User
	 */
	public static final String USER_FILE_EXTENSION = "usr";

	/**
	 * Extension des fichiers XML des Twit
	 */
	public static final String TWIT_FILE_EXTENSION = "twt";

	/**
	 * Extension des fichiers XML des DB utilisateur
	 */
	public static final String DB_FILE_EXTENSION = "db";

	/**
	 * Repertoire des fichiers temporaires du systeme.
	 */
	public static final String SYSTEM_TMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Separateur de fichier du systeme.
	 */
	public static final String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * Caractere pour delimiter les tags referencant des utilisateurs.
	 */
	public static final String USER_TAG_DELIMITER = "@";

	/**
	 * Caractere pour delimiter les tags referencant des mots-cles.
	 */
	public static final String WORD_TAG_DELIMITER = "#";

	/**
	 * Identifiant de l'utilisateur inconnu.
	 */
	public static final UUID UNKNONWN_USER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

	/**
	 * Fichier de configuration de l'application.
	 */
	public static final String CONFIGURATION_FILE = "src/resources/configuration.properties";

	/**
	 * Cle de configuration pour la sauvegarde du repertoire d'echange.
	 */
	public static final String CONFIGURATION_KEY_EXCHANGE_DIRECTORY = "EXCHANGE_DIRECTORY";

	/**
	 * Cle de configuration pour l'UI
	 */
	public static final String CONFIGURATION_KEY_UI_CLASS_NAME = "UI_CLASS_NAME";

	/**
	 * Cle de configuration pour le mode bouchone
	 */
	public static final String CONFIGURATION_KEY_MOCK_ENABLED = "MOCK_ENABLED";
}

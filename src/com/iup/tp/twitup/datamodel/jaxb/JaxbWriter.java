package com.iup.tp.twitup.datamodel.jaxb;

import java.io.File;
import java.io.FileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.iup.tp.twitup.common.FilesUtils;
import com.iup.tp.twitup.datamodel.jaxb.bean.twit.TwitXml;
import com.iup.tp.twitup.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de generation des fichiers XML.
 * 
 * @author S.Lucas
 */
public class JaxbWriter {
	/**
	 * Generation d'un fichier pour un twit ({@link TwitXml}).
	 * 
	 * @param twit
	 *            , TwitXml à generer.
	 * @param destFileName
	 *            , Fichier de destination.
	 * @return un booleen indiquant si la generation s'est deroulee avec succes.
	 */
	public static boolean writeTwitFile(TwitXml twit, String destFileName) {
		return writeFile(TwitXml.class, twit, destFileName);
	}

	/**
	 * Generation d'un fichier pour un utilisateur ({@link UserXml}).
	 * 
	 * @param user
	 *            , UserXml à generer.
	 * @param destFileName
	 *            , Fichier de destination.
	 * @return un booleen indiquant si la generation s'est deroulee avec succes.
	 */
	public static boolean writeUserFile(UserXml user, String destFileName) {
		return writeFile(UserXml.class, user, destFileName);
	}

	/**
	 * Generation d'un fichier XML correspondant à un objet.
	 * 
	 * @param jaxbContext
	 *            , Contexte JAXB pour le marshalling
	 * @param objectToMarshal
	 *            , Objet à convertir en XML.
	 * @param destFileName
	 *            , Chemin du fichier de destination finale.
	 * @return un booleen indiquant si l'operation s'est deroulee avec succes.
	 */
	protected static boolean writeFile(Class<?> clazz, Object objectToMarshal, String destFileName) {
		boolean isOk = false;

		try {
			// Conf du marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Cr�ation du fichier XML temporaire
			File tmpFile = File.createTempFile("twitup", ".tmp");
			marshaller.marshal(objectToMarshal, new FileWriter(tmpFile.getAbsolutePath()));

			// Si la g�n�ration s'est bien pass�e, d�placement du
			// fichier
			if (tmpFile.exists()) {
				isOk = FilesUtils.moveFile(tmpFile, destFileName);
			}
		} catch (Throwable t) {
			System.err.println("Erreur lors de la generation du fichier pour l'objet : '" + objectToMarshal + "'");
			t.printStackTrace();
		}

		return isOk;
	}

}

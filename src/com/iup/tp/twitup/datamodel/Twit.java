package com.iup.tp.twitup.datamodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import com.iup.tp.twitup.common.Constants;

/**
 * Classe du modele representant un twit.
 * 
 * @author S.Lucas
 */
public class Twit {
	/**
	 * Identifiant unique du twit.
	 */
	protected final UUID mUuid;

	/**
	 * Utilisateur source.
	 */
	protected final User mTwiter;

	/**
	 * Date d'emission du twit.
	 */
	protected final long mEmissionDate;

	/**
	 * Corps du message.
	 */
	protected final String mText;

	/**
	 * Liste des tags representant un utilisateur present dans le twit.
	 */
	protected final Set<String> mUserTags;

	/**
	 * Liste des tags present dans le twit.
	 */
	protected final Set<String> mTags;

	/**
	 * Constructeur.
	 * 
	 * @param twiter utilisateur à l'origine du twit.
	 * @param text   , corps du message.
	 */
	public Twit(User twiter, String text) {
		this(UUID.randomUUID(), twiter, System.currentTimeMillis(), text);
	}

	/**
	 * Constructeur.
	 * 
	 * @param twitUuid     , identifiant du twit.
	 * @param twiter       , utilisateur à l'origine du twit.
	 * @param emissionDate , date d'emission du twit.
	 * @param text         , corps du message.
	 */
	public Twit(UUID twitUuid, User twiter, long emissionDate, String text) {
		mUuid = twitUuid;
		mTwiter = twiter;
		mEmissionDate = emissionDate;
		mText = text;
		mTags = new HashSet<String>();
		mUserTags = new HashSet<String>();

		// Initialisation des mots-cl�s
		this.initTags(mText);
	}

	/**
	 * Initialisation de la liste de tags presents dans le corps du message.
	 */
	protected void initTags(String text) {
		if (text != null) {
			// Ajoute les tags correspondants aux utilisateurs.
			mUserTags.addAll(this.extractTags(text, Constants.USER_TAG_DELIMITER));

			// Ajoute les tags correspondants aux mots-cl�s.
			mTags.addAll(this.extractTags(text, Constants.WORD_TAG_DELIMITER));
		}
	}

	/**
	 * Retourne les tags presents dans le texte en fonction du caractere de
	 * detection.
	 * 
	 * @param text         , Texte à analyser.
	 * @param tagDelimiter , Caractere de delimitation des tags à rechercher.
	 */
	protected Set<String> extractTags(String text, String tagDelimiter) {
		Set<String> tags = new HashSet<String>();

		// Ajout d'un caractere special pour reconnaitre les elements
		// reellement
		// tagge
		String specialChar = "~";
		String replacedText = text.replace(tagDelimiter, tagDelimiter + specialChar);

		// Decoupage en foncion du delimiteur.
		String[] taggedStrings = replacedText.split(tagDelimiter);

		// Parcours de tous les groupes recuperes
		for (String taggedString : taggedStrings) {
			// Si la chaine courante commencait bien par le delimiteur
			if (taggedString.startsWith(specialChar)) {
				// Recuperation du tag (du delimiteur jusqu'au premier
				// espace)
				String newTag = taggedString.split(" ")[0];

				// Suppression du caractere sp�cial
				newTag = newTag.substring(1, newTag.length());

				// Ajout du tag à la liste
				tags.add(newTag);
			}
		}

		return tags;
	}

	/**
	 * @return l'identifiant du Twit.
	 */
	public UUID getUuid() {
		return mUuid;
	}

	/**
	 * @return l'utilisateur source.
	 */
	public User getTwiter() {
		return mTwiter;
	}

	/**
	 * @return le corps du message.
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Retourne la date d'emission.
	 */
	public long getEmissionDate() {
		return this.mEmissionDate;
	}

	/**
	 * Retourne une liste clonee des tags du twit. <br/>
	 * <i> Les tags sont les mots du twit precedes par la
	 * {@link Constants#WORD_TAG_DELIMITER}</i>
	 */
	public Set<String> getTags() {
		return new HashSet<String>(mTags);
	}

	/**
	 * Retourne une liste clonee des tags du twit representant un utilisateur. <br/>
	 * <i> Les tags utilisateurs sont les mots du twit precedes par la
	 * {@link Constants#USER_TAG_DELIMITER}</i>
	 */
	public Set<String> getUserTags() {
		return new HashSet<String>(mUserTags);
	}

	/**
	 * Indique si le Twit possede le tag donne.
	 * 
	 * @param aTag , tag à rechercher.
	 */
	public boolean containsTag(String aTag) {
		return this.getTags().contains(aTag);
	}

	/**
	 * Indique si le Twit possede le tag utilisateur donne.
	 * 
	 * @param anUserTag , tag utilisateur à rechercher.
	 */
	public boolean containsUserTag(String anUserTag) {
		return this.getUserTags().contains(anUserTag);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		int hashCode = 0;

		if (this.mUuid != null) {
			hashCode = this.mUuid.hashCode();
		}

		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;

		if (other != null) {
			if (other instanceof Twit) {
				Twit otherTwit = (Twit) other;
				equals = (this.getUuid().equals(otherTwit.getUuid()));
			}
		}

		return equals;
	}

	public Object[] toRow() {
		
		// Supprime les # du text
		String texteTwit = this.mText;
		texteTwit = texteTwit.replaceAll("(?i)[#][^ ]+", "");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy  'à' HH:mm:ss", Locale.FRENCH);
		String date = simpleDateFormat.format(new Date(this.getEmissionDate())); 
		Set<String> listeTag = this.getTags();
		StringBuilder tag = new StringBuilder("");
		for(String liste : listeTag) {
			tag.append("#"+liste);
		}
		
		Object[] twit = new Object[1];
		
		if(tag.toString().equals("")) {
			twit[0] = "<html><p>"+texteTwit+"</p><br><p>"+this.mTwiter.getUserTag()+" @"+this.mTwiter.getUserTag()+"</p><p> -- "+date+" -- </p></html>";
		}else {
			twit[0] = "<html><p>"+texteTwit+"</p><br><p>"+this.mTwiter.getName()+" @"+this.mTwiter.getUserTag()+"</p><p>"+tag+"</p><p> -- "+date+" -- </p></html>";
		}
		return twit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		sb.append(this.getClass().getName());
		sb.append("] : ");
		sb.append(this.getUuid());
		sb.append(" {");
		sb.append(this.getText());
		sb.append("}");

		return sb.toString();
	}

}

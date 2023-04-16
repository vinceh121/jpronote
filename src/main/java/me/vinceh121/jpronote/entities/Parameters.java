package me.vinceh121.jpronote.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameters {
	private boolean withMember, nouvelleCaledonie, hasMobile;
	private int conImgType, theme;
	private String urlImgCon, urlMobile, name;
	private ServerDate date;

	@JsonProperty("avecMembre")
	public boolean isWithMember() {
		return this.withMember;
	}

	@JsonProperty("avecMembre")
	public void setWithMember(final boolean withMember) {
		this.withMember = withMember;
	}

	@JsonProperty("pourNouvelleCaledonie")
	public boolean isNouvelleCaledonie() {
		return this.nouvelleCaledonie;
	}

	@JsonProperty("pourNouvelleCaledonie")
	public void setNouvelleCaledonie(final boolean nouvelleCaledonie) {
		this.nouvelleCaledonie = nouvelleCaledonie;
	}

	@JsonProperty("AvecEspaceMobile")
	public boolean hasMobile() {
		return this.hasMobile;
	}

	@JsonProperty("AvecEspaceMobile")
	public void hasMobile(final boolean hasMobile) {
		this.hasMobile = hasMobile;
	}

	@JsonProperty("genreImageConnexion")
	public int getConImgType() {
		return this.conImgType;
	}

	@JsonProperty("genreImageConnexion")
	public void setConImgType(final int conImgType) {
		this.conImgType = conImgType;
	}

	@JsonProperty("Theme")
	public int getTheme() {
		return this.theme;
	}

	@JsonProperty("Theme")
	public void setTheme(final int theme) {
		this.theme = theme;
	}

	@JsonProperty("urlImageConnexion")
	public String getUrlImgCon() {
		return this.urlImgCon;
	}

	@JsonProperty("urlImageConnexion")
	public void setUrlImgCon(final String urlImgCon) {
		this.urlImgCon = urlImgCon;
	}

	@JsonProperty("URLMobile")
	public String getUrlMobile() {
		return this.urlMobile;
	}

	@JsonProperty("URLMobile")
	public void setUrlMobile(final String urlMobile) {
		this.urlMobile = urlMobile;
	}

	@JsonProperty("nom")
	public String getName() {
		return this.name;
	}

	@JsonProperty("nom")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("DateServeurHttp")
	public ServerDate getDate() {
		return this.date;
	}

	@JsonProperty("DateServeurHttp")
	public void setDate(final ServerDate date) {
		this.date = date;
	}

	public class GeneralSettings {
		private int langID, afficherSemainesCalendaires, placesParJour, placesParHeure, placeDemiJourneeAbsence,
				debutDemiPension, finDemiPension, joursOuvresParCycle, premierJourSemaine, grillesEDTEnCycle,
				minBaremeQuestionQCM, maxBaremeQuestionQCM, maxNbPointQCM, tailleLibelleElementGrilleCompetence,
				tailleCommentaireDevoir, taillePolice, maxECTS;
		private String version, versionPN, millesime, langue, lienMentions, nomEtablissement, anneeScolaire, police,
				maskTelephone, nomCookieAppli;
		private boolean avecForum, avecChoixConnexion, activationDemiPension, avecHeuresPleinesApresMidi,
				activationMessagerieEntreParents, afficherAbbreviationNiveauDAcquisition, avecEvaluationHistorique,
				sansValidationNivIntermediairesDsValidAuto, neComptabiliserQueEvalsAnneeScoDsValidAuto, o365_Actif,
				o365_ModeFederated, avecRecuperationInfosConnexion, avecElevesRattaches, afficherSequences;
		private double dureeSequence;

		@JsonProperty("version")
		public void setVersion(final String version) {
			this.version = version;
		}

		@JsonProperty("version")
		public String getVersion() {
			return this.version;
		}

		@JsonProperty("versionPN")
		public void setVersionPN(final String versionPN) {
			this.versionPN = versionPN;
		}

		@JsonProperty("versionPN")
		public String getVersionPN() {
			return this.versionPN;
		}

		@JsonProperty("millesime")
		public void setMillesime(final String millesime) {
			this.millesime = millesime;
		}

		@JsonProperty("millesime")
		public String getMillesime() {
			return this.millesime;
		}

		@JsonProperty("langue")
		public void setLangue(final String langue) {
			this.langue = langue;
		}

		@JsonProperty("langue")
		public String getLangue() {
			return this.langue;
		}

		@JsonProperty("langID")
		public void setLangID(final int langID) {
			this.langID = langID;
		}

		@JsonProperty("langID")
		public int getLangID() {
			return this.langID;
		}

		@JsonProperty("lienMentions")
		public void setLienMentions(final String lienMentions) {
			this.lienMentions = lienMentions;
		}

		@JsonProperty("lienMentions")
		public String getLienMentions() {
			return this.lienMentions;
		}

		@JsonProperty("avecForum")
		public void setAvecForum(final boolean avecForum) {
			this.avecForum = avecForum;
		}

		@JsonProperty("avecForum")
		public boolean getAvecForum() {
			return this.avecForum;
		}

		@JsonProperty("AvecChoixConnexion")
		public void setAvecChoixConnexion(final boolean avecChoixConnexion) {
			this.avecChoixConnexion = avecChoixConnexion;
		}

		@JsonProperty("AvecChoixConnexion")
		public boolean getAvecChoixConnexion() {
			return this.avecChoixConnexion;
		}

		@JsonProperty("NomEtablissement")
		public void setNomEtablissement(final String nomEtablissement) {
			this.nomEtablissement = nomEtablissement;
		}

		@JsonProperty("NomEtablissement")
		public String getNomEtablissement() {
			return this.nomEtablissement;
		}

		@JsonProperty("afficherSemainesCalendaires")
		public void setAfficherSemainesCalendaires(final int afficherSemainesCalendaires) {
			this.afficherSemainesCalendaires = afficherSemainesCalendaires;
		}

		@JsonProperty("afficherSemainesCalendaires")
		public int getAfficherSemainesCalendaires() {
			return this.afficherSemainesCalendaires;
		}

		@JsonProperty("AnneeScolaire")
		public void setAnneeScolaire(final String anneeScolaire) {
			this.anneeScolaire = anneeScolaire;
		}

		@JsonProperty("AnneeScolaire")
		public String getAnneeScolaire() {
			return this.anneeScolaire;
		}

		@JsonProperty("PlacesParJour")
		public void setPlacesParJour(final int placesParJour) {
			this.placesParJour = placesParJour;
		}

		@JsonProperty("PlacesParJour")
		public int getPlacesParJour() {
			return this.placesParJour;
		}

		@JsonProperty("PlacesParHeure")
		public void setPlacesParHeure(final int placesParHeure) {
			this.placesParHeure = placesParHeure;
		}

		@JsonProperty("PlacesParHeure")
		public int getPlacesParHeure() {
			return this.placesParHeure;
		}

		@JsonProperty("DureeSequence")
		public void setDureeSequence(final double dureeSequence) {
			this.dureeSequence = dureeSequence;
		}

		@JsonProperty("DureeSequence")
		public double getDureeSequence() {
			return this.dureeSequence;
		}

		@JsonProperty("PlaceDemiJourneeAbsence")
		public void setPlaceDemiJourneeAbsence(final int placeDemiJourneeAbsence) {
			this.placeDemiJourneeAbsence = placeDemiJourneeAbsence;
		}

		@JsonProperty("PlaceDemiJourneeAbsence")
		public int getPlaceDemiJourneeAbsence() {
			return this.placeDemiJourneeAbsence;
		}

		@JsonProperty("activationDemiPension")
		public void setActivationDemiPension(final boolean activationDemiPension) {
			this.activationDemiPension = activationDemiPension;
		}

		@JsonProperty("activationDemiPension")
		public boolean getActivationDemiPension() {
			return this.activationDemiPension;
		}

		@JsonProperty("debutDemiPension")
		public void setDebutDemiPension(final int debutDemiPension) {
			this.debutDemiPension = debutDemiPension;
		}

		@JsonProperty("debutDemiPension")
		public int getDebutDemiPension() {
			return this.debutDemiPension;
		}

		@JsonProperty("finDemiPension")
		public void setFinDemiPension(final int finDemiPension) {
			this.finDemiPension = finDemiPension;
		}

		@JsonProperty("finDemiPension")
		public int getFinDemiPension() {
			return this.finDemiPension;
		}

		@JsonProperty("AvecHeuresPleinesApresMidi")
		public void setAvecHeuresPleinesApresMidi(final boolean avecHeuresPleinesApresMidi) {
			this.avecHeuresPleinesApresMidi = avecHeuresPleinesApresMidi;
		}

		@JsonProperty("AvecHeuresPleinesApresMidi")
		public boolean getAvecHeuresPleinesApresMidi() {
			return this.avecHeuresPleinesApresMidi;
		}

		@JsonProperty("ActivationMessagerieEntreParents")
		public void setActivationMessagerieEntreParents(final boolean activationMessagerieEntreParents) {
			this.activationMessagerieEntreParents = activationMessagerieEntreParents;
		}

		@JsonProperty("ActivationMessagerieEntreParents")
		public boolean getActivationMessagerieEntreParents() {
			return this.activationMessagerieEntreParents;
		}

		@JsonProperty("joursOuvresParCycle")
		public void setJoursOuvresParCycle(final int joursOuvresParCycle) {
			this.joursOuvresParCycle = joursOuvresParCycle;
		}

		@JsonProperty("joursOuvresParCycle")
		public int getJoursOuvresParCycle() {
			return this.joursOuvresParCycle;
		}

		@JsonProperty("premierJourSemaine")
		public void setPremierJourSemaine(final int premierJourSemaine) {
			this.premierJourSemaine = premierJourSemaine;
		}

		@JsonProperty("premierJourSemaine")
		public int getPremierJourSemaine() {
			return this.premierJourSemaine;
		}

		@JsonProperty("grillesEDTEnCycle")
		public void setGrillesEDTEnCycle(final int grillesEDTEnCycle) {
			this.grillesEDTEnCycle = grillesEDTEnCycle;
		}

		@JsonProperty("grillesEDTEnCycle")
		public int getGrillesEDTEnCycle() {
			return this.grillesEDTEnCycle;
		}

		@JsonProperty("AfficherAbbreviationNiveauDAcquisition")
		public void setAfficherAbbreviationNiveauDAcquisition(final boolean afficherAbbreviationNiveauDAcquisition) {
			this.afficherAbbreviationNiveauDAcquisition = afficherAbbreviationNiveauDAcquisition;
		}

		@JsonProperty("AfficherAbbreviationNiveauDAcquisition")
		public boolean getAfficherAbbreviationNiveauDAcquisition() {
			return this.afficherAbbreviationNiveauDAcquisition;
		}

		@JsonProperty("AvecEvaluationHistorique")
		public void setAvecEvaluationHistorique(final boolean avecEvaluationHistorique) {
			this.avecEvaluationHistorique = avecEvaluationHistorique;
		}

		@JsonProperty("AvecEvaluationHistorique")
		public boolean getAvecEvaluationHistorique() {
			return this.avecEvaluationHistorique;
		}

		@JsonProperty("SansValidationNivIntermediairesDsValidAuto")
		public void setSansValidationNivIntermediairesDsValidAuto(
				final boolean sansValidationNivIntermediairesDsValidAuto) {
			this.sansValidationNivIntermediairesDsValidAuto = sansValidationNivIntermediairesDsValidAuto;
		}

		@JsonProperty("SansValidationNivIntermediairesDsValidAuto")
		public boolean getSansValidationNivIntermediairesDsValidAuto() {
			return this.sansValidationNivIntermediairesDsValidAuto;
		}

		@JsonProperty("NeComptabiliserQueEvalsAnneeScoDsValidAuto")
		public void setNeComptabiliserQueEvalsAnneeScoDsValidAuto(
				final boolean neComptabiliserQueEvalsAnneeScoDsValidAuto) {
			this.neComptabiliserQueEvalsAnneeScoDsValidAuto = neComptabiliserQueEvalsAnneeScoDsValidAuto;
		}

		@JsonProperty("NeComptabiliserQueEvalsAnneeScoDsValidAuto")
		public boolean getNeComptabiliserQueEvalsAnneeScoDsValidAuto() {
			return this.neComptabiliserQueEvalsAnneeScoDsValidAuto;
		}

		@JsonProperty("minBaremeQuestionQCM")
		public void setMinBaremeQuestionQCM(final int minBaremeQuestionQCM) {
			this.minBaremeQuestionQCM = minBaremeQuestionQCM;
		}

		@JsonProperty("minBaremeQuestionQCM")
		public int getMinBaremeQuestionQCM() {
			return this.minBaremeQuestionQCM;
		}

		@JsonProperty("maxBaremeQuestionQCM")
		public void setMaxBaremeQuestionQCM(final int maxBaremeQuestionQCM) {
			this.maxBaremeQuestionQCM = maxBaremeQuestionQCM;
		}

		@JsonProperty("maxBaremeQuestionQCM")
		public int getMaxBaremeQuestionQCM() {
			return this.maxBaremeQuestionQCM;
		}

		@JsonProperty("maxNbPointQCM")
		public void setMaxNbPointQCM(final int maxNbPointQCM) {
			this.maxNbPointQCM = maxNbPointQCM;
		}

		@JsonProperty("maxNbPointQCM")
		public int getMaxNbPointQCM() {
			return this.maxNbPointQCM;
		}

		@JsonProperty("tailleLibelleElementGrilleCompetence")
		public void setTailleLibelleElementGrilleCompetence(final int tailleLibelleElementGrilleCompetence) {
			this.tailleLibelleElementGrilleCompetence = tailleLibelleElementGrilleCompetence;
		}

		@JsonProperty("tailleLibelleElementGrilleCompetence")
		public int getTailleLibelleElementGrilleCompetence() {
			return this.tailleLibelleElementGrilleCompetence;
		}

		@JsonProperty("tailleCommentaireDevoir")
		public void setTailleCommentaireDevoir(final int tailleCommentaireDevoir) {
			this.tailleCommentaireDevoir = tailleCommentaireDevoir;
		}

		@JsonProperty("tailleCommentaireDevoir")
		public int getTailleCommentaireDevoir() {
			return this.tailleCommentaireDevoir;
		}

		@JsonProperty("O365_Actif")
		public void setO365_Actif(final boolean o365_Actif) {
			this.o365_Actif = o365_Actif;
		}

		@JsonProperty("O365_Actif")
		public boolean getO365_Actif() {
			return this.o365_Actif;
		}

		@JsonProperty("O365_ModeFederated")
		public void setO365_ModeFederated(final boolean o365_ModeFederated) {
			this.o365_ModeFederated = o365_ModeFederated;
		}

		@JsonProperty("O365_ModeFederated")
		public boolean getO365_ModeFederated() {
			return this.o365_ModeFederated;
		}

		@JsonProperty("AvecRecuperationInfosConnexion")
		public void setAvecRecuperationInfosConnexion(final boolean avecRecuperationInfosConnexion) {
			this.avecRecuperationInfosConnexion = avecRecuperationInfosConnexion;
		}

		@JsonProperty("AvecRecuperationInfosConnexion")
		public boolean getAvecRecuperationInfosConnexion() {
			return this.avecRecuperationInfosConnexion;
		}

		@JsonProperty("Police")
		public void setPolice(final String police) {
			this.police = police;
		}

		@JsonProperty("Police")
		public String getPolice() {
			return this.police;
		}

		@JsonProperty("TaillePolice")
		public void setTaillePolice(final int taillePolice) {
			this.taillePolice = taillePolice;
		}

		@JsonProperty("TaillePolice")
		public int getTaillePolice() {
			return this.taillePolice;
		}

		@JsonProperty("AvecElevesRattaches")
		public void setAvecElevesRattaches(final boolean avecElevesRattaches) {
			this.avecElevesRattaches = avecElevesRattaches;
		}

		@JsonProperty("AvecElevesRattaches")
		public boolean getAvecElevesRattaches() {
			return this.avecElevesRattaches;
		}

		@JsonProperty("maskTelephone")
		public void setMaskTelephone(final String maskTelephone) {
			this.maskTelephone = maskTelephone;
		}

		@JsonProperty("maskTelephone")
		public String getMaskTelephone() {
			return this.maskTelephone;
		}

		@JsonProperty("maxECTS")
		public void setMaxECTS(final int maxECTS) {
			this.maxECTS = maxECTS;
		}

		@JsonProperty("maxECTS")
		public int getMaxECTS() {
			return this.maxECTS;
		}

		@JsonProperty("afficherSequences")
		public void setAfficherSequences(final boolean afficherSequences) {
			this.afficherSequences = afficherSequences;
		}

		@JsonProperty("afficherSequences")
		public boolean getAfficherSequences() {
			return this.afficherSequences;
		}

		@JsonProperty("nomCookieAppli")
		public void setNomCookieAppli(final String nomCookieAppli) {
			this.nomCookieAppli = nomCookieAppli;
		}

		@JsonProperty("nomCookieAppli")
		public String getNomCookieAppli() {
			return this.nomCookieAppli;
		}

	}

	public class ServerDate extends AbstractPronoteEntity {
		private final DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
		private String value;

		@JsonProperty("V")
		public String getValue() {
			return this.value;
		}

		@JsonProperty("V")
		public void setValue(final String value) {
			this.value = value;
		}

		public Date toDate() throws ParseException {
			return this.format.parse(this.value);
		}

	}
}

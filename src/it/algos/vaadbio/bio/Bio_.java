package it.algos.vaadbio.bio;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.field.TextArea;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

/**
 * Created by gac on 23 nov 2015.
 * .
 */
@StaticMetamodel(Bio.class)
public class Bio_ extends BaseEntity_ {
    public static volatile SingularAttribute<Bio, Long> pageid;
    public static volatile SingularAttribute<Bio, String> title;
    public static volatile SingularAttribute<Bio, TextArea> tmplBioServer;
    public static volatile SingularAttribute<Bio, TextArea> tmplBioStandard;
    public static volatile SingularAttribute<Bio, Timestamp> ultimaLettura;
    public static volatile SingularAttribute<Bio, Timestamp> ultimaElaborazione;
    public static volatile SingularAttribute<Bio, Boolean> templateEsiste;
    public static volatile SingularAttribute<Bio, Boolean> templateValido;
    public static volatile SingularAttribute<Bio, Boolean> templatesUguali;

    public static volatile SingularAttribute<Bio, String> titolo;
    public static volatile SingularAttribute<Bio, String> nome;
    public static volatile SingularAttribute<Bio, String> cognome;
    public static volatile SingularAttribute<Bio, String> cognomePrima;
    public static volatile SingularAttribute<Bio, String> postCognome;
    public static volatile SingularAttribute<Bio, String> postCognomeVirgola;
    public static volatile SingularAttribute<Bio, String> forzaOrdinamento;
    public static volatile SingularAttribute<Bio, String> preData;
    public static volatile SingularAttribute<Bio, String> sesso;

    public static volatile SingularAttribute<Bio, String> luogoNascita;
    public static volatile SingularAttribute<Bio, String> luogoNascitaLink;
    public static volatile SingularAttribute<Bio, String> luogoNascitaAlt;
    public static volatile SingularAttribute<Bio, String> giornoMeseNascita;
    public static volatile SingularAttribute<Bio, String> annoNascita;
    public static volatile SingularAttribute<Bio, String> noteNascita;

    public static volatile SingularAttribute<Bio, String> luogoMorte;
    public static volatile SingularAttribute<Bio, String> luogoMorteLink;
    public static volatile SingularAttribute<Bio, String> luogoMorteAlt;
    public static volatile SingularAttribute<Bio, String> giornoMeseMorte;
    public static volatile SingularAttribute<Bio, String> annoMorte;
    public static volatile SingularAttribute<Bio, String> noteMorte;

    public static volatile SingularAttribute<Bio, String> preAttivita;
    public static volatile SingularAttribute<Bio, String> epoca;
    public static volatile SingularAttribute<Bio, String> epoca2;
    public static volatile SingularAttribute<Bio, String> attivita;
    public static volatile SingularAttribute<Bio, String> attivita2;
    public static volatile SingularAttribute<Bio, String> attivita3;
    public static volatile SingularAttribute<Bio, String> attivitaAltre;

    public static volatile SingularAttribute<Bio, String> nazionalita;
    public static volatile SingularAttribute<Bio, String> nazionalitaNaturalizzato;
    public static volatile SingularAttribute<Bio, String> cittadinanza;
    public static volatile SingularAttribute<Bio, String> postNazionalita;

    public static volatile SingularAttribute<Bio, String> categorie;
    public static volatile SingularAttribute<Bio, String> fineIncipit;
    public static volatile SingularAttribute<Bio, String> punto;
    public static volatile SingularAttribute<Bio, String> immagine;
    public static volatile SingularAttribute<Bio, String> didascalia;
    public static volatile SingularAttribute<Bio, String> didascalia2;
    public static volatile SingularAttribute<Bio, String> dimImmagine;

    public static volatile SingularAttribute<Bio, String> nomeValido;
    public static volatile SingularAttribute<Bio, String> cognomeValido;
    public static volatile SingularAttribute<Bio, String> sessoValido;

    public static volatile SingularAttribute<Bio, String> luogoNascitaValido;
    public static volatile SingularAttribute<Bio, String> luogoNascitaLinkValido;
    public static volatile SingularAttribute<Bio, String> giornoMeseNascitaValido;
    public static volatile SingularAttribute<Bio, String> annoNascitaValido;

    public static volatile SingularAttribute<Bio, String> luogoMorteValido;
    public static volatile SingularAttribute<Bio, String> luogoMorteLinkValido;
    public static volatile SingularAttribute<Bio, String> giornoMeseMorteValido;
    public static volatile SingularAttribute<Bio, String> annoMorteValido;

    public static volatile SingularAttribute<Bio, String> attivitaValida;
    public static volatile SingularAttribute<Bio, String> attivita2Valida;
    public static volatile SingularAttribute<Bio, String> attivita3Valida;
    public static volatile SingularAttribute<Bio, String> nazionalitaValida;

    public static volatile SingularAttribute<Bio, Giorno> giornoNatoPunta;
    public static volatile SingularAttribute<Bio, Giorno> giornoMortoPunta;
    public static volatile SingularAttribute<Bio, Anno> annoNatoPunta;
    public static volatile SingularAttribute<Bio, Anno> annoMortoPunta;

    public static volatile SingularAttribute<Bio, String> didascaliaGiornoNato;
    public static volatile SingularAttribute<Bio, String> didascaliaGiornoMorto;
    public static volatile SingularAttribute<Bio, String> didascaliaAnnoNato;
    public static volatile SingularAttribute<Bio, String> didascaliaAnnoMorto;


}// end of entity class

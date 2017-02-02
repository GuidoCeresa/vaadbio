package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.HashMap;

/**
 * Created by gac on 21 nov 2016.
 * Crea la lista di una attività e la carica sul server wiki
 */
public class ListaAttivita extends ListaBio {

     final static String PROGETTO_BIOGRAFIE_ATTIVITÀ = "Progetto:Biografie/Attività/";
     final static String PROGETTO_BIOGRAFIE_NAZIONALITÀ = "Progetto:Biografie/Nazionalità/";

    /**
     * Costruttore senza parametri
     */
     ListaAttivita() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param attivita indicata
     */
    public ListaAttivita(Attivita attivita) {
        super(attivita);
    }// fine del costruttore


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();

        // head
        usaHeadTocIndice = true;
        usaHeadIncipit = true;
        tagHeadTemplateProgetto = "biografie";

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = false;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 100);

        // footer
        usaFooterPortale = true;
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            usaFooterCategorie = false;
        } else {
            usaFooterCategorie = true;
        }// end of if/else cycle
    }// fine del metodo


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        titoloPagina = PROGETTO_BIOGRAFIE_ATTIVITÀ + getAttivitaText();
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    @Override
    protected void elaboraListaBiografie() {
        Attivita attivita = this.getAttivita();

        if (attivita != null) {
            listaBio = attivita.bio();
        }// end of if cycle
    }// fine del metodo


    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        return LibBio.getChiavePerNazionalita(bio, tagParagrafoNullo);
    }// fine del metodo


    /**
     * Costruisce il titolo del paragrafo
     * <p>
     * Questo deve essere composto da:
     * Professione.pagina
     * Genere.plurale
     */
    protected String getTitoloParagrafo(Bio bio) {
        String titoloParagrafo = tagParagrafoNullo;
        String paginaWiki = CostBio.VUOTO;
        String linkVisibile = CostBio.VUOTO;
        String nazionalita = "";

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        nazionalita = LibBio.getChiavePerNazionalita(bio, tagParagrafoNullo);
        if (nazionalita != null) {
            paginaWiki = LibText.primaMaiuscola(nazionalita);
            linkVisibile = LibText.primaMaiuscola(nazionalita);
            titoloParagrafo = costruisceTitolo(paginaWiki, linkVisibile);
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce la chiave del paragrafo
     * <p>
     * Questo deve essere composto da:
     * Professione.pagina
     * Genere.plurale
     */
    protected String getChiaveParagrafo(Bio bio) {
        String chiaveParagrafo = tagParagrafoNullo;
        Nazionalita nazionalita = null;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        nazionalita = bio.getNazionalitaPunta();
        if (nazionalita != null) {
            chiaveParagrafo = nazionalita.getPlurale();
            chiaveParagrafo = LibText.primaMaiuscola(chiaveParagrafo);
        }// end of if cycle

        return chiaveParagrafo;
    }// fine del metodo

    /**
     * Costruisce il titolo del paragrafo
     * <p>
     * Questo deve essere composto da:
     * Professione.pagina
     * Genere.plurale
     */
    private String getTitoloParagrafo(String chiaveParagrafo) {
        String titoloParagrafo = tagParagrafoNullo;
        String pipe = "|";

        if (!chiaveParagrafo.equals(CostBio.VUOTO)) {
            titoloParagrafo = PROGETTO_BIOGRAFIE_NAZIONALITÀ + chiaveParagrafo + pipe + chiaveParagrafo;
            titoloParagrafo = LibWiki.setQuadre(titoloParagrafo);
            titoloParagrafo = LibWiki.setParagrafo(titoloParagrafo);
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce il titolo
     * Controlla se il titolo visibile (link) non esiste già
     * Se esiste, sostituisce la pagina (prima parte del titolo) con quella già esistente
     */
    protected String costruisceTitolo(String paginaWiki, String linkVisibile) {

        if (linkVisibile.equals(tagParagrafoNullo)) {
            return LibWiki.setParagrafo(linkVisibile);
        } else {
            return getTitoloParagrafo(linkVisibile);
        }// end of if/else cycle

    }// fine del metodo


    /**
     * Piede della pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String elaboraFooter() {
        String text = CostBio.VUOTO;
        String attivitaPluraleMaiuscola = LibText.primaMaiuscola(getPluraleAttività());

        text = "==Voci correlate==";
        text += CostBio.A_CAPO;
        text += "*[[:Categoria:" + attivitaPluraleMaiuscola + "]]";
        text += CostBio.A_CAPO;
        text += "*[[Progetto:Biografie/Attività]]";
        text += CostBio.A_CAPO;

        if (usaFooterPortale) {
            text += CostBio.A_CAPO;
            text += "{{Portale|biografie}}";
        }// end of if cycle

        if (usaFooterCategorie) {
            text += CostBio.A_CAPO;
            text += "[[Categoria:Bio attività|" + getPluraleAttività() + "]]";
        }// end of if cycle

        return text;
    }// fine del metodo


    /**
     * Controlla che la modifica sia sostanziale
     * Se il flag è false, registra sempre
     * Se il flag è vero, controlla la differenza del testo
     * Sovrascritto
     */
    @Override
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA, false)) {
            return true;
        } else {
            return LibBio.checkModificaSostanziale(titolo, testo, tagHeadTemplateAvviso, "}}");
        }// end of if/else cycle
    }// fine del metodo

    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaAttivitaABC(this, mappa);
    }// fine del metodo


    public Attivita getAttivita() {
        return (Attivita) getOggetto();
    }// end of getter method


    public String getPluraleAttività() {
        String plurale = "";
        Attivita attivita = getAttivita();

        if (attivita != null) {
            plurale = attivita.getPlurale();
            plurale = LibText.primaMaiuscola(plurale);
        }// end of if cycle

        return plurale;
    }// end of getter method


    public String getAttivitaText() {
        String attivitaText = "";
        Attivita attivita = getAttivita();

        if (attivita != null) {
            attivitaText = attivita.getPlurale();
            attivitaText = LibText.primaMaiuscola(attivitaText);
        }// end of if cycle

        return attivitaText;
    }// end of getter method

}// fine della classe

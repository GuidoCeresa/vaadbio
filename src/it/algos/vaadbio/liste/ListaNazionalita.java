package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.genere.Genere;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.vaadbio.professione.Professione;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.HashMap;

/**
 * Created by gac on 31/01/17.
 * Crea la lista di una nazionalità e la carica sul server wiki
 */
public class ListaNazionalita extends ListaAttNaz {


    /**
     * Costruttore senza parametri
     */
     ListaNazionalita() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param nazionalita indicata
     */
    public ListaNazionalita(Nazionalita nazionalita) {
        super(nazionalita);
    }// fine del costruttore



    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        titoloPagina = PROGETTO_BIOGRAFIE_NAZIONALITÀ + getNazionalitaText();
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    @Override
    protected void elaboraListaBiografie() {
        Nazionalita nazionalita = this.getNazionalita();

        if (nazionalita != null) {
            listaBio = nazionalita.bio();
        }// end of if cycle
    }// fine del metodo


    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        return LibBio.getChiavePerAttivita(bio, tagParagrafoNullo);
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
        String paginaWiki;
        String linkVisibile;
        String attivita;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = LibBio.getChiavePerAttivita(bio, tagParagrafoNullo);
        if (attivita != null) {
            paginaWiki = LibText.primaMaiuscola(attivita);
            linkVisibile = LibText.primaMaiuscola(attivita);
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
    @Deprecated
    protected String getChiaveParagrafo(Bio bio) {
        String chiaveParagrafo = tagParagrafoNullo;
        Attivita attivita = null;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();
        if (attivita != null) {
            chiaveParagrafo = attivita.getPlurale();
            chiaveParagrafo = LibText.primaMaiuscola(chiaveParagrafo);
        }// end of if cycle

        return chiaveParagrafo;
    }// fine del metodo

    /**
     * Costruisce il titolo del paragrafo
     * <p>
     * Arriva chiaveParagrafo
     * Lo cerco come plurale di Genere
     * Prendo il primo singolare di Genere
     * Prendo il corrispondente plurale di Attivita
     */
    private String getTitoloParagrafo(String chiaveParagrafo) {
        String titoloParagrafo = tagParagrafoNullo;
        Professione professione = null;
        String professioneTxt;
        String paginaWiki = CostBio.VUOTO;
        Genere genere = null;
        String genereSingolare;
        String linkVisibile = CostBio.VUOTO;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;
        String attivitaPlurale = CostBio.VUOTO;

        if (chiaveParagrafo.equals("")) {
            return CostBio.VUOTO;
        }// end of if cycle

        genere = Genere.findByFirstPlurale(chiaveParagrafo.toLowerCase());
        if (genere != null) {
            genereSingolare = genere.getSingolare();
            attivita = Attivita.findBySingolare(genereSingolare);
        }// end of if cycle

        if (attivita != null) {
            attivitaPlurale = attivita.getPlurale();
            paginaWiki = LibText.primaMaiuscola(attivitaPlurale);
//            linkVisibile = LibText.primaMaiuscola(attivitaPlurale);
            titoloParagrafo = getTitoloParagrafo(paginaWiki, chiaveParagrafo);
        }// end of if cycle

        int a = 87;
        return titoloParagrafo;

    }// fine del metodo

    /**
     * Costruisce il titolo del paragrafo
     */
    private String getTitoloParagrafo(String paginaWiki, String linkVisibile) {
        String titoloParagrafo;
        String pipe = "|";

        titoloParagrafo = PROGETTO_BIOGRAFIE_ATTIVITÀ + paginaWiki + pipe + linkVisibile;
        titoloParagrafo = LibWiki.setQuadre(titoloParagrafo);
        titoloParagrafo = LibWiki.setParagrafo(titoloParagrafo);

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
        String text;
        String nazionalitaPluraleMaiuscola = LibText.primaMaiuscola(getPluraleNazionalita());

        text = "==Voci correlate==";
        text += CostBio.A_CAPO;
        text += "*[[:Categoria:" + nazionalitaPluraleMaiuscola + "]]";
        text += CostBio.A_CAPO;
        text += "*[[Progetto:Biografie/Nazionalità]]";
        text += CostBio.A_CAPO;

        if (usaFooterPortale) {
            text += CostBio.A_CAPO;
            text += "{{Portale|biografie}}";
        }// end of if cycle

        if (usaFooterCategorie) {
            text += CostBio.A_CAPO;
            text += "[[Categoria:Bio nazionalità|" + getPluraleNazionalita() + "]]";
        }// end of if cycle

        return text;
    }// fine del metodo



    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaNazionalitaABC(this, mappa);
    }// fine del metodo


    public Nazionalita getNazionalita() {
        return (Nazionalita) getOggetto();
    }// end of getter method


    private String getPluraleNazionalita() {
        String plurale = "";
        Nazionalita nazionalita = getNazionalita();

        if (nazionalita != null) {
            plurale = nazionalita.getPlurale();
            plurale = LibText.primaMaiuscola(plurale);
        }// end of if cycle

        return plurale;
    }// end of getter method


    protected String getNazionalitaText() {
        String nazionalitaText = "";
        Nazionalita nazionalita = getNazionalita();

        if (nazionalita != null) {
            nazionalitaText = nazionalita.getPlurale();
            nazionalitaText = LibText.primaMaiuscola(nazionalitaText);
        }// end of if cycle

        return nazionalitaText;
    }// end of getter method

}// fine della classe

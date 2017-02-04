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
public class ListaAttivita extends ListaAttNaz {


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
        String paginaWiki ;
        String linkVisibile;
        String nazionalita ;

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
    @Deprecated
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

package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gac on 21 nov 2016.
 * Crea la lista di una attività e la carica sul server wiki
 */
public class ListaAttivita extends ListaBio {

    private final static String PATH_PROGETTO = "Progetto:Biografie/Nazionalità/";

    /**
     * Costruttore senza parametri
     */
    protected ListaAttivita() {
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
        tagHeadTemplateProgetto = "antroponimi";
        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = true;
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
        String tag = "Progetto:Biografie/Attività/";
        titoloPagina = tag + LibText.primaMaiuscola(getAttivita().getPlurale());
    }// fine del metodo


    /**
     * Costruisce una singola mappa
     * Sovrascritto
     */
    @Override
    protected void elaboraMappa(Bio bio) {
        String didascalia;
        ArrayList<String> lista;
        String chiaveParagrafo;
        HashMap<String, Object> mappa;

        chiaveParagrafo = getChiaveParagrafo(bio);
        didascalia = bio.getDidascaliaListe();

        if (mappaBio.containsKey(chiaveParagrafo)) {
            lista = (ArrayList<String>) mappaBio.get(chiaveParagrafo).get(KEY_MAP_LISTA);
            lista.add(didascalia);
        } else {
            mappa = new HashMap<>();
            lista = new ArrayList<>();
            lista.add(didascalia);
            mappa.put(KEY_MAP_LINK, chiaveParagrafo);
            mappa.put(KEY_MAP_TITOLO, chiaveParagrafo);
            mappa.put(KEY_MAP_LISTA, lista);
            mappa.put(KEY_MAP_SESSO, bio.getSesso());
            mappaBio.put(chiaveParagrafo, mappa);
        }// end of if/else cycle
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
    protected String getTitoloParagrafo(String chiaveParagrafo) {
        String titoloParagrafo = tagParagrafoNullo;
        String pipe = "|";

        if (!chiaveParagrafo.equals(CostBio.VUOTO)) {
            titoloParagrafo = PATH_PROGETTO + chiaveParagrafo + pipe + chiaveParagrafo;
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
     * <p>
     * Sovrascritto
     */
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        return true;
    }// fine del metodo


    /**
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    @Override
    protected List<Bio> getListaBio() {
        List<Bio> listaBio = null;
        Attivita attivita = this.getAttivita();

        if (attivita != null) {
            listaBio = attivita.bio();
        }// end of if cycle

        return listaBio;
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

}// fine della classe

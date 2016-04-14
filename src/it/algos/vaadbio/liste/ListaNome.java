package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.genere.Genere;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.vaadbio.professione.Professione;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gac on 27 dic 2015.
 * Crea la lista delle persone col nome indicato e la carica sul server wiki
 */
public class ListaNome extends ListaBio {


    /**
     * Costruttore senza parametri
     */
    protected ListaNome() {
    }// fine del costruttore

    /**
     * Costruttore
     *
     * @param nome indicato
     */
    public ListaNome(Nome nome) {
        super(nome);
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

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = true;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 100);

    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String tag = "Persone di nome ";

        titoloPagina = tag + getNomeTxt();
    }// fine del metodo


    /**
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    @Override
    protected ArrayList<Bio> getListaBio() {
        ArrayList<Bio> listaBio = null;
        Nome nome = this.getNome();

        if (nome != null) {
            listaBio = nome.bioNome();
        }// end of if cycle

        return listaBio;
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

        chiaveParagrafo = getTitoloParagrafo(bio);
        didascalia = bio.getDidascaliaListe();

        if (mappaBio.containsKey(chiaveParagrafo)) {
            lista = (ArrayList<String>) mappaBio.get(chiaveParagrafo).get(KEY_MAP_LISTA);
            lista.add(didascalia);
        } else {
            mappa = new HashMap<String, Object>();
            lista = new ArrayList<>();
            lista.add(didascalia);
            mappa.put(KEY_MAP_LINK, getPaginaLinkata(bio));
            mappa.put(KEY_MAP_TITOLO, getTitoloPar(bio));
            mappa.put(KEY_MAP_LISTA, lista);
            mappa.put(KEY_MAP_SESSO, bio.getSesso());
            mappaBio.put(chiaveParagrafo, mappa);
        }// end of if/else cycle
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
        Professione professione = null;
        String professioneTxt;
        String paginaWiki = CostBio.VUOTO;
        Genere genere = null;
        String genereTxt;
        String linkVisibile = CostBio.VUOTO;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();

        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            professione = Professione.findBySingolare(attivitaSingolare);
            genere = Genere.findBySingolareAndSesso(attivitaSingolare, bio.getSesso());
        }// end of if cycle

        if (professione != null) {
            professioneTxt = professione.getPagina();
        } else {
            professioneTxt = attivitaSingolare;
        }// end of if/else cycle
        if (!professioneTxt.equals(CostBio.VUOTO)) {
            paginaWiki = LibText.primaMaiuscola(professioneTxt);
        }// end of if cycle

        if (genere != null) {
            genereTxt = genere.getPlurale();
            if (!genereTxt.equals(CostBio.VUOTO)) {
                linkVisibile = LibText.primaMaiuscola(genereTxt);
            }// end of if cycle
        }// end of if cycle

        if (!paginaWiki.equals(CostBio.VUOTO) && !linkVisibile.equals(CostBio.VUOTO)) {
            titoloParagrafo = costruisceTitolo(paginaWiki, linkVisibile);
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce il link alla pagina linkata da inserire nel titolo del paragrafo
     */
    protected String getPaginaLinkata(Bio bio) {
        String paginaWiki = CostBio.VUOTO;
        Professione professione = null;
        String professioneTxt;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();
        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            professione = Professione.findBySingolare(attivitaSingolare);
        }// end of if cycle

        if (professione != null) {
            professioneTxt = professione.getPagina();
        } else {
            professioneTxt = attivitaSingolare;
        }// end of if/else cycle
        if (!professioneTxt.equals(CostBio.VUOTO)) {
            paginaWiki = LibText.primaMaiuscola(professioneTxt);
        }// end of if cycle

        return paginaWiki;
    }// fine del metodo

    /**
     * Costruisce il titolo visibile del paragrafo
     */
    protected String getTitoloPar(Bio bio) {
        String titoloParagrafo = tagParagrafoNullo;
        Genere genere = null;
        String genereTxt;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();
        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            genere = Genere.findBySingolareAndSesso(attivitaSingolare, bio.getSesso());
        }// end of if cycle

        if (genere != null) {
            genereTxt = genere.getPlurale();
            if (!genereTxt.equals(CostBio.VUOTO)) {
                titoloParagrafo = LibText.primaMaiuscola(genereTxt);
            }// end of if cycle
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce il titolo
     * Controlla se il titolo visibile (link) non esiste già
     * Se esiste, sostituisce la pagina (prima parte del titolo) con quella già esistente
     */
    private String costruisceTitolo(String paginaWiki, String linkVisibile) {
        String titoloParagrafo = LibWiki.setLink(paginaWiki, linkVisibile);
        String link;

        if (linkVisibile.equals(tagParagrafoNullo)) {
            return linkVisibile;
        }// end of if cycle

        for (String keyCompleta : mappaBio.keySet()) {
            link = keyCompleta.substring(keyCompleta.indexOf("|") + 1);
            link = LibWiki.setNoQuadre(link);
            if (link.equals(linkVisibile)) {
                titoloParagrafo = keyCompleta;
                break;
            }// end of if cycle
        }// end of for cycle

        if (usaTitoloParagrafoConLink) {
            titoloParagrafo = LibBio.fixLink(titoloParagrafo);
        }// end of if/else cycle

        return titoloParagrafo;
    }// fine del metodo


    /**
     * Costruisce il paragrafo
     * Sovrascrivibile
     */
    @Override
    protected String righeParagrafo() {
        String text = CostBio.VUOTO;
        int numVociParagrafo;
        String key;
        HashMap<String, Object> mappa;
        String titoloParagrafo;
        String titoloSottopagina;
        String paginaLinkata;
        String titoloVisibile;
        String sesso;
        ArrayList<String> lista;

        for (Map.Entry<String, HashMap> mappaTmp : mappaBio.entrySet()) {
            text += CostBio.A_CAPO;

            key = mappaTmp.getKey();
            mappa = (HashMap) mappaTmp.getValue();

            paginaLinkata = (String) mappa.get(KEY_MAP_LINK);
            titoloVisibile = (String) mappa.get(KEY_MAP_TITOLO);
            lista = (ArrayList<String>) mappa.get(KEY_MAP_LISTA);
            numVociParagrafo = lista.size();

            titoloParagrafo = costruisceTitolo(paginaLinkata, titoloVisibile);
            text += LibWiki.setParagrafo(titoloParagrafo);
            text += CostBio.A_CAPO;

            if (usaSottopagine && numVociParagrafo > maxVociParagrafo) {
                titoloSottopagina = titoloPagina + "/" + titoloVisibile;
                text += "{{Vedi anche|" + titoloSottopagina + "}}";
                creaSottopagina(mappa);
            } else {
                for (String didascalia : lista) {
                    text += CostBio.ASTERISCO;
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo

    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaNomeABC(this, mappa);
    }// fine del metodo


    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    @Override
    protected String elaboraIncipitSpecifico() {
        String text = "incipit lista nomi|nome=" + getNomeTxt();
        return LibWiki.setGraffe(text);
    }// fine del metodo


    /**
     * Piede della pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String elaboraFooter() {
        String text = "Categoria:Liste di persone per nome|" + getNomeTxt();

        text = LibWiki.setQuadre(text);
        text = LibBio.setNoInclude(text);

        return CostBio.A_CAPO + text;
    }// fine del metodo

    public Nome getNome() {
        return (Nome) getOggetto();
    }// end of getter method

    public String getNomeTxt() {
        String nomeTxt = "";
        Nome nome = getNome();

        if (nome != null) {
            nomeTxt = nome.getNome();
        }// end of if cycle

        return nomeTxt;
    }// end of getter method

}// fine della classe

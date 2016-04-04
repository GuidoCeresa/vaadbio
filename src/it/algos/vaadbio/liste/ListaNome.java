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
import java.util.LinkedHashMap;

/**
 * Created by gac on 27 dic 2015.
 * Crea la lista delle persone col nome indicato e la carica sul server wiki
 */
public class ListaNome extends ListaBio {


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
        usaHeadIncipit = true;

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = false;
        // footer

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
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
        ArrayList<Bio> listaNomi = null;
        Nome nome = getNome();
        String didascalia;
        int taglio = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        ArrayList<String> lista;
        String chiaveParagrafo;

        if (nome != null) {
            listaNomi = nome.bioNome();
        }// end of if cycle

        if (listaNomi != null && listaNomi.size() >= taglio) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (Bio bio : listaNomi) {
                chiaveParagrafo = getTitoloParagrafo(bio);
                didascalia = bio.getDidascaliaListe();

                if (mappaBiografie.containsKey(chiaveParagrafo)) {
                    lista = mappaBiografie.get(chiaveParagrafo);
                    lista.add(didascalia);
                } else {
                    lista = new ArrayList<>();
                    lista.add(didascalia);
                    mappaBiografie.put(chiaveParagrafo, lista);
                }// end of if/else cycle
            }// end of if cycle
            numPersone = listaNomi.size();
        }// end of for cycle
    }// fine del metodo


    /**
     * Costruisce il titolo del paragrafo
     * <p>
     * Questo deve essere composto da:
     * Professione.pagina
     * Genere.plurale
     */
    protected String getTitoloParagrafo(Bio bio) {
        String titoloParagrafo = ALTRE;
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
     * Costruisce il titolo
     * Controlla se il titolo visibile (link) non esiste già
     * Se esiste, sostituisce la pagina (prima parte del titolo) con quella già esistente
     */
    private String costruisceTitolo(String paginaWiki, String linkVisibile) {
        String titoloParagrafo = LibWiki.setLink(paginaWiki, linkVisibile);
        String link;

        for (String keyCompleta : mappaBiografie.keySet()) {
            link = keyCompleta.substring(keyCompleta.indexOf("|") + 1);
            link = LibWiki.setNoQuadre(link);
            if (link.equals(linkVisibile)) {
                titoloParagrafo = keyCompleta;
                break;
            }// end of if cycle
        }// end of for cycle

        titoloParagrafo = LibBio.fixLink(titoloParagrafo);
        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    @Override
    protected void creaSottopagina(String chiaveParagrafo, String titoloParagrafo, ArrayList<String> lista) {
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

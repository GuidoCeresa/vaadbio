package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gac on 02/02/17.
 * Crea la sottopagina della lista di attività indicata e la carica sul server wiki
 */
public class ListaAttivitaLettera extends ListaAttivitaABC {

    private HashMap<String, Object> mappaSuper;
    private String titoloVisibile;

    /**
     * Costruttore senza parametri
     */
    protected ListaAttivitaLettera() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param attivita indicata
     */
    public ListaAttivitaLettera(Attivita attivita) {
        super(attivita);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperAttivita della superPagina
     * @param mappaSuper         del paragrafo
     */
    public ListaAttivitaLettera(ListaAttivita listaSuperAttivita, HashMap<String, Object> mappaSuper) {
        this.mappaSuper = mappaSuper;
        this.oggetto = listaSuperAttivita.getOggetto();
        this.titoloVisibile = (String) mappaSuper.get(KEY_MAP_TITOLO);
        this.titoloPaginaMadre = listaSuperAttivita.getTitoloPagina();
        doInit();
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

        // body
        usaSuddivisioneParagrafi = false;
        usaSottopagine = false;
        usaOrdineAlfabeticoParagrafi = true;
        tagParagrafoNullo = "...";
        usaTitoloParagrafoConLink = false;
        usaTaglioVociPagina = false;

    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        titoloPagina = titoloPaginaMadre  + "/" + titoloVisibile;
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    @Override
    protected void elaboraListaBiografie() {
        listaBio = (List<Bio>) mappaSuper.get(KEY_MAP_LISTA);
    }// fine del metodo


    /**
     * Nessun raggruppamento
     */
    @Override
    protected String righeSemplici() {
        String text = CostBio.VUOTO;
        ArrayList<Bio> lista = null;
        String key = titoloVisibile;
        HashMap mappa = null;

        if (mappaBio.size() == 1) {
            mappa = mappaBio.get(key);
        }// end of if cycle

        if (mappa != null) {
            lista = (ArrayList<Bio>) mappa.get(KEY_MAP_LISTA);
        }// end of if cycle

        if (lista != null) {
            try { // prova ad eseguire il codice
                for (Bio bio : lista) {
                    text += CostBio.ASTERISCO;
                    text += CostBio.SPAZIO;
                    text += bio.getDidascaliaListe();
                    text += CostBio.A_CAPO;
                }// end of for cycle
            } catch (Exception unErrore) { // intercetta l'errore
                int a = 87;
            }// fine del blocco try-catch
        }// end of if cycle

        return text;
    }// fine del metodo

    @Override
    public String getAttivitaText() {
        return titoloPaginaMadre  + "/" + titoloVisibile;
    }// fine del metodo

}// fine della classe

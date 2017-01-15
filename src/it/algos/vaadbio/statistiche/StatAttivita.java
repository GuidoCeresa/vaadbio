package it.algos.vaadbio.statistiche;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.cognome.Cognome;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by gac on 10/01/17.
 * Pagine di controllo del progetto Biografie
 * Crea 1 pagina:
 * - Progetto:Biografie/Attivita: Elenco delle xxx attivita che hanno più di yyy ricorrenze nelle voci biografiche
 */
public class StatAttivita extends Statistiche {

    private static String TITOLO_PAGINA = "Attività";

    private ArrayList<Attivita> listaAttivita;
    private LinkedHashMap<Attivita, Integer> mappaAttivita;
    private long inizio = System.currentTimeMillis();
    private int mod = 0;
    private String modTxt;


    /**
     * Costruttore vuoto
     */
    public StatAttivita() {
        super();
    }// end of constructor

    /**
     * Costruttore completo
     */
    public StatAttivita(LinkedHashMap<Attivita, Integer> mappaAttivita) {
        this.mappaAttivita = mappaAttivita;
        doInit();
    }// end of constructor

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
        tagPath = PATH_BIO;
        usaHeadInclude = false;
        usaHeadTocIndice = false;

        // footer
        usaFooterNote = false;
        usaFooterCorrelate = true;

        titoloPagina = TITOLO_PAGINA;
    }// fine del metodo

    /**
     * Costruisce una mappa di valori varii
     * Sovrascritto
     */
    @Override
    protected void elaboraMappaBiografie() {
        if (mappaAttivita == null) {
//            mappaAttivita = Attivita.findMappaTaglioListe();
        }// end of if cycle
    }// fine del metodo


}// end of class

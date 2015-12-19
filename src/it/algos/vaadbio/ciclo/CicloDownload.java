package it.algos.vaadbio.ciclo;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.download.DownloadPages;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Created by gac on 23 nov 2015.
 * <p>
 * Esegue un ciclo di sincronizzazione tra le pagine della categoria TAG_BIO ed i records della tavola Bio
 * <p>
 * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
 * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
 * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
 * <p>
 * Il ciclo viene chiamato da DaemonDownload (con frequenza giornaliera)
 * Il ciclo può essere invocato dal bottone 'Ciclo Down' nella tavola Bio
 * Il ciclo necessita del login come bot per il funzionamento normale
 */
public class CicloDownload {

    public final static String TAG_BIO = "BioBot";
    public final static String TAG_CAT_DEBUG = "Nati nel 1420";
    //--numero massimo accettato da mediawiki per le richieste multiple
    private final static int PAGES_PER_REQUEST = 500;


    /**
     * Costruttore completo
     */
    public CicloDownload() {
        doInit();
    }// end of constructor


    /**
     * Legge la categoria BioBot
     * Legge le voci Bio esistenti
     * <p>
     * Trova la differenza negativa (records mancanti)
     * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti sul server e mancanti nel database
     * Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
     * <p>
     * Trova la differenza positiva (records eccedenti)
     * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
     * Cancella tutti i records non più presenti nella categoria
     */
    @SuppressWarnings("unchecked")
    protected void doInit() {
        long inizio = System.currentTimeMillis();
        String nomeCategoria = "";
        ArrayList<Long> listaTotaleCategoria;
        ArrayList<Long> listaEsistentiDataBase;
        ArrayList<Long> listaMancanti;
        ArrayList<Long> listaEccedenti;

        // Il ciclo necessita del login come bot per il funzionamento normale
        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
        if (!LibBio.checkLoggin()) {
            return;
        }// end of if cycle

        // seleziona la categoria normale o di debug
        if (Pref.getBool(CostBio.USA_DEBUG, true)) {
            nomeCategoria = TAG_CAT_DEBUG;
        } else {
            nomeCategoria = TAG_BIO;
        }// fine del blocco if-else

        // carica la categoria
        listaTotaleCategoria = Api.leggeCatLong(nomeCategoria);
        Log.setInfo("categoria", "Letti e caricati in memoria i pageids di " + LibNum.format(listaTotaleCategoria.size()) + " pagine della categoria '" + nomeCategoria + "' in " + LibTime.difText(inizio));

        // recupera la lista dei records esistenti nel database
        listaEsistentiDataBase = Bio.findAllPageid();

        // elabora le liste delle differenze per la sincronizzazione
        listaMancanti = LibWiki.delta(listaTotaleCategoria, listaEsistentiDataBase);
        listaEccedenti = LibWiki.delta(listaEsistentiDataBase, listaTotaleCategoria);

        // Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
        new CicloNew(listaMancanti);

        // Cancella tutti i records non più presenti nella categoria
        new CicloDelete(listaEccedenti);

        // Aggiorna tutti i records esistenti
        new CicloUpdate();
    }// end of method


    /**
     * Esegue una serie di RequestWikiReadMultiPages a blocchi di PAGES_PER_REQUEST per volta
     * Esegue la RequestWikiReadMultiPages (tramite Api)
     * Crea le PAGES_PER_REQUEST Pages ricevute
     * Per ogni page crea o modifica il records corrispondente con lo stesso pageid
     *
     * @param listaVociDaScaricare elenco (pageids) delle pagine mancanti o modificate, da scaricare
     */
    protected int downloadPagine(ArrayList<Long> listaVociDaScaricare) {
        int numVociRegistrate = 0;
        int numVociDaScaricare = 0;
        int vociPerBlocco;
        ArrayList<Long> bloccoPageids;
        int iniBlocco;
        int endBlocco;
        int numCicli;

        if (listaVociDaScaricare != null) {
            numVociDaScaricare = listaVociDaScaricare.size();
        }// fine del blocco if

        if (numVociDaScaricare < 1) {
            return 0;
        }// end of if cycle

        if (numVociDaScaricare > 0) {
//            vociPerBlocco = Math.min(numVociDaScaricare, PAGES_PER_REQUEST);



            numCicli = LibArray.numCicli(listaVociDaScaricare.size(), dimBlocco());

            for (int k = 0; k < numCicli; k++) {
                bloccoPageids = LibArray.estraeSublistaLong(listaVociDaScaricare, dimBlocco(), k);

//                numVociRegistrate = downloadPagine(listaVociDaScaricare);
                numVociRegistrate += new DownloadPages(bloccoPageids).getNumVociRegistrate();

//                mappaVoci = downloadSingoloBlocco(bloccoPageids);
//                if (mappaVoci != null) {
//                    numVociModificate += mappaVoci.get(CostBio.KEY_MAPPA_MODIFICATE);
//                    numVociUploadate += mappaVoci.get(CostBio.KEY_MAPPA_UPLOADATE);
//                }// end of if cycle
            }// end of for cycle



//            for (int k = 0; k < numVociDaScaricare; k = k + vociPerBlocco) {
//                iniBlocco = k * vociPerBlocco;
//                endBlocco = iniBlocco + vociPerBlocco;
//                bloccoPageids = new ArrayList<Long>(listaVociDaScaricare.subList(iniBlocco, endBlocco));
//                numVociRegistrate += new DownloadPages(bloccoPageids).getNumVociRegistrate();
//            }// end of for cycle

        }// end of if cycle

        return numVociRegistrate;
    }// end of method

    /**
     * Divide il numero di voci da aggiornare in blocchi di 500 (50 se non flaggato come bot)
     */
    protected int dimBlocco() {
        int dimBlocco = 0;

        if (LibBio.isLoggatoBot()) {
            dimBlocco = Pref.getInt(CostBio.NUM_PAGEIDS_REQUEST, 500);
        } else {
            if (Pref.getBool(CostBio.USA_CICLI_ANCHE_SENZA_BOT)) {
                dimBlocco = 50;
            } else {
                Log.setDebug("bioCicloUpdate", "Ciclo interrotto. Non sei loggato come bot ed il flag usaCicliAncheSenzaBot è false");
                return 0;
            }// end of if/else cycle
        }// end of if/else cycle

        return dimBlocco;
    }// end of method

}// end of class

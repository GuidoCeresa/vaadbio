package it.algos.vaadbio.ciclo;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.AttivitaService;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.download.DownloadPages;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.NazionalitaService;
import it.algos.vaadbio.professione.ProfessioneService;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import javax.persistence.EntityManager;
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
    public final static String TAG_CAT_DEBUG = "Nati nel 1980";

    //--container statico per il database
    protected static EntityManager MANAGER = null;


    /**
     * Costruttore completo
     */
    public CicloDownload() {
        creaContainer();
        doInit();
    }// end of constructor

    /**
     * Crea l'Entity Manager
     * Crea il container di collegamento con il database
     */
    private void creaContainer() {
        MANAGER = EM.createEntityManager();
    }// end of method

    /**
     * Aggiorna la tavola delle attività
     * Aggiorna la tavola delle nazionalità
     * Aggiorna la tavola delle professioni
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
        long inizio;
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
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            nomeCategoria = TAG_CAT_DEBUG;
        } else {
            nomeCategoria = TAG_BIO;
        }// fine del blocco if-else

        // aggiorna la tavola delle attività
        if (!Pref.getBool(CostBio.USA_DEBUG, false)) {
            AttivitaService.download();
        }// fine del blocco if-else

        // aggiorna la tavola delle nazionalità
        if (!Pref.getBool(CostBio.USA_DEBUG, false)) {
            NazionalitaService.download();
        }// fine del blocco if-else

        // aggiorna la tavola delle professioni
        if (!Pref.getBool(CostBio.USA_DEBUG, false)) {
            ProfessioneService.download();
        }// fine del blocco if-else

        // carica la categoria
        inizio = System.currentTimeMillis();
        listaTotaleCategoria = Api.leggeCatLong(nomeCategoria);
        if (listaTotaleCategoria != null) {
            Log.debug("categoria", "Letti e caricati in memoria i pageids di " + LibNum.format(listaTotaleCategoria.size()) + " pagine della categoria '" + nomeCategoria + "' in " + LibTime.difText(inizio));
        } else {
            Log.debug("categoria", "Categoria '" + nomeCategoria + "' non trovata. Probabilmente manca il Login ");
        }// end of if/else cycle

        // recupera la lista dei records esistenti nel database
        listaEsistentiDataBase = Bio.findAllPageid();

        // elabora le liste delle differenze per la sincronizzazione
        listaMancanti = LibWiki.delta(listaTotaleCategoria, listaEsistentiDataBase);

        // Cancella tutti i records non più presenti nella categoria
        // Solo se NON è in debug (la categoria sarebbe minima e cancellerebbe quasi tutto)
        if (!Pref.getBool(CostBio.USA_DEBUG, false)) {
            listaEccedenti = LibWiki.delta(listaEsistentiDataBase, listaTotaleCategoria);
            new CicloDelete(listaEccedenti);
        }// fine del blocco if-else

        // Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
        new CicloNew(listaMancanti);
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
        long inizio = System.currentTimeMillis();
        int numVociDaScaricare = 0;
        int numVociRegistrate = 0;
        ArrayList<Long> bloccoPageids;
        int dimBloccoLettura = dimBloccoPages();
        int numCicliLetturaPagine;
        long inizioCommit = 0;
        long fineCommit = 0;
        String mess;

        if (listaVociDaScaricare != null && listaVociDaScaricare.size() > 0) {
            numVociDaScaricare = listaVociDaScaricare.size();
            if (Pref.getBool(CostBio.USA_COMMIT_MULTI_RECORDS, false)) {
                numCicliLetturaPagine = LibArray.numCicli(listaVociDaScaricare.size(), dimBloccoLettura);
                for (int k = 0; k < numCicliLetturaPagine; k++) {
                    bloccoPageids = LibArray.estraeSublistaLong(listaVociDaScaricare, dimBloccoLettura, k);

                    MANAGER.getTransaction().begin();

                    numVociRegistrate += new DownloadPages(bloccoPageids, MANAGER).getNumVociRegistrate();

                    inizioCommit = System.currentTimeMillis();
                    MANAGER.getTransaction().commit();
                    fineCommit = System.currentTimeMillis();

                    if (Pref.getBool(CostBio.USA_LOG_DEBUG, false)) {
                        mess = "Commit unico blocco di " + LibNum.format(dimBloccoLettura);
                        mess += " Save " + LibNum.format(numVociRegistrate) + "/" + LibNum.format(numVociDaScaricare) + " voci";
                        mess += " in " + LibNum.format(fineCommit - inizioCommit) + " milliSec./" + LibTime.difText(inizio);
                        Log.debug("test", mess);
                    }// end of if cycle
                }// end of for cycle
            } else {
                numCicliLetturaPagine = LibArray.numCicli(listaVociDaScaricare.size(), dimBloccoLettura);

                for (int k = 0; k < numCicliLetturaPagine; k++) {
                    bloccoPageids = LibArray.estraeSublistaLong(listaVociDaScaricare, dimBloccoLettura, k);
                    numVociRegistrate += new DownloadPages(bloccoPageids, null).getNumVociRegistrate();
                }// end of for cycle
            }// end of if/else cycle
        }// end of if cycle

        return numVociRegistrate;
    }// end of method


    /**
     * Numero di voci da scaricare dal server in blocco con un'unica API di lettura
     * Tipicamente 500 (50 se non flaggato come bot)
     */

    protected int dimBloccoPages() {
        int dimBlocco = 0;

        if (LibBio.isLoggatoBot()) {
            dimBlocco = Pref.getInt(CostBio.NUM_PAGEIDS_REQUEST, 500);
        } else {
            if (Pref.getBool(CostBio.USA_CICLI_ANCHE_SENZA_BOT)) {
                dimBlocco = 50;
            } else {
                Log.debug("bioCicloUpdate", "Ciclo interrotto. Non sei loggato come bot ed il flag usaCicliAncheSenzaBot è false");
                return 0;
            }// end of if/else cycle
        }// end of if/else cycle

        return dimBlocco;
    }// end of method

}// end of class

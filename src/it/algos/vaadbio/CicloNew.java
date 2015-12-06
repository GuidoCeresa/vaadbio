package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
 * Il ciclo necessita del login come bot per il funzionamento normale
 * <p>
 * Controlla il flag USA_LIMITE_DOWNLOAD
 * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare in totale (se USA_LIMITE_DOWNLOAD=true)
 * Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
 * <p>
 */
public class CicloNew {

    //--numero massimo accettato da mediawiki per le richieste multiple
    private final static int PAGES_PER_REQUEST = 500;

    //--chiavi mappa volori numerici passati come valore di ritorno multiplo
    private final static String KEY_MAPPA_REGISTRATE = "registrate";
    private final static String KEY_MAPPA_UPLOADATE = "uplodate";



    /**
     * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
     */
    public CicloNew(ArrayList<Long> listaMancanti) {
        downloadVociMancanti(listaMancanti);
    }// end of constructor


//    /**
//     * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti sul server e mancanti nel database
//     * <p>
//     * Legge la categoria BioBot
//     * Legge le voci Bio esistenti
//     * Trova la differenza negativa (records mancanti)
//     * Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
//     */
//    @SuppressWarnings("unchecked")
//    private void doInit(ArrayList<Long> listaMancanti) {
//        long inizio = System.currentTimeMillis();
//        String nomeCategoria = "";
//        ArrayList<Long> listaTotaleCategoria;
//        ArrayList<Long> listaEsistentiDataBase;
//        ArrayList<Long> listaMancanti;
//
//        // Il ciclo necessita del login come bot per il funzionamento normale
//        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
//        if (!LibBio.checkLoggin()) {
//            return;
//        }// end of if cycle
//
//        // seleziona la categoria normale o di debug
//        if (Pref.getBool(CostBio.USA_DEBUG, true)) {
//            nomeCategoria = TAG_CAT_DEBUG;
//        } else {
//            nomeCategoria = TAG_BIO;
//        }// fine del blocco if-else
//
//        // carica la categoria
//        listaTotaleCategoria = Api.leggeCatLong(nomeCategoria);
//        Log.setInfo("categoria", "Letti e caricati in memoria i pageids di " + LibNum.format(listaTotaleCategoria.size()) + " pagine della categoria '" + nomeCategoria + "' in " + LibTime.difText(inizio));
//
//        // recupera la lista dei records esistenti nel database
//        listaEsistentiDataBase = Bio.findAllPageid();
//
//        // elabora le liste delle differenze per la sincronizzazione
//        listaMancanti = LibWiki.delta(listaTotaleCategoria, listaEsistentiDataBase);
//
//        // Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
//        downloadVociMancanti(listaMancanti);
//    }// end of method


    /**
     * Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare in totale (se USA_LIMITE_DOWNLOAD=true)
     * Esegue una serie di Request a blocchi di PAGES_PER_REQUEST per volta
     *
     * @param listaMancanti elenco (pageids) delle pagine mancanti da scaricare
     */
    private void downloadVociMancanti(ArrayList<Long> listaMancanti) {
        long inizio = System.currentTimeMillis();
        int numVociRegistrate = 0;
        int numVociUploadate = 0;
        int vociMancanti = 0;
        int vociDaScaricare;
        int vociPerBlocco;
        ArrayList<Long> bloccoPageids;
        HashMap<String, Integer> mappaVoci;

        if (listaMancanti != null) {
            vociMancanti = listaMancanti.size();
        }// fine del blocco if

        if (vociMancanti < 1) {
            return;
        }// end of if cycle

        if (Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false)) {
            vociDaScaricare = Math.min(vociMancanti, Pref.getInt(CostBio.MAX_DOWNLOAD, 1000));
        } else {
            vociDaScaricare = vociMancanti;
        }// fine del blocco if-else

        if (vociDaScaricare > 0) {
            vociPerBlocco = Math.min(vociDaScaricare, PAGES_PER_REQUEST);
            for (int k = 0; k < vociDaScaricare; k = k + vociPerBlocco) {
                bloccoPageids = new ArrayList<Long>(listaMancanti.subList(k, k + vociPerBlocco));
                mappaVoci = downloadBlocco(bloccoPageids);
                if (mappaVoci != null) {
                    numVociRegistrate += mappaVoci.get(KEY_MAPPA_REGISTRATE);
                    numVociUploadate += mappaVoci.get(KEY_MAPPA_UPLOADATE);
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
            Log.setInfo("cicloNew", "Create " + LibNum.format(numVociRegistrate) + " nuove voci (di cui " + LibNum.format(numVociUploadate) + " uploadate) in " + LibTime.difText(inizio));
        }// fine del blocco if

    }// end of method


    /**
     * Esegue una RequestWikiReadMultiPages per un blocco di PAGES_PER_REQUEST tra quelle mancanti da scaricare
     * <p>
     * Esegue la RequestWikiReadMultiPages
     * Crea le PAGES_PER_REQUEST Pages ricevute
     * Per ogni page crea o modifica il records corrispondente con lo stesso pageid
     * Esegue il metodo Elabora, col flag di update specifico per il ciclo di Download
     * Esegue il metodo Update, se previsto dal flag USA_UPLOAD_DOWNLOADATA
     *
     * @param bloccoPageids elenco di pageids delle pagine da scaricare
     */
    public HashMap<String, Integer> downloadBlocco(ArrayList<Long> bloccoPageids) {
        HashMap<String, Integer> mappaVoci = null;
        int numVociRegistrate = 0;
        int numVociUploadate = 0;
        ArrayList<Page> pages;
        DownloadBio downloadBio;

        if (bloccoPageids != null && bloccoPageids.size() > 0) {
            mappaVoci = new HashMap<String, Integer>();
            pages = Api.leggePages(bloccoPageids);
            for (Page page : pages) {
                downloadBio = new DownloadBio(page);
                if (downloadBio.isNuova()) {
                    numVociRegistrate++;
                }// fine del blocco if
                if (downloadBio.isUploadata()) {
                    numVociUploadate++;
                }// fine del blocco if
            }// end of for cycle
        }// end of if cycle

        if (mappaVoci != null) {
            mappaVoci.put(KEY_MAPPA_REGISTRATE, numVociRegistrate);
            mappaVoci.put(KEY_MAPPA_UPLOADATE, numVociUploadate);
        }// end of if cycle

        return mappaVoci;
    }// end of method


}// end of class

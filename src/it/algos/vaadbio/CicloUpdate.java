package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaad.wiki.WrapTime;
import it.algos.vaad.wiki.request.RequestWikiTimestamp;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.lib.LibTime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
 * Il ciclo necessita del login come bot per il funzionamento normale
 * <p>
 * Legge le voci Bio esistenti
 * Controlla il flag USA_LIMITE_DOWNLOAD
 * Usa il numero massimo (MAX_DOWNLOAD) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
 * <p>
 * Divide il numero di voci totali esistenti nel database in blocchi di 5.000
 * Per ogni blocco, ordina i records di Bio secondo il campo ultimalettura (ascendente). Comincia dal più vecchio.
 * Legge per le voci del blocco il timestamp dell'ultima modifica effettuata
 * Seleziona le voci del blocco che sono state modificate dall'ultima lettura (timestamp > ultimalettura)
 * <p>
 * Esegue una serie di RequestWikiReadMultiPages a blocchi di PAGES_PER_REQUEST per volta
 * Crea le PAGES_PER_REQUEST Pages ricevute
 * Per ogni page aggiorna il record esistente
 * <p>
 * Esegue il metodo Elabora, col flag di update specifico per il ciclo di Download
 * Esegue il metodo Update, se previsto dal flag USA_UPLOAD_DOWNLOADATA
 * Aggiorna la property ultimalettura per tutti gli altri records che non sono stati modificati
 * Aggiorna la tavola Bio
 */
public class CicloUpdate {


    public CicloUpdate() {
        doUpdate();
    }// end of constructor


    /**
     * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
     * Il ciclo necessita del login come bot per il funzionamento normale
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
     * Legge le voci Bio esistenti sul database
     * <p>
     * Divide il numero di voci da controllare nel database in PAGES_PER_REQUEST blocchi
     * Recupera ed ordina PAGES_PER_REQUEST records di Bio secondo il campo ultimalettura (ascendente). Comincia dal più vecchio.
     * Esegue una RequestWikiTimestamp per creare una lista di WrapTime
     * Estrae una lista delle voci che sono state effettivamente modificate dall'ultima lettura (timestamp > ultimalettura)
     * Accumula le voci effettivamente modificate per ogni ciclo
     * Scarica la lista globale delle voci effettivamente modificate
     * <p>
     * //     * Per tutta la lista di voci effettivamente modificate, esegue una serie di RequestWikiReadMultiPages a blocchi di PAGES_PER_REQUEST per volta
     * //     * Crea le PAGES_PER_REQUEST Pages ricevute
     * <p>
     * //     * Per ogni page aggiorna il record esistente
     * //     * Esegue il metodo Elabora, col flag di update specifico per il ciclo di Download
     * //     * Esegue il metodo Update, se previsto dal flag USA_UPLOAD_DOWNLOADATA
     * //     * Aggiorna la property ultimalettura per tutti gli altri records che non sono stati modificati
     * //     * Aggiorna la tavola Bio
     */
    @SuppressWarnings("all")
    private void doUpdate() {
        long inizio = System.currentTimeMillis();
        ArrayList<Long> listaBloccoDaControllare = null;
        ArrayList<Long> listaBloccoModificate = null;
        ArrayList<Long> listaAllVociModificate = null;
        int vociPerBlocco = this.dimBlocco();
        ArrayList<WrapTime> listaWrapTimeBlocco;
        ArrayList<WrapTime> listaWrapTime;
        int numVociDaControllare = 50000;
        HashMap<String, Integer> mappaInfoVoci = null;
        int numVociModificate = 0;
        int numVociUploadate = 0;
        int numCicli;
        int offset;

        // Il ciclo necessita del login come bot per il funzionamento normale
        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
        if (!LibBio.checkLoggin()) {
            Log.setDebug("bioCicloUpdate", "Ciclo interrotto. Non sei loggato come bot ed il flag usaCicliAncheSenzaBot è false");
            return;
        }// end of if cycle

        // Recupera il numero globale di voci da controllare; tutto il database oppure un tot stabilito nelle preferenze
        if (Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, true)) {
            numVociDaControllare = Pref.getInt(CostBio.MAX_DOWNLOAD, 50000);
        } else {
            numVociDaControllare = Bio.count();
        }// end of if/else cycle

        numCicli = LibArray.numCicli(numVociDaControllare, vociPerBlocco);
        vociPerBlocco = Math.min(numVociDaControllare, vociPerBlocco);
        for (int k = 0; k < numCicli; k++) {
            offset = vociPerBlocco * k;
            if (k == (numCicli - 1)) {
                vociPerBlocco = numVociDaControllare - (vociPerBlocco * k);
            }// end of if cycle

            //--Lista (pageids) di un blocco ordinato di records del database
            listaBloccoDaControllare = Bio.findAllPageid(vociPerBlocco, offset);

            //--Esegue una RequestWikiTimestamp per creare una lista di WrapTime
            listaWrapTimeBlocco = getWrapTimeBlocco(listaBloccoDaControllare);

            //--Crea per questo blocco la lista (pageids) delle voci effettivamente modificate sul server wikipedia dall'ultimo controllo
            listaBloccoModificate = checkWrapTime(listaWrapTimeBlocco);

            //--Accumula la lista (pageids) di tutte le voci effettivamente modificate sul server wikipedia dall'ultimo controllo
            listaAllVociModificate = LibArray.somma(listaAllVociModificate, listaBloccoModificate);
        }// end of for cycle

        //--Aggiorna le voci della lista (pageids)
        mappaInfoVoci = downloadVociMancanti(listaAllVociModificate);

        //--Informazioni per il log
        if (mappaInfoVoci != null) {
            numVociModificate = mappaInfoVoci.get(CicloDownload.KEY_MAPPA_MODIFICATE);
            numVociUploadate = mappaInfoVoci.get(CicloDownload.KEY_MAPPA_UPLOADATE);
            Log.setInfo("update", "Controllate " + LibNum.format(numVociDaControllare) + " voci (di cui " + LibNum.format(numVociModificate) + " modificate e " + LibNum.format(numVociUploadate) + " uploadate) in " + LibTime.difText(inizio));
        }// end of if cycle

    }// end of method

    /**
     * Divide il numero di voci da aggiornare in blocchi di 500 (50 se non flaggato come bot)
     */

    private int dimBlocco() {
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


    /**
     * Esegue una RequestWikiTimestamp per creare una lista di WrapTime
     *
     * @param listaBloccoDaControllare lista (pageids) delle pagine da controllare
     * @return lista di wrapper WrapTime
     */
    private ArrayList<WrapTime> getWrapTimeBlocco(ArrayList<Long> listaBloccoDaControllare) {
        ArrayList<WrapTime> listaWrapTime = null;
        RequestWikiTimestamp request;

        if (listaBloccoDaControllare != null && listaBloccoDaControllare.size() > 0) {
            request = new RequestWikiTimestamp(listaBloccoDaControllare);
            listaWrapTime = request.getListaWrapTime();
        }// fine del blocco if

        return listaWrapTime;
    }// end of method

    /**
     * Elabora una lista (pageids) di voci effettivamente modificate sul server wikipedia dall'ultimo controllo
     * <p>
     * Recupera dalla lisa wrapper, una lista di soli pageids per la query
     * Recupera una lista di pageids/ultimaLettura dal database (con una query)
     * Spazzola la lista ricevuta
     * Confronta per ogni voce se è stata effettivamente modificata dall'ultima lettura (timestamp > ultimalettura)
     * Aggiunge alla lista in uscita solo se è stata modificata
     *
     * @param listaWrapTime lista di wrapper WrapTime
     * @return lista di pagine effettivamente mofificate
     */
    private ArrayList<Long> checkWrapTime(ArrayList<WrapTime> listaWrapTime) {
        ArrayList<Long> listaBloccoModificate = null;
        ArrayList<Long> listaPageids = null;
        String listaPageidsText = "";
        String tag = " or";
        ArrayList listaUltimaLettura = null;
        long pageid;
        Timestamp ultimalettura;
        Timestamp timestamp;
        HashMap<Long, WrapTime> mappaWrapTime;
        Bio bio;

        if (listaWrapTime != null && listaWrapTime.size() > 0) {
            listaBloccoModificate = new ArrayList<Long>();

            listaPageids = new ArrayList<Long>();
            mappaWrapTime = new HashMap<Long, WrapTime>();
            for (WrapTime wrap : listaWrapTime) {
                pageid = wrap.getPageid();
                mappaWrapTime.put(pageid, wrap);
                listaPageids.add(pageid);
            }// end of for cycle

            for (Long pageidTmp : listaPageids) {
                listaPageidsText += " bio.pageid=" + pageidTmp + tag;
            }// end of for cycle
            listaPageidsText = LibText.levaCoda(listaPageidsText, tag);

            listaUltimaLettura = LibBio.queryFind("select bio.pageid,bio.ultimaLettura,bio.title from Bio bio where " + listaPageidsText);
            for (int k = 0; k < listaUltimaLettura.size(); k++) {
                pageid = (Long) ((Object[]) listaUltimaLettura.get(k))[0];

                if (mappaWrapTime.get(pageid) != null) {
                    timestamp = mappaWrapTime.get(pageid).getTimestamp();
                    ultimalettura = (Timestamp) ((Object[]) listaUltimaLettura.get(k))[1];

                    if (timestamp.getTime() > ultimalettura.getTime()) {
                        listaBloccoModificate.add(pageid);
                    } else {
                        bio = Bio.findByPageid(pageid);
                        bio.setUltimaLettura(LibTime.adesso());
                        bio.save();
                    }// end of if/else cycle

                }// end of if cycle
            }// end of for cycle

        }// fine del blocco if

        return listaBloccoModificate;
    }// end of method


    /**
     * Scarica la lista di voci mancanti dal server e aggiorna i records di Bio
     * Esegue una serie di Request a blocchi di PAGES_PER_REQUEST per volta
     *
     * @param listaAllVociModificate elenco di pageids delle pagine da scaricare
     * @return info per il log
     */
    public HashMap<String, Integer> downloadVociMancanti(ArrayList<Long> listaAllVociModificate) {
        HashMap<String, Integer> mappaVoci = null;
        ArrayList<Long> bloccoPageids;
        int numVociModificate = 0;
        int numVociUploadate = 0;
        int numCicli;

        if (listaAllVociModificate != null && listaAllVociModificate.size() > 0) {
            numCicli = LibArray.numCicli(listaAllVociModificate.size(), dimBlocco());

            for (int k = 0; k < numCicli; k++) {
                bloccoPageids = LibArray.estraeSublistaLong(listaAllVociModificate, dimBlocco(), k);
                mappaVoci = downloadSingoloBlocco(bloccoPageids);
                if (mappaVoci != null) {
                    numVociModificate += mappaVoci.get(CicloDownload.KEY_MAPPA_MODIFICATE);
                    numVociUploadate += mappaVoci.get(CicloDownload.KEY_MAPPA_UPLOADATE);
                }// end of if cycle
            }// end of for cycle

        }// end of if cycle

        if (mappaVoci != null) {
            mappaVoci.put(CicloDownload.KEY_MAPPA_MODIFICATE, numVociModificate);
            mappaVoci.put(CicloDownload.KEY_MAPPA_UPLOADATE, numVociUploadate);
        }// end of if cycle

        return mappaVoci;
    }// end of method

    /**
     * Esegue una RequestWikiReadMultiPages per un blocco di PAGES_PER_REQUEST tra quelle mancanti da scaricare
     * <p>
     * Esegue la RequestWikiReadMultiPages (tramite Api)
     * Crea le PAGES_PER_REQUEST Pages ricevute
     * Per ogni page crea o modifica il records corrispondente con lo stesso pageid
     * Esegue il metodo Elabora, col flag di update specifico per il ciclo di Download
     * Esegue il metodo Update, se previsto dal flag USA_UPLOAD_DOWNLOADATA
     *
     * @param listaAllVociModificate elenco di pageids delle pagine da scaricare
     */
    public HashMap<String, Integer> downloadSingoloBlocco(ArrayList<Long> listaAllVociModificate) {
        HashMap<String, Integer> mappaVoci = null;
        int numVociModificate = 0;
        int numVociUploadate = 0;
        ArrayList<Page> pages;
        DownloadBio downloadBio;

        if (listaAllVociModificate != null && listaAllVociModificate.size() > 0) {
            mappaVoci = new HashMap<String, Integer>();
            pages = Api.leggePages(listaAllVociModificate);
            for (Page page : pages) {
                downloadBio = new DownloadBio(page, true);
                numVociModificate++;
                if (downloadBio.isUploadata()) {
                    numVociUploadate++;
                }// fine del blocco if
            }// end of for cycle
        }// end of if cycle

        if (mappaVoci != null) {
            mappaVoci.put(CicloDownload.KEY_MAPPA_MODIFICATE, numVociModificate);
            mappaVoci.put(CicloDownload.KEY_MAPPA_UPLOADATE, numVociUploadate);
        }// end of if cycle

        return mappaVoci;
    }// end of method


}// end of class

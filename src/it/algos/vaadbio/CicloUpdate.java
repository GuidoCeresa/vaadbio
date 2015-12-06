package it.algos.vaadbio;

import it.algos.vaad.wiki.WrapTime;
import it.algos.vaad.wiki.request.RequestWikiTimestamp;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 23 nov 2015.
 * <p>
 * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
 * Il ciclo necessita del login come bot per il funzionamento normale
 * <p>
 * Controlla il flag USA_LIMITE_DOWNLOAD
 * Usa il numero massimo (MAX_DOWNLOAD) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
 * Ordina i records di Bio secondo il campo ultimalettura (ascendente)
 * Legge per MAX_DOWNLOAD voci il timestamp dell'ultima modifica effettuata
 * Seleziona le voci (tra le MAX_DOWNLOAD) che sono state modificate dall'ultima lettura (timestamp > ultimalettura)
 * Legge le voci modificate ed aggiorna i records esistenti di Bio
 * Aggiorna la property ultimalettura per tutti gli altri records (dei MAX_DOWNLOAD del ciclo)
 * Controlla il flag USA_ELABORA
 * Elabora le informazioni dopo che una pagina è stata scaricata/aggiornata dal server
 * Aggiorna la tavola Bio
 */
public class CicloUpdate {


    public CicloUpdate() {
        update();
    }// end of constructor


    /**
     * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
     * Il ciclo necessita del login come bot per il funzionamento normale
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
     * Divide il numero di voci da aggiornare in blocchi di 500 (50 se non flaggato come bot)
     * Per ogni blocco, ordina i records di Bio secondo il campo ultimalettura (ascendente). Comincia dal più vecchio.
     * Legge per le voci del blocco il timestamp dell'ultima modifica effettuata
     * Seleziona le voci del blocco che sono state modificate dall'ultima lettura (timestamp > ultimalettura)
     * Legge le voci modificate ed aggiorna i records esistenti di Bio
     * Aggiorna la property ultimalettura per tutti gli altri records del blocco
     * Controlla il flag USA_ELABORA
     * Elabora le informazioni dopo che una pagina è stata scaricata/aggiornata dal server
     * Aggiorna la tavola Bio
     */
    @SuppressWarnings("all")
    private void update() {
        long inizio = System.currentTimeMillis();
        ArrayList<Long> listaVociDaControllare = null;
        int dimBlocco = this.dimBlocco();
        int numVociDaControllare = 50000;

        // Il ciclo necessita del login come bot per il funzionamento normale
        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
        if (!LibBio.checkLoggin()) {
            Log.setDebug("bioCicloUpdate", "Ciclo interrotto. Non sei loggato come bot ed il flag usaCicliAncheSenzaBot è false");
            return;
        }// end of if cycle

        // Crea la lista di voci (pageid) esistenti nel database, da controllare
        if (Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, true)) {
            numVociDaControllare = Pref.getInt(CostBio.MAX_DOWNLOAD, 50000);
        } else {
            numVociDaControllare = Bio.count();
        }// end of if/else cycle

        for (int k = 0; k < numVociDaControllare; k += dimBlocco) {
            listaVociDaControllare = Bio.findAllPageid(dimBlocco, k);
            updateBlocco(listaVociDaControllare);
        }// end of for cycle

        // Crea la lista delle voci effettivamente modificate sul server wikipedia dall'ultimo controllo
//        vociModificate(listaVociDaControllare, inizio);

        Log.setInfo("update", "Controllate " + "27" + " nuove voci (di cui " + "45" + " uploadate) in " + LibTime.difText(inizio));
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
     * Update blocco di pagine (pageids)
     * Esegue una singola QueryTimestamp e recupera una lista di pageids e timestamp
     *
     * @param listaBlocco lista (pageids) delle pagine da controllare
     * @return numero delle pagine effettivamente modificate
     */
    private HashMap<String, Integer> updateBlocco(ArrayList listaBlocco) {
        HashMap<String, Integer> mappa;
        int vociEsaminate = 0;
        int vociModificate = 0;
        int vociCancellate = 0;
        RequestWikiTimestamp query;
        ArrayList<WrapTime> listaWrapTime;
        ArrayList<WrapTime> listaWrapTimeMissing;

        if (listaBlocco != null && listaBlocco.size() > 0) {
            vociEsaminate = listaBlocco.size();
            query = new RequestWikiTimestamp(listaBlocco);
            listaWrapTime = query.getListaWrapTime();
            listaWrapTimeMissing = query.getListaWrapTimeMissing();

            if (listaWrapTime != null && listaWrapTime.size() > 0) {
                vociModificate = updateListaBlocco(listaWrapTime);
            }// fine del blocco if

            if (Pref.getBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, true) && listaWrapTimeMissing != null && listaWrapTimeMissing.size() > 0) {
//                vociCancellate = deleteVociMancanti(listaWrapTimeMissing);
            }// end of if cycle
        }// fine del blocco if

        mappa = new HashMap<String, Integer>();
//        mappa.put(KEY_VOCI_ESAMINATE, vociEsaminate);
//        mappa.put(KEY_VOCI_MODIFICATE, vociModificate);
//        mappa.put(KEY_VOCI_CANCELLATE, vociCancellate);
        return mappa;
    }// end of method

    /**
     * Update lista (WrapTime) blocco di pagine (pageids)
     *
     * @param listaWrapTime lista (wraptime) delle pagine da controllare
     * @return numero delle pagine effettivamente modificate
     */
    private int updateListaBlocco(ArrayList<WrapTime> listaWrapTime) {
        int vociModificate = 0;

        if (listaWrapTime != null) {
            for (WrapTime wrap : listaWrapTime) {
                if (updateSingolaPagina(wrap)) {
                    vociModificate++;
                }// fine del blocco if
            } // fine del ciclo for-each
        }// fine del blocco if

        return vociModificate;
    }// end of method


    /**
     * Update singola pagina (pageid)
     * <p>
     * Riceve il pageid ed il timestamp dell'ultima modifica sul server
     * Recupera dal database il record con il pageid specificato
     * Recupera il valore del parametro ultimalettura
     * Confronta timestamp con ultimalettura (entrambi valori long)
     * <p>
     * Se timestamp è maggiore a ultimalettura, scarica la pagina dal server wiki e aggiorna il database (salva le modifiche)
     * Se il timestamp è minore di ultimalettura NON scarica la pagina ed aggiorna solamente il valore di ultimalettura nel record
     * Elabora le informazioni dopo che una pagina è stata scaricata/aggiornata dal server
     *
     * @param wrap wrapper con pageid e timestamp dell'ultima modifica sul server
     * @return true se pagina scaricata e database aggiornatao
     */
    private boolean updateSingolaPagina(WrapTime wrap) {
        boolean status = false;
        Bio bio = null;
        long pageid;
        long ultimaLetturaDatabase = 0;
        long ultimaModificaServer = 0;

        pageid = wrap.getPageid();
        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
        }// fine del blocco if

        ultimaModificaServer = wrap.getTimestamp().getTime();
        if (bio != null) {
            ultimaLetturaDatabase = bio.getUltimaLettura().getTime();
        }// fine del blocco if

        if (ultimaModificaServer > 0 && ultimaLetturaDatabase > 0) {
            if (ultimaModificaServer > ultimaLetturaDatabase) {
                new DownloadBio(pageid);
                status = true;
            } else {
                bio.setUltimaLettura(LibTime.adesso());
                bio.save();
            }// fine del blocco if-else
        }// fine del blocco if

        return status;
    }// end of method

}// end of class

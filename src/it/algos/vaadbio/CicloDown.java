package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
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
 * Esegue un ciclo (UPGRADE) di controllo e aggiornamento di tutti i records esistenti nel database
 * <p>
 * Il ciclo viene chiamato da DaemonDownload (con frequenza giornaliera)
 * Il ciclo può essere invocato dal bottone 'Ciclo Down' nella tavola Bio
 * Il ciclo necessita del login come bot per il funzionamento normale
 * <p>
 * Controlla il flag USA_LIMITE_DOWNLOAD
 * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
 * Legge la categoria BioBot
 * Legge le voci Bio esistenti
 * Trova la differenza negativa (records mancanti)
 * Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
 * Trova la differenza positiva (records eccedenti)
 * Cancella tutti i records non più presenti nella categoria
 * <p>
 * Controlla il flag USA_LIMITE_DOWNLOAD_BIO_CICLO_UPDATE
 * Usa il numero massimo (MAX_DOWNLOAD_BIO_CICLO_UPDATE) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD_BIO_CICLO_UPDATE=true)
 * Ordina i records di BioWiki secondo il campo ultimalettura (ascendente)
 * Legge per MAX_DOWNLOAD_BIO_CICLO_UPDATE voci il timestamp dell'ultima modifica effettuata
 * Seleziona le voci (tra le MAX_DOWNLOAD_BIO_CICLO_UPDATE) che sono state modificate dall'ultima lettura (timestamp > ultimalettura)
 * Legge le voci modificate ed aggiorna i records esistenti di Bio
 * Aggiorna la property ultimalettura per tutti gli altri records (dei MAX_DOWNLOAD_BIO_CICLO_UPDATE del ciclo)
 * Controlla il flag USA_ELABORA
 * Elabora le informazioni dopo che una pagina è stata scaricata/aggiornata dal server
 * Aggiorna la tavola Bio
 */
public class CicloDown {

    private static final String TAG_BIO = "BioBot";
    private static final String TAG_CAT_DEBUG = "Nati nel 1420";

    public CicloDown() {
        newAndDelete();
        upgrade();
    }// end of constructor

    /**
     * Esegue un ciclo di sincronizzazione tra le pagine della categoria TAG_BIO ed i records della tavola Bio
     * <p>
     * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
     * Esegue un ciclo (UPGRADE) di controllo di records esistenti nel database e modificati sul server dall'ultimo controllo
     * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
     * <p>
     * Il ciclo viene chiamato da DaemonDownload (con frequenza giornaliera)
     * Il ciclo può essere invocato dal bottone 'Ciclo Down' nella tavola Bio
     * <p></p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
     * Legge la categoria BioBot
     * Legge le voci Bio esistenti
     * Trova la differenza negativa (records mancanti)
     * Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
     * Trova la differenza positiva (records eccedenti)
     * Cancella tutti i records non più presenti nella categoria
     */
    @SuppressWarnings("unchecked")
    private void newAndDelete() {
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

        // Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
        downloadVociMancanti(listaMancanti);

        // Cancella tutti i records non più presenti nella categoria
//        deleteRecordsEccedenti(listaEccedenti);//@todo rimettere
    }// end of method


    /**
     * Esegue un ciclo di sincronizzazione tra le pagine della categoria TAG_BIO ed i records della tavola Bio
     * Esegue un ciclo di controllo e aggiornamento dei records esistenti nel database
     * <p>
     * Il ciclo viene chiamato da DaemonBioCicloUpdate (con frequenza oraria)
     * Il ciclo può essere invocato dal bottone 'Update ciclo' nella tavola Wikibio
     * Il ciclo necessita del login come bot per il funzionamento normale,
     * oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD_BIO_CICLO_UPDATE
     * Usa il numero massimo (MAX_DOWNLOAD_BIO_CICLO_UPDATE) di voci da aggiornare ad ogni ciclo (se USA_LIMITE_DOWNLOAD_BIO_CICLO_UPDATE=true)
     * Ordina i records di BioWiki secondo il campo ultimalettura (ascendente)
     * Legge per MAX_DOWNLOAD_BIO_CICLO_UPDATE voci il timestamp dell'ultima modifica effettuata
     * Seleziona le voci (tra le MAX_DOWNLOAD_BIO_CICLO_UPDATE) che sono state modificate dall'ultima lettura (timestamp > ultimalettura)
     * Legge le voci modificate ed aggiorna i records esistenti di Wikibio
     * Aggiorna la property ultimalettura per tutti gli altri records (dei MAX_DOWNLOAD_BIO_CICLO_UPDATE del ciclo)
     */
    @SuppressWarnings("all")
    private void upgrade() {
//        long inizio = System.currentTimeMillis();
//        ArrayList<Long> listaVociDaControllare;
//
//        // Il ciclo necessita del login come bot per il funzionamento normale
//        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
//        if (!LibBio.checkLoggin()) {
//            Log.setDebug("bioCicloUpdate", "Ciclo interrotto. Non sei loggato come bot ed il flag usaCicliAncheSenzaBot è false");
//            return;
//        }// end of if cycle
//
//        // Crea la lista di voci (pageid) esistenti nel database, da controllare
//        listaVociDaControllare = vociDaControllare();
//
//        // Crea la lista delle voci effettivamente modificate sul server wikipedia dall'ultimo controllo
//        vociModificate(listaVociDaControllare, inizio);
    }// end of method

    /**
     * Scarica le voci mancanti dal server e crea i nuovi records di Bio
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare ad ogni ciclo (se USA_LIMITE_DOWNLOAD=true)
     * Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
     *
     * @param listaMancanti elenco di pageid delle pagine da scaricare
     */
    private void downloadVociMancanti(ArrayList<Long> listaMancanti) {
        long inizio = System.currentTimeMillis();
        Voce voce;
        int numVociRegistrate = 0;
        int numVociUploadate = 0;
        int vociMancanti = 0;
        int vociDaScaricare = 0;

        if (listaMancanti != null) {
            vociMancanti = listaMancanti.size();
        }// fine del blocco if

        if (vociMancanti > 0) {
            if (Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false)) {
                vociDaScaricare = Math.min(vociMancanti, Pref.getInt(CostBio.MAX_DOWNLOAD, 100));
            } else {
                vociDaScaricare = vociMancanti;
            }// fine del blocco if-else

            for (int k = 0; k < vociDaScaricare; k++) {
                voce = download(listaMancanti.get(k));
                if (voce == Voce.trovataCorretta) {
                    numVociRegistrate++;
                }// fine del blocco if
                if (voce == Voce.uploadata) {
                    numVociRegistrate++;
                    numVociUploadate++;
                }// fine del blocco if
            } // fine del ciclo for

            if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
                Log.setInfo("bioCicloNew", "Create " + LibNum.format(numVociRegistrate) + " nuove voci (di cui " + LibNum.format(numVociUploadate) + " uploadate) in " + LibTime.difText(inizio));
            }// fine del blocco if

        }// fine del blocco if

    }// end of method


    /**
     * Legge dal server wiki
     * <p>
     * Crea un nuovo record (solo se il pageid non esiste già)
     * Modifica il record esistente con lo stesso pageid
     * Registra il record Bio
     * Regola il flag temporale ultimaLettura
     * Azzera il flag temporale ultimaElaborazione
     *
     * @param pageId della pagina
     */
    public Voce download(long pageId) {
        Voce voce = Voce.nonTrovata;
        DownloadBio downloadBio = new DownloadBio(pageId);
        Bio bio;

        if (downloadBio.isStatus()) {
            voce = Voce.trovataCorretta;
            bio = downloadBio.getBio();
            new ElaboraBio(bio);
        }// fine del blocco if-else

        //--se è attivo il flag ed i template sono diversi, parte il ciclo di aggiornamento
        if (Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false)) {
//            new ElaboraOriginale(pageId);
//            //--se i template sono diversi, parte il ciclo di aggiornamento
//            if (LibBio.isTemplateDiversi(pageId)) {
//                new UploadBio(pageId);
//                new DownloadBio(pageId);
//                voce = Voce.uploadata;
//            }// end of if cycle
        }// end of if cycle

        return voce;
    }// end of method


    private enum Voce {
        nonTrovata, trovataCorretta, uploadata
    }// end of inner enumeration

}// end of class

package it.algos.vaadbio.lib;

/**
 * Created by gac on 17 set 2015.
 * .
 */
public abstract class CostBio {


    // generalissime
    public final static String SPAZIO = " ";
    public final static String VUOTO = "";
    public final static String A_CAPO = "\n";
    public final static String ASTERISCO = "*";
    public final static String MSG = "Messaggio di controllo";
    public final static String PUNTI = "...";
    public final static String TAG_NATO_CRONO = "n." + SPAZIO;
    public final static String TAG_MORTO_CRONO = "†" + SPAZIO;
    public final static String TAG_PARENTESI_INI = SPAZIO + "(";
    public final static String TAG_PARENTESI_END = ")";
    public final static String TAG_SEPARATORE = SPAZIO + "-" + SPAZIO;

    // pagine wiki
    public final static String DISAMBIGUA = "{{disambigua}}";
    public final static String REDIRECT = "#REDIRECT";
    public final static String REDIRECT_B = "#redirect";
    // generali
    public final static String USA_DEBUG = "usaDebug";
    public final static String USA_DIALOGHI_CONFERMA = "usaDialoghiConferma";
    public final static String USA_CICLI_ANCHE_SENZA_BOT = "usaCicliAncheSenzaBot";
    public final static String USA_LOG_DEBUG = "usaLogDebug";
    public final static String USA_COMMIT_MULTI_RECORDS = "usaCommitMultiRecords";
    public final static String NUM_RECORDS_COMMIT = "numRecordsCommit";

    // daemons
    public final static String USA_CRONO_DOWNLOAD = "usaCronoDownload";
    public final static String USA_CRONO_ELABORA = "usaCronoElabora";
    public final static String USA_LOG_DAEMONS = "usaLogDaemons";


    // ciclo download
    public final static String USA_LIMITE_DOWNLOAD = "usaLimiteDownload";
    public final static String MAX_DOWNLOAD = "maxDownload";
    public final static String USA_LOG_DOWNLOAD = "usaLogDownload";
    public final static String USA_UPLOAD_DOWNLOADATA = "usaUploadDownloadata";
    public final static String USA_CANCELLA_VOCE_MANCANTE = "usaCancellaVoceMancante";
    public final static String NUM_PAGEIDS_REQUEST = "numPageidesRequest";


    // ciclo elabora
    public final static String USA_LIMITE_ELABORA = "usaLimiteElabora";
    public final static String MAX_ELABORA = "maxElabora";
    public final static String USA_LOG_ELABORA = "usaLogElabora";
    public final static String USA_UPLOAD_ELABORATA = "usaUploadElaborata";
    public final static String USA_LOG_UPLOAD_ELABORATA = "usaLogUploadElaborata";

    // liste
    public final static String USA_FOOTER_PORTALE_CRONO = "usaFooterPortaleCrono";
    public final static String USA_FOOTER_CATEGORIE_CRONO = "usaFooterCategorieCrono";
    public final static String USA_TOC_INDICE_CRONO = "usaTocIndiceCrono";
    public final static String USA_RITORNO_CRONO = "usaRitornoCrono";
    public final static String TEMPLATE_AVVISO_CRONO = "templateAvvisoCrono";
    public final static String USA_BODY_RIGHE_MULTIPLE_CRONO = "usaBodyRigheMultipleCrono";
    public final static String REGISTRA_SOLO_MODIFICHE_SOSTANZIALI_CRONO = "usaEditSoloVoceDiversaCrono";


    //--chiavi mappa volori numerici passati come valore di ritorno multiplo
    public final static String KEY_MAPPA_REGISTRATE = "registrate";
    public final static String KEY_MAPPA_MODIFICATE = "modificate";
    public final static String KEY_MAPPA_UPLOADATE = "uplodate";


    //--chiavi mappa costruzione giorni
    public final static String PRIMO_GIORNO_MESE = "1º";
    public final static String KEY_MAPPA_GIORNI_MESE_NUMERO = "meseNumero";
    public final static String KEY_MAPPA_GIORNI_MESE_TESTO = "meseTesto";
    public final static String KEY_MAPPA_GIORNI_NORMALE = "normale";
    public final static String KEY_MAPPA_GIORNI_BISESTILE = "bisestile";
    public final static String KEY_MAPPA_GIORNI_NOME = "nome";
    public final static String KEY_MAPPA_GIORNI_TITOLO = "titolo";


    public static final String STAT_DATA_ULTIMA_SINTESI = "ultimaSintesi";
    public static final String STAT_NUM_VOCI = "numeroVociGestite";
    public static final String STAT_NUM_GIORNI = "numeroGiorniGestiti";
    public static final String STAT_NUM_ANNI = "numeroAnniGestiti";
    public static final String STAT_NUM_ATTIVITA = "numeroAttivitaGestite";
    public static final String STAT_NUM_NAZIONALITA = "numeroNazionalitaGestite";
    public static final String STAT_GIORNI_ATTESA = "giorniAttesa";

}// end of static abstract class

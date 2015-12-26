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

    // generali
    public final static String USA_DEBUG = "usaDebug";
    public final static String USA_DIALOGHI_CONFERMA = "usaDialoghiConferma";
    public final static String USA_CICLI_ANCHE_SENZA_BOT = "usaCicliAncheSenzaBot";

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

}// end of static abstract class

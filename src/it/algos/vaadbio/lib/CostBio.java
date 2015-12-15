package it.algos.vaadbio.lib;

/**
 * Created by gac on 17 set 2015.
 * .
 */
public abstract class CostBio {


    // generalissime
    public static final String SPAZIO = " ";

    // generali
    public static final String USA_DEBUG = "usaDebug";
    public static final String USA_DIALOGHI_CONFERMA = "usaDialoghiConferma";
    public static final String USA_CICLI_ANCHE_SENZA_BOT = "usaCicliAncheSenzaBot";

    // daemons
    public static final String USA_CRONO_DOWNLOAD = "usaCronoDownload";
    public static final String USA_CRONO_ELABORA = "usaCronoElabora";
    public static final String USA_LOG_DAEMONS = "usaLogDaemons";


    // ciclo download
    public static final String USA_LIMITE_DOWNLOAD = "usaLimiteDownload";
    public static final String MAX_DOWNLOAD = "maxDownload";
    public static final String USA_LOG_DOWNLOAD = "usaLogDownload";
    public static final String USA_UPLOAD_DOWNLOADATA = "usaUploadDownloadata";
    public static final String USA_CANCELLA_VOCE_MANCANTE = "usaCancellaVoceMancante";
    public static final String NUM_PAGEIDS_REQUEST = "numPageidesRequest";


    // ciclo elabora
    public static final String USA_LIMITE_ELABORA = "usaLimiteElabora";
    public static final String MAX_ELABORA = "maxElabora";
    public static final String USA_LOG_ELABORA = "usaLogElabora";
    public static final String USA_UPLOAD_ELABORATA = "usaUploadElaborata";
    public static final String USA_LOG_UPLOAD_ELABORATA = "usaLogUploadElaborata";


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

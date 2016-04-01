package it.algos.vaadbio.professione;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 27 mar 2016.
 * Legame tra i nomi delle attività (chiamate professioni perché la tavola esiste già) e la pagina wiki di riferimento
 * La tavola rispecchia esattamente il modulo su wikipedia (mirror), senza nessuna elaborazione
 */
public abstract class ProfessioneService {

    private final static String TITOLO_MODULO = "Modulo:Bio/Link attività";

    /**
     * Aggiorna i records leggendoli dalla pagina wiki
     * <p>
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Aggiunge al database i records mancanti
     */
    public static boolean download() {
        long inizio = System.currentTimeMillis();
        LinkedHashMap<String, Object> mappa;
        String secondi;
        String records;

        // Recupera la mappa dalla pagina wiki
        mappa = LibWiki.leggeMappaModulo(TITOLO_MODULO);

        // Aggiunge i records mancanti
        if (mappa == null) {
            return false;
        } else {
            for (Map.Entry<String, Object> elementoDellaMappa : mappa.entrySet()) {
                aggiungeRecord(elementoDellaMappa);
            }// end of for cycle

            if (Pref.getBool(CostBio.USA_LOG_DEBUG, false)) {
                secondi = LibTime.difText(inizio);
                records = LibNum.format(mappa.size());
                Log.setDebug("prof", "Aggiornati in " + secondi + " i " + records + " records di professione");
            }// fine del blocco if
            return true;
        }// end of if/else cycle
    } // fine del metodo statico

    /**
     * Aggiunge il record mancante
     */
    public static void aggiungeRecord(Map.Entry<String, Object> elementoDellaMappa) {
        String singolare;
        String pagina = CostBio.VUOTO;
        Professione professione;

        if (elementoDellaMappa != null) {
            singolare = elementoDellaMappa.getKey();

            if (elementoDellaMappa.getValue() instanceof String) {
                pagina = (String) elementoDellaMappa.getValue();
            }// end of if cycle

            if (!singolare.equals(CostBio.VUOTO)) {
                professione = Professione.findBySingolare(singolare);
                if (professione == null) {
                    professione = new Professione(CostBio.VUOTO, CostBio.VUOTO);
                }// fine del blocco if
                professione.setSingolare(singolare);
                professione.setPagina(pagina);
                professione.save();
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo statico

}// end of abstract static class

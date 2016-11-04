package it.algos.vaadbio.cognome;


import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Vector;

/**
 * Created by gac on 05 feb 2016.
 * Gestione dei cognomi
 */
public abstract class CognomeService {

    private static String[] TAG_INI_NUMERI = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private static String[] TAG_INI_CHAR = {"*", "&", "!", "&nbsp", ".", "(", ",", "?", "{", "[", "<", "-"};
    private static String[] TAG_INI_APICI = {"‘", "‛", "\'", "\"", "''"};


    /**
     * Cancella i records esistenti
     * Vengono creati nuovi records per i cognomi (unici) presenti nelle voci (bioGrails)
     */
    public static int crea() {
        int recordsCreati = 0;
        Vector vettore = null;
        EntityManager manager = EM.createEntityManager();

        vettore = Cognome.findMappa(manager);

        //--cancella i records esistenti
        cancellaCognomi();

        //--elabora i cognomi e li registra
        recordsCreati = spazzolaAllCognomiUnici(vettore, manager);

        return recordsCreati;
    }// fine del metodo


    /**
     * Cancella i records esistenti
     */
    private static void cancellaCognomi() {
        AQuery.delete(Cognome.class);
    }// fine del metodo

    /**
     * Elabora tutti i cognomi
     * Controlla la validità di ogni singolo cognome
     * Controlla che ci siano almeno n voci biografiche per il singolo cognome
     * Registra il record
     */
    private static int spazzolaAllCognomiUnici(Vector vettore, EntityManager manager) {
        int numCognomiRegistrati = 0;
        Object[] obj;
        int taglio = Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20);
        String cognomeTxt = "";
        long numVociBio = 0;

        for (int k = 0; k < vettore.size(); k++) {
            obj = (Object[])vettore.get(k);
            cognomeTxt=(String)obj[0];
            numVociBio=(long)obj[1];
            if (numVociBio>taglio) {
                if (creaSingolo(cognomeTxt, (int)numVociBio, manager)) {
                    numCognomiRegistrati++;
                }// end of if cycle
            }// end of if cycle
        }// endof for cycle

        return numCognomiRegistrati;
    }// fine del metodo


    /**
     * Controlla la validità del cognome
     * Calcola il numero di voci Bio esistenti per il cognome
     * Se supera il taglio TAGLIO_COGNOMI_ELENCO, registra il record
     */
    private static boolean creaSingolo(String cognomeTxt, int numVociBio, EntityManager manager) {
        boolean registrato = false;

        cognomeTxt = check(cognomeTxt);
        if (cognomeTxt.equals(CostBio.VUOTO)) {
            return false;
        }// end of if cycle

        try { // prova ad eseguire il codice
            Cognome.crea(cognomeTxt, numVociBio, manager);
            registrato = true;
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        return registrato;
    }// fine del metodo


    /**
     * Controllo di validità di un cognome <br>
     * Quarta regola: elimina parti iniziali con caratteri/prefissi non accettati -> LibBio.checkNome()
     * Quinta regola: elimina <ref>< finali e testo successivo
     * Elimina parti iniziali con caratteri/prefissi non accettati <br>
     * Elimina sempre il cognome esattamente uguale al tag-iniziale o al tag-all <br>
     * Elimina sempre il cognome che inizia col tag-iniziale <br>
     * Elimina il cognome che inizia col tag-all se il tag è seguito da spazio <br>
     *
     * @param cognomeIn cognome della persona
     */
    private static String check(String cognomeIn) {
        String cognomeOut;
        List<String> tagIniziale = LibBio.sumTag(TAG_INI_NUMERI, TAG_INI_CHAR, TAG_INI_APICI);

        for (String tag : tagIniziale) {
            if (cognomeIn.startsWith(tag)) {
                return CostBio.VUOTO;
            }// fine del blocco if
        }// end of for cycle

        //--quinta regola
        cognomeOut = LibBio.fixCampo(cognomeIn);

        //--per sicurezza in caso di nomi strani
        cognomeOut = LibText.primaMaiuscola(cognomeOut);

        return cognomeOut;
    }// fine del metodo

}// end of static class

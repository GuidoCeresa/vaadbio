package it.algos.vaadbio.cognome;


import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
        int recordsCreati=0;
        Vector vettore=null;
        EntityManager manager = EM.createEntityManager();

//        List<String> listaCognomiUniciCompleta;
        vettore= Cognome.findMappa(manager);

        //--cancella i records esistenti
        cancellaCognomi();

        //--recupera una lista 'grezza' di tutti i cognomi
//        listaCognomiUniciCompleta = creaListaCognomiCompleta();

        //--elabora i cognomi e li registra
//        recordsCreati = spazzolaAllCognomiUnici(listaCognomiUniciCompleta);

        return recordsCreati;
    }// fine del metodo


    /**
     * Cancella i records esistenti
     */
    private static void cancellaCognomi() {
        AQuery.delete(Cognome.class);
    }// fine del metodo

    /**
     * Recupera una lista 'grezza' di tutti i cognomi
     * Si considera il campo Bio.cognomeValido
     */
    private static ArrayList<String> creaListaCognomiCompleta() {
        return LibBio.queryFindDistinctStx("Bio", "cognomeValido");
    }// fine del metodo

    /**
     * Elabora tutti i cognomi
     * Controlla la validità di ogni singolo cognome
     * Controlla che ci siano almeno n voci biografiche per il singolo cognome
     * Registra il record
     */
    private static int spazzolaAllCognomiUnici(List<String> listaCognomiUniciCompleta) {
        int numCognomiRegistrati = 0;
        EntityManager manager = EM.createEntityManager();

//        for (String cognomeDaControllare : listaCognomiUniciCompleta) {
//            if (elaboraSingolo(cognomeDaControllare)) {
//                numCognomiRegistrati++;
//            }// end of if cycle
//        }// end of for cycle
//

        String cognomeDaControllare;
        for (int k = 0; k < 500; k++) {
            cognomeDaControllare = listaCognomiUniciCompleta.get(k);
            if (elaboraSingolo(cognomeDaControllare,manager)) {
                numCognomiRegistrati++;
            }// end of if cycle
        }// end of for cycle

        return numCognomiRegistrati;
    }// fine del metodo


    /**
     * Controlla la validità del cognome
     * Calcola il numero di voci Bio esistenti per il cognome
     * Se supera il taglio TAGLIO_COGNOMI_ELENCO, registra il record
     *
     * @param cognomeTxt cognome della persona
     */
    private static boolean elaboraSingolo(String cognomeTxt,EntityManager manager) {
        boolean registrato = false;
        int taglio = Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20);
        int numVociBio=0;

        cognomeTxt = check(cognomeTxt);

        if (cognomeTxt.equals(CostBio.VUOTO)) {
            return false;
        }// end of if cycle

//        numVociBio = Cognome.countBioCognome(cognomeTxt,manager);
        if (numVociBio >= taglio) {
            try { // prova ad eseguire il codice
                Cognome.crea(cognomeTxt, numVociBio,manager);
                registrato = true;
            } catch (Exception unErrore) { // intercetta l'errore
            }// fine del blocco try-catch
        }// end of if cycle

        return registrato;
    }// fine del metodo


    /**
     * Controllo di validità di un cognome <br>
     * Prima regola: elimina cognomi di un solo carattere
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

        // --prima regola
//        if (cognomeIn.length() < 2) {
//            return CostBio.VUOTO;
//        }// fine del blocco if

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

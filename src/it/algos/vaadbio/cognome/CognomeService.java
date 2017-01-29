package it.algos.vaadbio.cognome;


import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public static void crea() {
        EntityManager manager = EM.createEntityManager();
        manager.getTransaction().begin();

        //--cancella i records esistenti
        cancellaCognomi(manager);

        //--elabora i cognomi e li registra
        creaAllCognomiUnici(manager);

        try {
            manager.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            manager.getTransaction().rollback();
        }// fine del blocco try-catch
        manager.close();

    }// fine del metodo


    /**
     * Cancella i records esistenti
     */
    private static void cancellaCognomi(EntityManager manager) {
        Query query;
        String queryTxt = "update Bio bio set bio.cognomePunta=null";

        try { // prova ad eseguire il codice
            query = manager.createQuery(queryTxt);
            query.executeUpdate();
        } catch (Exception unErrore) { // intercetta l'errore
            Log.warning("cognomePunta", "Ancora da eliminare la property cognomePunta");
        }// fine del blocco try-catch

        AQuery.delete(Cognome.class, manager);
    }// fine del metodo

    /**
     * Elabora tutti i cognomi
     * Controlla la validità di ogni singolo cognome
     * Controlla che ci siano almeno n voci biografiche per il singolo cognome
     * Registra il record
     */
    private static void creaAllCognomiUnici(EntityManager manager) {
        int numCognomiRegistrati = 0;
        LinkedHashMap<String, Integer> mappa;
        int taglio = Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20);
        String cognomeTxt = "";
        long numVociBio = 0;

        long inizio = System.currentTimeMillis();
        mappa = Cognome.findMappa(manager, taglio);

        for (Map.Entry<String, Integer> elementoDellaMappa : mappa.entrySet()) {
            cognomeTxt = elementoDellaMappa.getKey();
            numVociBio = elementoDellaMappa.getValue();
            if (creaSingolo(cognomeTxt, numVociBio, manager)) {
                numCognomiRegistrati++;
            }// end of if cycle
        }// end of for cycle

        Log.debug("Cognomi", "Create " + LibNum.format(numCognomiRegistrati) + " pagine di cognomi in " + LibTime.difText(inizio));
    }// fine del metodo


    /**
     * Controlla la validità del cognome
     * (Ri)Calcola (eventualmente) il numero di voci Bio esistenti per il cognome
     * Se supera il taglio TAGLIO_COGNOMI_ELENCO, registra il record
     */
    private static boolean creaSingolo(String cognomeTxt, long numVociBioTmp, EntityManager manager) {
        boolean registrato = false;
        long numVociBio = 0;

        cognomeTxt = check(cognomeTxt);
        if (cognomeTxt.equals(CostBio.VUOTO)) {
            return false;
        }// end of if cycle

        // ricontrolla il numero di voci
        if (Pref.getBool(CostBio.USA_RICONTEGGIO_NOMI, false)) {
            numVociBio = AQuery.count(Bio.class, Bio_.cognomeValido, cognomeTxt, manager);
        } else {
            numVociBio = numVociBioTmp;
        }// end of if/else cycle

        try { // prova ad eseguire il codice
            Cognome.crea(cognomeTxt, (int) numVociBio, manager);
            registrato = true;
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        return registrato;
    }// fine del metodo


    /**
     * Controlla che ci siano almeno n voci biografiche per il singolo cognome
     */
    private static void elaboraCognomiValidi(Vector vettore, EntityManager manager) {
        int taglio = Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20);
        Object[] obj;
        String cognomeTxt = "";
        long numVociBio;

        for (int k = 0; k < vettore.size(); k++) {
            obj = (Object[]) vettore.get(k);
            cognomeTxt = (String) obj[0];
            numVociBio = (long) obj[1];
            if (numVociBio > taglio) {
                cognomeTxt = check(cognomeTxt);
                if (!cognomeTxt.equals(CostBio.VUOTO)) {
                    Cognome.crea(cognomeTxt, (int)numVociBio, manager);
                }// end of if cycle
            }// end of if cycle
        }// endof for cycle

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

        return cognomeOut;
    }// fine del metodo

}// end of static class

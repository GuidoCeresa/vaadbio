package it.algos.vaadbio.cognome;


import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
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
        AQuery.delete(Cognome.class, manager);
    }// fine del metodo

    /**
     * Elabora tutti i cognomi
     * Registra il record
     */
    private static void creaAllCognomiUnici(EntityManager manager) {
        Vector vettore = Cognome.findMappa(manager);
        elaboraCognomiValidi(vettore, manager);
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

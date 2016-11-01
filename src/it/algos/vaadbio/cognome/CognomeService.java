package it.algos.vaadbio.cognome;


import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gac on 05 feb 2016.
 * Gestione dei cognomi
 */
public abstract class CognomeService {

    private static String[] TAG_INI_NUMERI = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private static String[] TAG_INI_CHAR = {"*", "&", "!", "&nbsp", ".", "(", ",", "?", "{", "[", "<", "-"};
    private static String[] TAG_INI_APICI = {"‘", "‛", "\"", "''"};


    /**
     * Aggiunta nuovi records e modifica di quelli esistenti
     * Vengono creati nuovi records per i cognomi presenti nelle voci (bioGrails) che superano la soglia minima (TAGLIO_COGNOMI_ELENCO)
     * Vengono segnati come NON validi (non si possono cancellare) quelli che scendono al di sotto della soglia minima
     */
    public static void creaAggiorna() {
        List<String> listaCognomiUniciCompleta;
        List<String> listaCognomiUniciValidi;

        //--recupera una lista 'grezza' di tutti i cognomi
        listaCognomiUniciCompleta = creaListaCognomiCompleta();

        //--elimina tutto ciò che compare oltre al cognome
        listaCognomiUniciValidi = elaboraAllCognomiUnici(listaCognomiUniciCompleta);

        //--(ri)costruisce i records
        spazzolaAllCognomiUnici(listaCognomiUniciValidi);
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
     * Costruisce una lista di cognomi ''validi'
     * Dovrebbero essere già stati 'puliti' in Bio, ma meglio ripassarli
     */
    private static List<String> elaboraAllCognomiUnici(List<String> listaCognomiUniciCompleta) {
        ArrayList<String> listaCognomiUniciValidi = new ArrayList<>();
        String cognomeValido;

        //--costruisce una lista di cognomi 'validi'
        for (String cognomeDaControllare : listaCognomiUniciCompleta) {
            cognomeValido = controllaCognome(cognomeDaControllare);
            if (!cognomeValido.equals(CostBio.VUOTO)) {
                if (!listaCognomiUniciValidi.contains(cognomeValido)) {
                    listaCognomiUniciValidi.add(cognomeValido);
                }// fine del blocco if
            }// fine del blocco if
        }// end of for cycle

        return listaCognomiUniciValidi;
    }// fine del metodo


    /**
     * Controlla il singolo cognome
     * <p>
     * Quarta regola: elimina parti iniziali con caratteri/prefissi non accettati -> LibBio.checkNome()
     * Quinta regola: elimina <ref>< finali e testo successivo
     *
     * @param cognomeIn cognome della persona
     */
    private static String controllaCognome(String cognomeIn) {
        String cognomeOut = CostBio.VUOTO;

        // --quarta regola
        if (!checkNome(cognomeOut)) {
            cognomeOut = CostBio.VUOTO;
        }// fine del blocco if

        //--quinta regola
        cognomeOut = LibBio.fixCampo(cognomeOut);

        //--per sicurezza in caso di nomi strani
        cognomeOut = LibText.primaMaiuscola(cognomeOut);

        return cognomeOut;
    }// fine del metodo

    /**
     * Controllo di validità di un cognome <br>
     * Elimina parti iniziali con caratteri/prefissi non accettati <br>
     * Elimina sempre il cognome esattamente uguale al tag-iniziale o al tag-all <br>
     * Elimina sempre il cognome che inizia col tag-iniziale <br>
     * Elimina il cognome che inizia col tag-all se il tag è seguito da spazio <br>
     *
     * @param cognomeTxt cognome della persona
     */
    private static boolean checkNome(String cognomeTxt) {
        boolean accettato = true;
        ArrayList<String> tagIniziale = LibBio.sumTag(TAG_INI_NUMERI, TAG_INI_CHAR, TAG_INI_APICI);

        for (String tag : tagIniziale) {
            if (cognomeTxt.startsWith(tag)) {
                accettato = false;
            }// fine del blocco if
        }// end of for cycle

        return accettato;
    }// fine del metodo

    /**
     * Spazzola la lista dei cognomi
     */
    private static void spazzolaAllCognomiUnici(List<String> listaCognomiUnici) {
        listaCognomiUnici.forEach((cognomeTxt) -> elaboraSingolo(cognomeTxt));
    }// fine del metodo


    /**
     * Calcola il numero di voci Bio esistenti per il cognome
     * Se supera il taglio TAGLIO_COGNOMI_ELENCO, crea/aggiunge/modifica
     * Se non supera il taglio TAGLIO_COGNOMI_ELENCO, elimina/invalida
     *
     * @param cognomeTxt cognome della persona
     */
    private static Cognome elaboraSingolo(String cognomeTxt) {
        int numVociBio = 0;
        Cognome cognome = Cognome.findByCognome(cognomeTxt);

        if (cognome != null) {
            numVociBio = cognome.countBioCognome();
        }// end of if cycle

        if (numVociBio >= Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO)) {
            aggiungeSingolo(cognomeTxt);
        } else {
            eliminaSingolo(cognomeTxt);
        }// end of if/else cycle

        return cognome;
    }// fine del metodo

    /**
     * Crea/aggiunge/modifica
     * Controlla se esiste
     * Se non esiste, lo crea
     * Aggiorna i flag ed il numero di voci Bio
     *
     * @param cognomeTxt cognome della persona
     */
    private static Cognome aggiungeSingolo(String cognomeTxt) {
        Cognome cognome = Cognome.findByCognome(cognomeTxt);

        if (cognome == null) {
            cognome = new Cognome(cognomeTxt);
            cognome.setPrincipale(true);
            cognome.setRiferimento(cognome);
            try { // prova ad eseguire il codice
                cognome.save();
            } catch (Exception unErrore) { // intercetta l'errore
                int a = 87;
            }// fine del blocco try-catch
        }// end of if cycle

        return cognome;
    }// fine del metodo

    /**
     * Elimina/invalida
     * Se esiste cerca di cancellarlo o comunque lo invalida
     * Se non esiste, non fa nulla
     *
     * @param cognomeTxt cognome della persona
     */
    private static Cognome eliminaSingolo(String cognomeTxt) {
        Cognome cognome = Cognome.findByCognome(cognomeTxt);

        if (cognome == null) {
            cognome = new Cognome(cognomeTxt);
            cognome.setPrincipale(true);
            cognome.setRiferimento(cognome);
            try { // prova ad eseguire il codice
                cognome.save();
            } catch (Exception unErrore) { // intercetta l'errore
                int a = 87;
            }// fine del blocco try-catch
        }// end of if cycle

        return cognome;
    }// fine del metodo

}// end of static class

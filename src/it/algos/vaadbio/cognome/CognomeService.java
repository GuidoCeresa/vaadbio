package it.algos.vaadbio.cognome;


import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gac on 05 feb 2016.
 * .
 */
public abstract class CognomeService {

    /**
     * Aggiunta nuovi records e modifica di quelli esistenti
     * Vengono creati nuovi records per i nomi presenti nelle voci (bioGrails) che superano la soglia minima
     */
    public static void aggiorna() {
        List<String> listaCognomiCompleta;
        List<String> listaCognomiUnici;

        //--recupera una lista 'grezza' di tutti i cognomi
        listaCognomiCompleta = creaListaNomiCompleta();

        //--elimina tutto ciò che compare oltre al cognome
        listaCognomiUnici = elaboraAllCognomiUnici(listaCognomiCompleta);

        //--(ri)costruisce i records
        spazzolaAllCognomiUnici(listaCognomiUnici);
    }// fine del metodo


    /**
     * Recupera una lista 'grezza' di tutti i cognomi
     */
    private static ArrayList<String> creaListaNomiCompleta() {
        return LibBio.queryFindDistinctStx("Bio", "cognome");
    }// fine del metodo

    /**
     * Elabora tutti i cognomi
     * Costruisce una lista di cognomi ''validi' e 'unici'
     */
    public static List<String> elaboraAllCognomiUnici(List<String> listaCognomiCompleta) {
        ArrayList<String> listaCognomiUnici = new ArrayList<String>();
        String cognomeValido;

        //--costruisce una lista di cognomi 'unici'
        for (String cognomeDaControllare : listaCognomiCompleta) {
            cognomeValido = check(cognomeDaControllare);
            if (!cognomeValido.equals(CostBio.VUOTO)) {
                if (!listaCognomiUnici.contains(cognomeValido)) {
                    listaCognomiUnici.add(cognomeValido);
                }// fine del blocco if
            }// fine del blocco if
        }// end of for cycle

        return listaCognomiUnici;
    }// fine del metodo


    /**
     * Elabora il singolo cognome
     * <p>
     * Prima regola: considera solo i nomi più lunghi di 2 caratteri
     * Seconda regola: usa (secondo preferenze) i nomi singoli; Maria e Maria Cristina sono uguali
     * Terza regola: controlla una lista di nomi doppi accettati come autonomi per la loro rilevanza
     * Quarta regola: elimina parti iniziali con caratteri/prefissi non accettati -> LibBio.checkNome()
     * Quinta regola: elimina <ref>< finali e testo successivo
     * <p>
     * Elimina caratteri 'anomali' dal nome
     * Gian, Lady, Sir, Maestro, Abd, 'Abd, Abu, Abū, Ibn, DJ, e J.
     */
    public static String check(String cognomeIn) {
        String cognomeOut = CostBio.VUOTO;
        ArrayList listaTagIniziali = new ArrayList();
        int pos;
        String tag = CostBio.VUOTO;
        boolean usaNomeSingolo = Pref.getBool(CostBio.USA_NOME_SINGOLO, true);
        Nome cognomeEsistente = null;


        //--prima regola
        if (cognomeIn.length() < 3) {
            return cognomeOut;
        } else {
            cognomeOut = cognomeIn.trim();
        }// end of if/else cycle

//        if (cognomeOut.contains(CostBio.SPAZIO)) {
//            //--seconda regola
//            if (usaNomeSingolo) {
//                pos = nomeOut.indexOf(CostBio.SPAZIO);
//                nomeOut = nomeOut.substring(0, pos);
//                nomeOut = nomeOut.trim();
//            }// end of if cycle
//
//            //--terza regola
//            nomeEsistente = Nome.findByNome(nomeIn.trim());
//            if (nomeEsistente != null) {
//                if (nomeEsistente.isNomeDoppio()) {
//                    nomeOut = nomeEsistente.getNome();
//                }// end of if cycle
//            } else {
//                int a = 87;
//            }// end of if/else cycle
//        }// fine del blocco if
//
//        // --quarta regola
//        if (!checkNome(nomeOut)) {
//            nomeOut = CostBio.VUOTO;
//        }// fine del blocco if

        //--quinta regola
        cognomeOut = LibBio.fixCampo(cognomeOut);

//        //--prima regola ricontrollata dopo l'uso del nome singolo
//        if (nomeOut.length() < 3) {
//            nomeOut = CostBio.VUOTO;
//        }// fine del blocco if

        //--per sicurezza in caso di nomi strani
        cognomeOut = LibText.primaMaiuscola(cognomeOut);

        return cognomeOut;
    }// fine del metodo

    /**
     * Spazzola la lista dei cognomi
     */
    public static void spazzolaAllCognomiUnici(List<String> listaCognomiUnici) {
        for (String cognome : listaCognomiUnici) {
            elaboraSingolo(cognome);
        }// end of for cycle
    }// fine del metodo


    /**
     * Crea (controllando che non esista già) un record principale di Cognome
     *
     * @param cognomeTxt cognome della persona
     */
    private static Cognome elaboraSingolo(String cognomeTxt) {
        Cognome cognome = Cognome.findByCognome(cognomeTxt);

        if (cognome == null) {
            cognome = new Cognome(cognomeTxt);
            cognome.setPrincipale(true);
            cognome.setRiferimento(cognome);
            try { // prova ad eseguire il codice
                cognome.save();
            } catch (Exception unErrore) { // intercetta l'errore
                int a=87;
            }// fine del blocco try-catch
        }// end of if cycle

        return cognome;
    }// fine del metodo

}// end of static class

package it.algos.vaadbio.cognome;


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
        ArrayList<String> listaCognomiCompleta;
        List alfa;
        ArrayList<String> listaCognomiUnici;

//        //--recupera una lista 'grezza' di tutti i nomi
//        listaCognomiCompleta = creaListaNomiCompleta();
//
//        //--elimina tutto ciò che compare oltre al nome
//        listaCognomiUnici = elaboraAllNomiUnici(listaCognomiCompleta);
//
//        //--(ri)costruisce i records
//        spazzolaAllCognomiUnici(listaCognomiUnici);
    }// fine del metodo


}// end of static class

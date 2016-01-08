package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.lib.ParBio;

import java.util.LinkedHashMap;

/**
 * Created by gac on 19 dic 2015.
 * Elabora valori base di tutti i parametri
 */
public class ElaboraParametri {

    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraParametri(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    //--Elabora valori base di tutti i parametri
    private void doInit(Bio bio) {
        LinkedHashMap<String, String> mappaBio = LibBio.getMappaBio(bio);

        if (mappaBio == null || mappaBio.size() < 1) {
            return;
        }// end of if cycle

        for (ParBio par : ParBio.values()) {
            if (mappaBio.get(par.getTag()) != null) {
                par.setBio(bio, mappaBio.get(par.getTag()));
            }// end of if cycle
        } // fine del ciclo for-each

    }// end of method

//    /**
//     * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
//     */
//    public static HashMap<String, String> estraeMappa(Bio bio) {
//        String tmplBioServer = bio.getTmplBioServer();
//        return LibBio.getMappaBio(tmplBioServer);
//    }// end of method

}// end of class

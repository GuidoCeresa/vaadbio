package it.algos.vaadbio.delta;


import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;

/**
 * Created by gac on 05 feb 2016.
 * .
 */
public abstract class DeltaService {

    /**
     * Cancella tutti i records esistenti di delta
     */
    private static void reset() {
        ArrayList<Delta> lista = Delta.findAll();

        if (lista != null && lista.size() > 0) {
            for (Delta delta : lista) {
                delta.delete();
            }// end of for cycle
        }// end of if cycle
    }// end of static method

    /**
     * Recupera tutti i records di Bio
     */
    private static ArrayList<Bio> findBio() {
        return Bio.findAll();
    }// end of static method


    /**
     * Recupera tutti i records di Bio
     */
    private static ArrayList<Bio> getBio() {
        reset();
        return Bio.findAll();
    }// end of static method


    /**
     * Crea le differenze tra title e nome + cognome del template Bio
     * <p>
     * Cancella tutti i records esistenti
     * Recupera tutti i records di Bio
     * Elabora la differenza per ogni record di Bio
     * Registra il record di Delta solo se esiste la differenza
     */
    public static void titolo() {
        ArrayList<Bio> listaBio = getBio();
        String titleValido;

        if (listaBio != null && listaBio.size() > 0) {
            for (Bio bio : listaBio) {
                titleValido = bio.getNome();

                if (!bio.getCognome().equals(CostBio.VUOTO)) {
                    titleValido += " " + bio.getCognome();
                }// end of if cycle

                elaboraDifferenza(bio.getTitle(), titleValido);
            }// end of for cycle
        }// end of if cycle
    }// end of static method

    /**
     * Crea le differenze tra nome e nomeValido del template Bio
     * <p>
     * Cancella tutti i records esistenti
     * Recupera tutti i records di Bio
     * Elabora la differenza per ogni record di Bio
     * Registra il record di Delta solo se esiste la differenza
     */
    public static void nome() {
        ArrayList<Bio> listaBio = getBio();
        if (listaBio != null && listaBio.size() > 0) {
            for (Bio bio : listaBio) {
                elaboraDifferenza(bio.getNome(), bio.getNomeValido());
            }// end of for cycle
        }// end of if cycle
    }// end of static method

    /**
     * Crea le differenze tra cognome e cognomeValido del template Bio
     * <p>
     * Cancella tutti i records esistenti
     * Recupera tutti i records di Bio
     * Elabora la differenza per ogni record di Bio
     * Registra il record di Delta solo se esiste la differenza
     */
    public static void cognome() {
        ArrayList<Bio> listaBio = getBio();
        if (listaBio != null && listaBio.size() > 0) {
            for (Bio bio : listaBio) {
                elaboraDifferenza(bio.getCognome(), bio.getCognomeValido());
            }// end of for cycle
        }// end of if cycle
    }// end of static method

    /**
     * Elabora la differenza per ogni record di Bio
     * Registra il record di Delta solo se esiste la differenza
     */
    private static void elaboraDifferenza(String sorgente, String valido) {
        String differenza = CostBio.VUOTO;
        if (!sorgente.equals(valido)) {
            if (sorgente.startsWith(valido)) {
                differenza = sorgente.substring(valido.length());
            } else {
                differenza = "xxx";
            }// end of if/else cycle
        }// end of if cycle

        if (!differenza.equals(CostBio.VUOTO)) {
            new Delta(sorgente, valido, differenza).save();
        }// end of if cycle
    }// end of static method

}// end of static class

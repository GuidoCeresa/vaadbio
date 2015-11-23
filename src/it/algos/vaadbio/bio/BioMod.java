package it.algos.vaadbio.bio;

import it.algos.webbase.web.module.ModulePop;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class BioMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Bio";

    public BioMod() {
        super(Bio.class, MENU_ADDRESS);
    }// end of constructor


}// end of class

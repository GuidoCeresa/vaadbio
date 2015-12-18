package it.algos.vaadbio.anno;


import it.algos.webbase.web.module.ModulePop;
import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class AnnoMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Anno";

    public AnnoMod() {
        super(Anno.class, MENU_ADDRESS);
    }// end of constructor

}// end of class


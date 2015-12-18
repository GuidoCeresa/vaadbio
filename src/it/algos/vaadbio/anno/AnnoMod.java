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

    /**
     * Crea i campi visibili
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * NON garantisce l'ordine con cui vengono presentati i campi nella scheda <br>
     * Può mostrare anche il campo ID, oppure no <br>
     * Se si vuole differenziare tra Table, Form e Search, <br>
     * sovrascrivere creaFieldsList, creaFieldsForm e creaFieldsSearch <br>
     */
    protected Attribute<?, ?>[] creaFieldsAll() {
        return new Attribute[]{Anno_.ordinamento, Anno_.nome, Anno_.secolo};
    }// end of method

}// end of class


package it.algos.vaadbio.biologo;

import com.vaadin.server.FontAwesome;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.log.LogMod;
import it.algos.webbase.domain.vers.Versione;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

/**
 * Created by gac on 17 set 2015
 * Sovrascritta la classe esistente nel plugin Webbase, per modificare la Table
 */
public class BioLogoMod extends LogMod {

    /**
     * Costruttore senza parametri
     * La classe implementa il pattern Singleton.
     * Per una nuova istanza, usare il metodo statico getInstance.
     * Usare questo costruttore SOLO con la Reflection dal metodo Module.getInstance
     * Questo costruttore è pubblico SOLO per l'uso con la Reflection.
     * Per il pattern Singleton dovrebbe essere privato.
     *
     * @deprecated
     */
    public BioLogoMod() {
        super();
        this.setIcon(FontAwesome.BARS);
    }// end of constructor

    /**
     * Crea una sola istanza di un modulo per sessione.
     * Tutte le finestre e i tab di un browser sono nella stessa sessione.
     */
    public static BioLogoMod getInstance() {
        return (BioLogoMod) ModulePop.getInstance(BioLogoMod.class);
    }// end of singleton

    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    @Override
    public ATable createTable() {
        return (new BioLogoTable(this));
    }// end of method

}// end of class

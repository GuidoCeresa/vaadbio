package it.algos.vaadbio.bio;

import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 25 nov 2015.
 * .
 */
public class BioTable extends ModuleTable {

    public BioTable(ModulePop modulo) {
        super(modulo);
        this.setColumnHeader(Bio_.templateEsiste.getName(), "IS");
        this.setColumnHeader(Bio_.templateValido.getName(), "OK");
        this.setColumnHeader(Bio_.templatesUguali.getName(), "==");
    }// end of constructor

}// end of class

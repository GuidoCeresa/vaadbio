package it.algos.vaadbio.giorno;

import it.algos.vaadbio.crono.CronoTable;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 26 nov 2016.
 * .
 */
public class GiornoTable extends CronoTable {


    /**
     * Costruttore base
     */
    public GiornoTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    /**
     * Returns an array of the visible columns ids. Ids might be of type String
     * or Attribute.<br>
     * This implementations returns all the columns (no order).
     *
     * @return the list
     */
    @Override
    @SuppressWarnings("rawtypes")
    protected Object[] getDisplayColumns() {
        return new Object[]{Giorno_.ordinamento, Giorno_.titolo, colLinkNati, colLinkMorti};
    }// end of method


    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloNato(BaseEntity bean) {
        String wikiTitle = "";
        Giorno giorno;

        if (bean != null && bean instanceof Giorno) {
            giorno = (Giorno) bean;
            wikiTitle = giorno.getTitoloListaNati();
        }// fine del blocco if

        return wikiTitle;
    }// end of method

    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloMorto(BaseEntity bean) {
        String wikiTitle = "";
        Giorno giorno;

        if (bean != null && bean instanceof Giorno) {
            giorno = (Giorno) bean;
            wikiTitle =   giorno.getTitoloListaMorti();
        }// fine del blocco if

        return wikiTitle;
    }// end of method


}// end of class

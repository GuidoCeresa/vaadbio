package it.algos.vaadbio.anno;

import it.algos.vaadbio.crono.CronoTable;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 27 nov 2016.
 * .
 */
public class AnnoTable extends CronoTable {

    /**
     * Costruttore base
     */
    public AnnoTable(ModulePop modulo) {
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
        return new Object[]{Anno_.ordinamento, Anno_.titolo, colLinkNati, colLinkMorti};
    }// end of method


    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloNato(BaseEntity bean) {
        String wikiTitle = "";
        Anno anno;
        String tag = "Nati nel ";

        if (bean != null && bean instanceof Anno) {
            anno = (Anno) bean;
            wikiTitle = tag + anno.getTitolo();
        }// fine del blocco if

        return wikiTitle;
    }// end of method

    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloMorto(BaseEntity bean) {
        String wikiTitle = "";
        Anno anno;
        String tag = "Morti nel ";

        if (bean != null && bean instanceof Anno) {
            anno = (Anno) bean;
            wikiTitle = tag + anno.getTitolo();
        }// fine del blocco if

        return wikiTitle;
    }// end of method


}// end of class

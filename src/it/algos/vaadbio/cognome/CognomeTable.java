package it.algos.vaadbio.cognome;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 12 mag 2016.
 * .
 */
public class CognomeTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String colLink = "wiki";

    private static boolean checkLista = false;


    public CognomeTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void createAdditionalColumns() {
        addGeneratedColumn(colLink, new LinkColumnGenerator());
    }// end of method

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
        return new Object[]{Cognome_.cognome, colLink};
    }// end of method

    /**
     * Genera la singola cella di link.
     * Siamo in fase di creazione/visualizzazione della lista
     *
     * @param itemId l'item id
     */
    @SuppressWarnings("unchecked")
    private Component generateCellLink(Object itemId) {
        String wikiTitle = CostBio.VUOTO;
        Cognome istanza = null;
        BaseEntity bean = getBean(itemId);

        if (bean != null && bean instanceof Nome) {
            istanza = (Cognome) bean;
            wikiTitle = getTitolo(istanza);
        }// end of if cycle

        if (checkLista) {
            if (Api.esiste(wikiTitle)) {
                return new Label(wikiTitle);
            } else {
                return null;
            }// end of if/else cycle
        } else {
            return new Label(wikiTitle);
        }// end of if/else cycle
    }// end of method

    /**
     * Action when a user click the table
     * Must be overridden on the subclass
     * Siamo in fase di click nella tavola
     *
     * @param itemClickEvent the event
     */
    @Override
    public void itemClick(ItemClickEvent itemClickEvent) {
        BaseEntity bean = getBeanClickOnColumn(itemClickEvent, colLink);

        if (bean != null && bean instanceof Cognome) {
            clickOnLink((Cognome) bean);
        }// end of if cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanzaNome record selezionato
     */
    private void clickOnLink(Cognome istanzaNome) {
        String wikiTitle = getTitolo(istanzaNome);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += "Su wikipedia non esiste (ancora) una lista di persone di cognome ";
            message += istanzaNome.getCognome();
            message += ".\n Ci sono troppe poche voci biografiche che usano questo cognome.";
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * @param istanza record selezionato
     */
    private String getTitolo(Cognome istanza) {
        String wikiTitle = "";
        String cognome = istanza.getCognome();
        String tag = "Persone di cognome ";

        if (!cognome.equals(CostBio.VUOTO)) {
            wikiTitle = tag + cognome;
        }// fine del blocco if

        return wikiTitle;
    }// end of method

    /**
     * Genera la colonna dei link alle pagine wikipedia con le liste di persone.
     */
    class LinkColumnGenerator implements ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            return generateCellLink(itemId);
        }// end of inner method
    }// end of inner class

}// end of class

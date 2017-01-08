package it.algos.vaadbio.cognome;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 25 nov 2016.
 * .
 */
public class CognomeTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String colLink = "pagina su wikipedia";
    private final static String ESISTE = "Su wikipedia non esiste (ancora) una lista di persone di cognome ";
    private final static String POCHE = "Ci sono troppe poche voci biografiche che usano questo cognome";
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
        return new Object[]{Cognome_.cognome, colLink, Cognome_.voci};
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
        Label label;
        int soglia = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        Cognome istanza = null;
        BaseEntity bean = getBean(itemId);
        boolean superaSoglia = false;

        if (bean != null && bean instanceof Cognome) {
            istanza = (Cognome) bean;
            wikiTitle = getTitolo(istanza);
            superaSoglia = istanza.getVoci() >= soglia;
        }// end of if cycle

        if (checkLista) {
            if (Api.esiste(wikiTitle)) {
                return new Label(wikiTitle);
            } else {
                return null;
            }// end of if/else cycle
        } else {
            if (superaSoglia) {
                label = new Label(wikiTitle);
                label.addStyleName("wiki");
            } else {
                label = new Label(POCHE);
                label.addStyleName("warning");
            }// end of if/else cycle
            return label;
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
            message += ESISTE;
            message += istanzaNome.getCognome();
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * @param istanza selezionata
     */
    private String getTitolo(Cognome istanza) {
        String wikiTitle = "";
        String cognomeTxt = istanza.getCognome();
        String tag = "Persone di cognome ";

        if (!cognomeTxt.equals(CostBio.VUOTO)) {
            wikiTitle = tag + cognomeTxt;
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

package it.algos.vaadbio.attivita;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.cognome.Cognome_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 25 nov 2016.
 * .
 */
public class AttivitaTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String colLink = "pagina su wikipedia";
    private final static String ESISTE = "Su wikipedia non esiste (ancora) una lista di persone di attività ";
    private final static String POCHE = "Ci sono troppe poche voci biografiche che usano questa attività";
    private static boolean checkLista = false;


    public AttivitaTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void createAdditionalColumns() {
        addGeneratedColumn(colLink, new AttivitaTable.LinkColumnGenerator());
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
        return new Object[]{Attivita_.singolare, Attivita_.plurale, colLink};
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
        Label label = null;
        int soglia = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        Attivita istanza = null;
        BaseEntity bean = getBean(itemId);
        boolean superaSoglia = false;

        if (bean != null && bean instanceof Attivita) {
            istanza = (Attivita) bean;
            wikiTitle = getTitolo(istanza);
        }// end of if cycle

        if (checkLista) {
            if (Api.esiste(wikiTitle)) {
                label = new Label(wikiTitle);
            } else {
                label = new Label(POCHE);
                label.addStyleName("warning");
            }// end of if cycle
        } else {
            label = new Label(wikiTitle);
            label.addStyleName("wiki");
        }// end of if/else cycle

        return label;
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

        if (bean != null && bean instanceof Attivita) {
            clickOnLink((Attivita) bean);
        }// end of if cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanzaNome record selezionato
     */
    private void clickOnLink(Attivita istanzaNome) {
        String wikiTitle = getTitolo(istanzaNome);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += ESISTE;
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * @param istanza selezionata
     */
    private String getTitolo(Attivita istanza) {
        String wikiTitle = "";
        String attivitaTxt = istanza.getPlurale();
        String tag = "Progetto:Biografie/Attività/";

        if (!attivitaTxt.equals(CostBio.VUOTO)) {
            wikiTitle = tag + LibText.primaMaiuscola(attivitaTxt);
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

package it.algos.vaadbio.giorno;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 26 nov 2016.
 * .
 */
public class GiornoTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String colLinkNati = "wiki nati";
    private static final String colLinkMorti = "wiki morti";
    private final static String ESISTE = "Su wikipedia non esiste (ancora) una lista di persone di cognome ";
    private final static String POCHE = "Ci sono troppe poche voci biografiche che usano questo cognome";

    private static boolean checkLista = false;


    public GiornoTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void createAdditionalColumns() {
        addGeneratedColumn(colLinkNati, new LinkColumnGeneratorNati());
        addGeneratedColumn(colLinkMorti, new LinkColumnGeneratorMorti());
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
        return new Object[]{Giorno_.ordinamento, Giorno_.titolo, colLinkNati, colLinkMorti};
    }// end of method

    /**
     * Genera la singola cella di link.
     * Siamo in fase di creazione/visualizzazione della lista
     *
     * @param itemId l'item id
     */
    @SuppressWarnings("unchecked")
    private Component generateCellLinkNati(Object itemId) {
        String wikiTitle = CostBio.VUOTO;
        Label label;
        Giorno istanza = null;
        BaseEntity bean = getBean(itemId);
        boolean superaSoglia = false;

        if (bean != null && bean instanceof Giorno) {
            istanza = (Giorno) bean;
            wikiTitle = getTitoloNato(istanza);
        }// end of if cycle

        label = new Label(wikiTitle);
        label.addStyleName("wiki");

        return label;
    }// end of method

    /**
     * Genera la singola cella di link.
     * Siamo in fase di creazione/visualizzazione della lista
     *
     * @param itemId l'item id
     */
    @SuppressWarnings("unchecked")
    private Component generateCellLinkMorti(Object itemId) {
        String wikiTitle = CostBio.VUOTO;
        Label label;
        Giorno istanza = null;
        BaseEntity bean = getBean(itemId);
        boolean superaSoglia = false;

        if (bean != null && bean instanceof Giorno) {
            istanza = (Giorno) bean;
            wikiTitle = getTitoloMorto(istanza);
        }// end of if cycle

        label = new Label(wikiTitle);
        label.addStyleName("wiki");

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
        BaseEntity beanNato = getBeanClickOnColumn(itemClickEvent, colLinkNati);
        BaseEntity beanMorto = getBeanClickOnColumn(itemClickEvent, colLinkMorti);

        if (beanNato != null && beanNato instanceof Giorno) {
            clickOnLinkNato((Giorno) beanNato);
        }// end of if cycle

        if (beanMorto != null && beanMorto instanceof Giorno) {
            clickOnLinkMorto((Giorno) beanMorto);
        }// end of if cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanzaGiorno record selezionato
     */
    private void clickOnLinkNato(Giorno istanzaGiorno) {
        String wikiTitle = getTitoloNato(istanzaGiorno);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += ESISTE;
            message += istanzaGiorno.getTitolo();
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanzaGiorno record selezionato
     */
    private void clickOnLinkMorto(Giorno istanzaGiorno) {
        String wikiTitle = getTitoloMorto(istanzaGiorno);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += ESISTE;
            message += istanzaGiorno.getTitolo();
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * @param istanza selezionata
     */
    protected String getTitoloNato(Giorno istanza) {
        String wikiTitle = "";
        String giornoTxt = istanza.getTitolo();
        String tag = "Nati il ";

        if (!giornoTxt.equals(CostBio.VUOTO)) {
            wikiTitle = tag + giornoTxt;
        }// fine del blocco if

        return wikiTitle;
    }// end of method

    /**
     * @param istanza selezionata
     */
    protected String getTitoloMorto(Giorno istanza) {
        String wikiTitle = "";
        String giornoTxt = istanza.getTitolo();
        String tag = "Morti il ";

        if (!giornoTxt.equals(CostBio.VUOTO)) {
            wikiTitle = tag + giornoTxt;
        }// fine del blocco if

        return wikiTitle;
    }// end of method

    /**
     * Genera la colonna dei link alle pagine wikipedia con le liste dei nati per giorno.
     */
    class LinkColumnGeneratorNati implements ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            return generateCellLinkNati(itemId);
        }// end of inner method
    }// end of inner class

    /**
     * Genera la colonna dei link alle pagine wikipedia con le liste dei morti per giorno.
     */
    class LinkColumnGeneratorMorti implements ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            return generateCellLinkMorti(itemId);
        }// end of inner method
    }// end of inner class

}// end of class

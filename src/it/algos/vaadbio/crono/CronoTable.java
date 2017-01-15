package it.algos.vaadbio.crono;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

/**
 * Created by gac on 27 nov 2016.
 * .
 */
public abstract class CronoTable extends ModuleTable {

    protected final static String WIKI_BASE = "https://it.wikipedia.org/";
    protected final static String WIKI_URL = WIKI_BASE + "wiki/";
    protected final static String colLinkNati = "Nati";
    protected final static String colLinkMorti = "Mrti";
    protected final static String ESISTE = "Su wikipedia non esiste (ancora) una lista corrispondente ";
    protected final static String POCHE = "Ci sono troppe poche voci biografiche che usano questo lista";

    private static boolean checkLista = false;


    /**
     * Costruttore base
     */
    public CronoTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    @Override
    protected void createAdditionalColumns() {
        addGeneratedColumn(colLinkNati, new LinkColumnGeneratorNati());
        addGeneratedColumn(colLinkMorti, new LinkColumnGeneratorMorti());
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

        if (beanNato != null) {
            clickOnLinkNato(beanNato);
        }// end of if cycle

        if (beanMorto != null) {
            clickOnLinkMorto(beanMorto);
        }// end of if cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanza record selezionato
     */
    private void clickOnLinkNato(BaseEntity istanza) {
        String wikiTitle = getTitoloNato(istanza);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += ESISTE;
            message += wikiTitle;
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method

    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanza record selezionato
     */
    private void clickOnLinkMorto(BaseEntity istanza) {
        String wikiTitle = getTitoloMorto(istanza);
        String message = CostBio.VUOTO;

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            message += ESISTE;
            message += wikiTitle;
            message += ".\n" + POCHE;
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

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
        BaseEntity bean = getBean(itemId);

        if (bean != null && bean instanceof BaseEntity) {
            wikiTitle = getTitoloNato(bean);
        }// end of if cycle

        label = new Label(wikiTitle);
        label.addStyleName("wiki");

        return label;
    }// end of method

    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloNato(BaseEntity bean) {
        return CostBio.VUOTO;
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
        BaseEntity bean = getBean(itemId);

        if (bean != null && bean instanceof BaseEntity) {
            wikiTitle = getTitoloMorto(bean);
        }// end of if cycle

        label = new Label(wikiTitle);
        label.addStyleName("wiki");

        return label;
    }// end of method

    /**
     * @param bean istanza selezionata
     */
    protected String getTitoloMorto(BaseEntity bean) {
        return CostBio.VUOTO;
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

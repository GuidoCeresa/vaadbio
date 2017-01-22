package it.algos.vaadbio.bio;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.attivita.AttivitaTable;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;

import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gac on 25 nov 2015.
 * .
 */
public class BioTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String COL_LINK = "pagina su wikipedia";

    public BioTable(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    @Override
    protected void createAdditionalColumns() {
        addGeneratedColumn(COL_LINK, new BioTable.LinkColumnGenerator());
    }// end of method

    /**
     * Returns an array of the visible columns ids. Ids might be of type String
     * or Attribute.<br>
     * This implementations returns all the columns (no order).
     *
     * @return the list
     */
    @SuppressWarnings("rawtypes")
    protected Object[] getDisplayColumns() {
        return new Object[]{
                Bio_.pageid,
//                Bio_.title,
                COL_LINK,

                Bio_.ultimaLettura,
                Bio_.ultimaElaborazione,
                Bio_.templateEsiste,
                Bio_.templateValido,
                Bio_.templatesUguali,

                Bio_.titolo,
                Bio_.nome,
                Bio_.nomeValido,
                Bio_.cognome,
                Bio_.sesso,

                Bio_.luogoNascita,
                Bio_.luogoNascitaLink,
                Bio_.luogoNascitaAlt,
                Bio_.luogoMorte,
                Bio_.luogoMorteLink,
                Bio_.luogoMorteAlt,

                Bio_.attivita,
                Bio_.attivita2,
                Bio_.attivita3,
                Bio_.attivitaAltre,
                Bio_.nazionalita,

                Bio_.didascaliaListe,
        };// end of collection
    }// end of method


    /**
     * Initializes the table.
     * Must be called from the costructor in each subclass
     * Chiamato dal costruttore di ModuleTable
     */
    protected void init() {
        super.init();

        for (Object obj : getContainerPropertyIds()) {
            this.setColumnCollapsed(obj, true);
        }// end of for cycle

        this.setColumnCollapsed(Bio_.pageid.getName(), false);
        this.setColumnCollapsed(Bio_.title.getName(), false);
        this.setColumnCollapsed(Bio_.ultimaLettura.getName(), false);
        this.setColumnCollapsed(Bio_.ultimaElaborazione.getName(), false);
        this.setColumnCollapsed(Bio_.didascaliaListe.getName(), false);
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
        Bio istanza = null;
        BaseEntity bean = getBean(itemId);
        boolean superaSoglia = false;

        if (bean != null && bean instanceof Bio) {
            istanza = (Bio) bean;
            wikiTitle = istanza.getTitle();
        }// end of if cycle

        label = new Label(wikiTitle);
        label.addStyleName("wiki");

//        if (checkLista) {
//            if (Api.esiste(wikiTitle)) {
//                label = new Label(wikiTitle);
//            } else {
//                label = new Label(POCHE);
//                label.addStyleName("warning");
//            }// end of if cycle
//        } else {
//            label = new Label(wikiTitle);
//            label.addStyleName("wiki");
//        }// end of if/else cycle

        return label;
    }// end of method

    /**
     * Genera la colonna dei link alle pagine wikipedia con le liste di persone.
     */
    class LinkColumnGenerator implements ColumnGenerator {
        public Component generateCell(Table source, Object itemId, Object columnId) {
            return generateCellLink(itemId);
        }// end of inner method
    }// end of inner class


    /**
     * Action when a user click the table
     * Must be overridden on the subclass
     * Siamo in fase di click nella tavola
     *
     * @param itemClickEvent the event
     */
    @Override
    public void itemClick(ItemClickEvent itemClickEvent) {
        BaseEntity bean = getBeanClickOnColumn(itemClickEvent, COL_LINK);

        if (bean != null && bean instanceof Bio) {
            clickOnLink((Bio) bean);
        }// end of if cycle
    }// end of method


    /**
     * Mouse click on column colLink
     * Apre la pagina di wikipedia con la lista dei nomi
     *
     * @param istanza record selezionato
     */
    private void clickOnLink(Bio istanza) {
        String wikiTitle = istanza.getTitle();
        String message = "Su wikipedia non esiste questa voce biografica ";

        if (Api.esiste(wikiTitle)) {
            this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
        } else {
            Notification.show("Avviso", message, Notification.Type.HUMANIZED_MESSAGE);
        }// end of if/else cycle

    }// end of method


}// end of class

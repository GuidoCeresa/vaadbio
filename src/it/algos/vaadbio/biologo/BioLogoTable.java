package it.algos.vaadbio.biologo;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import it.algos.vaad.lib.VaadWiki;
import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaad.wiki.PagePar;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ModuleTable;
import org.vaadin.addons.lazyquerycontainer.CompositeItem;

/**
 * Created by gac on 17 set 2015.
 * Sovrascritta la classe esistente nel plugin Webbase, per aggiungere un listener
 */
public class BioLogoTable extends ModuleTable {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    public static final String WIKI_TITLE = WIKI_BASE + "w/index.php?title=";
    public static final String WIKI_DIFF_HEADER = WIKI_TITLE;
    public static final String WIKI_DIFF_NEW = "&diff=";
    public static final String WIKI_DIFF_OLD = "&oldid=";
    public static final String WIKI_CRONO = WIKI_TITLE;
    public static final String WIKI_CRONO_END = "&action=history";
    public static final String WIKI_UTENTE = WIKI_URL + "Speciale:Contributi/";

    private static final String COLUMN_DESC = "descrizione";


    public BioLogoTable(ModulePop module) {
        super(module);
    }// end of constructor


    /**
     * Action when a user click the table
     * Must be overridden on the subclass
     *
     * @param itemClickEvent the event
     */
    @Override
    public void itemClick(ItemClickEvent itemClickEvent) {
        BaseEntity istanza = getBeanClickOnColumn(itemClickEvent, COLUMN_DESC);

        if (istanza != null && istanza instanceof Log) {
            clickOnDescrizione((Log) istanza);
        }// end of if cycle

    }// end of method



    /**
     * Mouse click on title descrizione
     * Se esiste un titolo valido, apre la voce di wikipedia
     *
     * @param logo record selezionato
     */
    private void clickOnDescrizione(Log logo) {
        String desc = logo.getDescrizione();
        String tag1 = "La pagina";
        String tag2 = "la voce";
        String tag3 = "Le mappe della voce";
        String tag4 = "Singolo download";
        String tag5 = "sul server la voce";
        String wikiTitle = "";

        if (desc != null) {
            if (desc.contains(tag1) || desc.contains(tag2) || desc.contains(tag3)|| desc.contains(tag4)) {
                wikiTitle = VaadWiki.estraeDoppiaQuadra(desc);
                if (!wikiTitle.equals("")) {
                    this.getUI().getPage().open(WIKI_URL + wikiTitle, "_blank");
                }// fine del blocco if
            }// fine del blocco if
            if (desc.contains(tag4)) {
                wikiTitle = VaadWiki.estraeDoppiaQuadra(desc);
                if (!wikiTitle.equals("")) {
                    visualizzaUltimaModifica(wikiTitle);
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if
    }// end of method


    /**
     * Apre la differenza di versioni con l'ultima modifica
     */
    private void visualizzaUltimaModifica(String wikiTitle) {
        Page page;
        long revId = 0;
        long parentId = 0;

        if (!wikiTitle.equals("")) {
            page = Api.leggePage(wikiTitle);
            if (page != null) {
                wikiTitle = page.getTitle();
                revId = (Long) page.getMappaReadObj().get(PagePar.revid.toString());
                parentId = (Long) page.getMappaReadObj().get(PagePar.parentid.toString());
            }// end of if cycle
        }// end of if cycle

        if (wikiTitle != null && revId > 0 && parentId > 0) {
            this.getUI().getPage().open(WIKI_DIFF_HEADER + wikiTitle + WIKI_DIFF_NEW + revId + WIKI_DIFF_OLD + parentId, "_blank");
        }// end of if cycle
    }// end of method

}// end of class

package it.algos.vaadbio.biologo;

import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import it.algos.vaad.lib.VaadWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.BioMod;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

/**
 * Created by gac on 17 set 2015.
 * Sovrascritta la classe esistente nel plugin Webbase, per aggiungere un listener
 */
public class BioLogoTable extends ATable {

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
    protected void itemClick(ItemClickEvent itemClickEvent) {
        Log logo = null;
        String titoloColonna = itemClickEvent.getPropertyId().toString();
        JPAContainerItem container = (JPAContainerItem) itemClickEvent.getItem();
        Object obj = container.getEntity();

        if (obj instanceof Log) {
            logo = (Log) obj;
        }// fine del blocco if

        if (logo == null) {
            return;
        }// fine del blocco if

        if (titoloColonna.equals(COLUMN_DESC)) {
            clickOnDescrizione(logo);
        }// fine del blocco if
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
        String tag2 = "Le mappe della voce";
        String tag3 = "Singolo download";
        String tag4 = "sul server la voce";
        String title = "";

        if (desc != null) {
            if (desc.contains(tag1) || desc.contains(tag2)|| desc.contains(tag3)) {
                title = VaadWiki.estraeDoppiaQuadra(desc);
                if (!title.equals("")) {
//                    this.getUI().getPage().open(BioMod.WIKI_URL + title, "_blank"); @todo abilitare
                }// fine del blocco if
            }// fine del blocco if
            if (desc.contains(tag4)) {
                title = VaadWiki.estraeDoppiaQuadra(desc);
                if (!title.equals("")) {
                    esegueUltimaModifica(title);
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if
    }// end of method


    /**
     * Apre la differenza di versioni con l'ultima modifica
     */
    private void esegueUltimaModifica(String title) {
//        Bio bio = Bio.findTitle(title);
//        long revId = 0;
//        long parentId = 0;
//
//        if (bio != null) {
//            title = bio.getTitle();
//            revId = bio.getRevid();
//            parentId = bio.getParentid();
//        }// end of if cycle
//
//        if (title != null && revId > 0 && parentId > 0) {
//            this.getUI().getPage().open(Bio.WIKI_DIFF_HEADER + title + Bio.WIKI_DIFF_NEW + revId + Bio.WIKI_DIFF_OLD + parentId, "_blank");
//        }// end of if cycle
    }// end of method

}// end of class

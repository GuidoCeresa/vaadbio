package it.algos.vaadbio.download;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.wrapperbio.WrapBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/**
 * Download di un pacchetto (500) di pages
 * <p>
 * Scarica il pacchetto dal server
 * Esegue un ciclo e crea/modifica ogni singola pagina
 */
public class DownloadPages {

    //--container statico per il database
    public static EntityManager MANAGER = null;

    private ArrayList<Page> pages; // di norma 500
    private int numVociRegistrate = 0;
    private ArrayList<WrapBio> wraps;

    /**
     * Costruttore
     *
     * @param bloccoPageids lista (pageids) di pagine da scaricare dal server wiki
     */
    public DownloadPages(ArrayList<Long> bloccoPageids) {
        doInit(bloccoPageids);
    }// end of constructor

    /**
     * @param bloccoPageids lista (pageids) di pagine da scaricare dal server wiki
     */
    private void doInit(ArrayList<Long> bloccoPageids) {
        creaContainer();
        if (Pref.getBool(CostBio.USA_BLOCCO_COMMIT, true)) {
            doInitCommit(bloccoPageids);
        } else {
            doInitSenzaCommit(bloccoPageids);
        }// end of if/else cycle
    }// end of method

    /**
     * Crea l'Entity Manager
     * Crea il container di collegamento con il database
     */
    private void creaContainer() {
        MANAGER = EM.createEntityManager();
    }// end of method


//    /**
//     * crea i dati iniziali
//     */
//    private void createData() {
//        long inizio;
//        long fine;
//        EntityManager entityManager = EM.createEntityManager();
//
//        LazyEntityContainer<Bio> entityContainer = new LazyEntityContainer<Bio>(entityManager, Bio.class, 100, "id", true, true, true);
//        entityContainer.addContainerProperty("id", Long.class, 0L, true, true);
//
////        final Company company = new Company();
////        company.setName("test-company");
////        final Author author = new Author();
////        author.setName("test-author");
////        author.setCompany(company);
//        Bio entity = null;
//        inizio = System.currentTimeMillis();
//        int numVociRegistrate = 1000;
//        for (int i = 0; i < numVociRegistrate; i++) {
//            entity = entityContainer.addEntity();
//            entity.setTitle("task-" + Integer.toString(i));
//            for (ParBio par : ParBio.values()) {
//                par.setBio(entity, "pippoz");
//            } // fine del ciclo for-each
//        }
//
//        entityContainer.commit();
//        fine = System.currentTimeMillis();
//        Log.setInfo("test", "creaData " + LibNum.format(numVociRegistrate) + " nuove voci in " + LibNum.format(fine - inizio));
//
//    }

    /**
     * @param bloccoPageids lista (pageids) di pagine da scaricare dal server wiki
     */
    private void doInitCommit(ArrayList<Long> bloccoPageids) {
        long inizio = 0;
        long fine = 0;
        WrapBio wrap;

        inizio = System.currentTimeMillis();
        pages = Api.leggePages(bloccoPageids);
        Log.setDebug("test", "lettura di " + pages.size() + " pages in " + LibTime.difText(inizio));

        if (pages != null && pages.size() > 0) {
            wraps = new ArrayList<WrapBio>();
            inizio = System.currentTimeMillis();

            MANAGER.getTransaction().begin();
            for (Page page : pages) {
                wrap = new WrapBio(page, MANAGER);
                if (wrap.isRegistrata()) {
                    numVociRegistrate++;
                }// end of if cycle
                wraps.add(wrap);
            }// end of for cycle
            MANAGER.getTransaction().commit();

            fine = System.currentTimeMillis();
            Log.setDebug("test", "save ciclo con unico commit  " + LibNum.format(numVociRegistrate) + " nuove voci in " + LibNum.format(fine - inizio));
        }// end of if cycle
    }// end of method

    /**
     * @param bloccoPageids lista (pageids) di pagine da scaricare dal server wiki
     */
    private void doInitSenzaCommit(ArrayList<Long> bloccoPageids) {
        long inizio = 0;
        long fine = 0;
        WrapBio wrap;

        inizio = System.currentTimeMillis();
        pages = Api.leggePages(bloccoPageids);
        Log.setDebug("test", "lettura di " + pages.size() + " pages in " + LibTime.difText(inizio));

        if (pages != null && pages.size() > 0) {
            wraps = new ArrayList<WrapBio>();
            inizio = System.currentTimeMillis();

            for (Page page : pages) {
                wrap = new WrapBio(page, null);
                if (wrap.isRegistrata()) {
                    numVociRegistrate++;
                }// end of if cycle
                wraps.add(wrap);
            }// end of for cycle

            fine = System.currentTimeMillis();
            Log.setDebug("test", "save singolarmente senza commit " + LibNum.format(numVociRegistrate) + " nuove voci in " + LibNum.format(fine - inizio));
        }// end of if cycle
    }// end of method


    public ArrayList<Page> getPages() {
        return pages;
    }// end of getter method

    public int getNumVociRegistrate() {
        return numVociRegistrate;
    }// end of getter method

    public ArrayList<WrapBio> getWraps() {
        return wraps;
    }// end of getter method

}// end of class

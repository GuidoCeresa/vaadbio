package it.algos.vaadbio.download;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.wrapperbio.WrapBio;
import it.algos.webbase.domain.log.Log;
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
        WrapBio wrap;
        EntityManager manager = EM.createEntityManager();
        pages = Api.leggePages(bloccoPageids);

        long inizio = System.currentTimeMillis();

        if (pages != null && pages.size() > 0) {
            wraps = new ArrayList<WrapBio>();
            manager.getTransaction().begin();
            for (Page page : pages) {
                wrap = new WrapBio(page, manager);
                if (wrap.isRegistrata()) {
                    numVociRegistrate++;
                }// end of if cycle
                wraps.add(wrap);
            }// end of for cycle
        }// end of if cycle
        manager.getTransaction().commit();
        manager.close();
        Log.setInfo("test", "save " + LibNum.format(numVociRegistrate) + " nuove voci in " + LibTime.difText(inizio));
        int a = 87;
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

package it.algos.vaadbio.download;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.wrapperbio.WrapBio;

import java.util.ArrayList;

/**
 * Download di un pacchetto (500) di pages
 * <p>
 * Scarica il pacchetto dal server
 * Esegue un ciclo e crea/modifica ogni singola pagina
 */
public class DownloadPages {


    private ArrayList<Page> pages;
    private int numVociRegistrate = 0;


    /**
     * Costruttore
     *
     * @param listaPageids di pagine da scaricare dal server wiki
     */
    public DownloadPages(ArrayList<Long> listaPageids) {
        doInit(listaPageids);
    }// end of constructor


    /**
     * @param listaPageids di pagine da scaricare dal server wiki
     */
    private void doInit(ArrayList<Long> listaPageids) {
        pages = Api.leggePages(listaPageids);

        if (pages != null && pages.size() > 0) {
            for (Page page : pages) {
                if (new WrapBio(page).isRegistrata()) {
                    numVociRegistrate++;
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

    }// end of method


    public ArrayList<Page> getPages() {
        return pages;
    }// end of getter method

    public int getNumVociRegistrate() {
        return numVociRegistrate;
    }// end of getter method

}// end of class

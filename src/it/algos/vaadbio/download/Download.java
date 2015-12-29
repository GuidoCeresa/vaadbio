package it.algos.vaadbio.download;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.wrapperbio.WrapBio;

/**
 * Download della singola voce
 * <p>
 * Scarica la singola pagina dal server e crea/modifica il record di Bio
 */
public class Download {

    private Page page;
    private boolean registrata = false;

    /**
     * Costruttore
     *
     * @param pageid della voce da cui estrarre l'istanza bio (esistente)
     */
    public Download(long pageid) {
        this.doInit(pageid);
    }// end of constructor

    /**
     * Costruttore
     *
     * @param title della voce da cui estrarre l'istanza bio (esistente)
     */
    public Download(String title) {
        this.doInit(title);
    }// end of constructor


    /**
     * @param pageid della voce da cui estrarre l'istanza bio (esistente)
     */
    private void doInit(long pageid) {
        page = Api.leggePage(pageid);

        if (page != null) {
            registrata = new WrapBio(page,null).isRegistrata();
        }// end of if cycle

    }// end of method

    /**
     * @param title della voce da cui estrarre l'istanza bio (esistente)
     */
    private void doInit(String title) {
        page = Api.leggePage(title);

        if (page != null) {
            registrata = new WrapBio(page,null).isRegistrata();
        }// end of if cycle
    }// end of method

    public Page getPage() {
        return page;
    }// end of getter method

    public boolean isRegistrata() {
        return registrata;
    }// end of getter method

}// end of class

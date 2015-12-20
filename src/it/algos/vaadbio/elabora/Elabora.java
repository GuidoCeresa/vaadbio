package it.algos.vaadbio.elabora;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

/**
 * Elabora la singola voce
 * <p>
 * Invocato dalla classe Ciclo Elabora
 * Può essere invocato dal bottone 'Elabora' nel modulo Bio
 * <p>
 * Presuppone SEMPRE che esista già l'istanza bio scaricata (adesso o precedentemente) dal server Wiki
 * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
 * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
 * <p>
 * Elabora valori validi dei parametri significativi
 * <p>
 * Elabora i link alle tavole collegate
 * <p>
 * Registra l'istanza così modificata
 * <p>
 * L' upload della pagina NON viene fatto qui
 */
public class Elabora {

    private Bio bio = null;
    private boolean elaborata = false;


    /**
     * Costruttore
     *
     * @param pageid della voce da cui estrarre l'istanza bio (esistente)
     */
    public Elabora(long pageid) {
        Bio bio = null;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
        }// end of if cycle

        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore
     *
     * @param title della voce da cui estrarre l'istanza bio (esistente)
     */
    public Elabora(String title) {
        Bio bio = null;

        if (!title.equals("")) {
            bio = Bio.findByTitle(title);
        }// end of if cycle

        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore completo
     *
     * @param bio istanza da elaborare
     */
    public Elabora(Bio bio) {
        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
     * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
     * <p>
     * Elabora valori base di tutti i parametri
     * <p>
     * Elabora valori validi dei parametri significativi
     * <p>
     * Elabora i link alle tavole collegate
     * <p>
     * Registra l'istanza così modificata
     */
    private void doInit() {
        //--Costruisce il tmplBioStandard che serve per l'upload della singola voce sul server
        new ElaboraTemplate(bio);

        //--Elabora valori base di tutti i parametri
        new ElaboraParametri(bio);

        //--Elabora valori validi dei parametri significativi
        new ElaboraValidi(bio);

        //--Elabora i link alle tavole collegate
        new ElaboraLink(bio);

        try { // prova ad eseguire il codice
            bio.setUltimaElaborazione(LibTime.adesso());
            bio.save();
            elaborata = true;
        } catch (Exception unErrore) { // intercetta l'errore
            //--Recupera i dati dal record della tavola Wikibio
            if (Pref.getBool(CostBio.USA_LOG_ELABORA, true)) {
                Log.setDebug("elabora", "Non sono riuscito ad elaborare la voce " + LibWiki.setQuadre(bio.getTitle()));
            }// end of if cycle
        }// fine del blocco try-catch
    }// end of method


    public boolean isElaborata() {
        return elaborata;
    }// end of getter method

    public Bio getBio() {
        return bio;
    }// end of getter method

}// end of class

package it.algos.vaadbio;

import com.vaadin.ui.Upload;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Elabora la singola pagina
 * <p>
 * Controlla la equivalenza di tmplBioServer con tmplBioStandard
 * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
 */
public class ElaboraBio {

    private boolean upload;
    private boolean uploadata = false;

    public ElaboraBio(long pageid) {
        this(pageid, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(long pageid, boolean upload) {
        doInit(pageid, upload);
    }// end of constructor

    public ElaboraBio(Bio bio) {
        this(bio, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(Bio bio, boolean upload) {
        doInit(bio, upload);
    }// end of constructor

    /**
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
     *
     * @param pageid della voce da elaborare
     */
    private void doInit(long pageid, boolean upload) {
        Bio bio;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
            if (bio != null) {
                doInit(bio, upload);
            }// end of if cycle
        }// end of if cycle

    }// end of method

    /**
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
     *
     * @param bio da elaborare
     */
    private void doInit(Bio bio, boolean upload) {
        boolean templatesUguali;
        this.upload = upload;
        new ElaboraOnly(bio);

        if (upload) {
            templatesUguali = bio.isTemplatesUguali();
            if (!templatesUguali) {
//                new Upload(bio);
//                new DownloadBio(bio.getPageid());
//                new ElaboraOnly(bio);
            }// end of if cycle

        }// end of if cycle

    }// end of method

    public boolean isUploadata() {
        return uploadata;
    }// end of getter method
}// end of class

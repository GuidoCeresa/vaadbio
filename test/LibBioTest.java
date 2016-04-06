import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gac on 04 apr 2016.
 * .
 */
public class LibBioTest {

    private String previsto = "";
    private String ottenuto = "";

    @Test
    /**
     * Estrae la parte visibile di un link
     */
    public void estraeLink() {
        String paginaCompleta;

        paginaCompleta = "[[linkAPagina|visibile]]";
        previsto = "visibile";
        ottenuto = LibBio.estraeLink(paginaCompleta);
        assertEquals(ottenuto, previsto);

        paginaCompleta = "[[visibile]]";
        previsto = "visibile";
        ottenuto = LibBio.estraeLink(paginaCompleta);
        assertEquals(ottenuto, previsto);

        paginaCompleta = "linkAPagina|visibile";
        previsto = "visibile";
        ottenuto = LibBio.estraeLink(paginaCompleta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
      * Semplifica un link, se la parte visibile è uguale al link effettivo
      */
    public void fixLink() {
        String paginaCompleta;

        paginaCompleta = "[[linkAPagina|visibile]]";
        previsto = "[[linkAPagina|visibile]]";
        ottenuto = LibBio.fixLink(paginaCompleta);
        assertEquals(ottenuto, previsto);

        paginaCompleta = "[[visibile|visibile]]";
        previsto = "[[visibile]]";
        ottenuto = LibBio.fixLink(paginaCompleta);
        assertEquals(ottenuto, previsto);
    }// end of single test

}// end of test class
import it.algos.vaadbio.ElaboraOnly;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.elabora.Elabora;
import it.algos.vaadbio.lib.LibBio;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gac on 31 ott 2016.
 * .
 */
public class BioTest {

    private Bio bio;

    @Test
    public void pagina() {
        String titolo;

        titolo = "Pietro Ponzo";
        bio = LibBio.leggeBio(titolo);
        assertNotNull(bio);
        new Elabora(bio);

        titolo = "Eunjung";
        bio = LibBio.leggeBio(titolo);
        new ElaboraOnly(bio);
        assertNotNull(bio);

    }// end of single test

}// end of test class
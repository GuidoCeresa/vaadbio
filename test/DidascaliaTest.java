import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.didascalia.DidascaliaNatiGiorno;
import it.algos.vaadbio.lib.LibBio;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gac on 07 feb 2016.
 * .
 */
public class DidascaliaTest {

    private static String TITOLO_VOCE_BIOGRAFICA = "Silvio Spaventa";
    private Bio bio;

    @Before
    @SuppressWarnings("all")
    // Setup logic here
    public void setUp() {
        bio = new Bio();
        bio.setTitle("Silvio Spaventa");
        bio.setNome("Silvio");
        bio.setCognome("Spaventa");
        bio.setLuogoNascita("Bomba");
        bio.setLuogoNascitaLink("Bomba (Italia)");
        bio.setGiornoMeseNascita("12 maggio");
        bio.setAnnoNascita("1822");
        bio.setLuogoMorte("Roma");
        bio.setGiornoMeseMorte("20 giugno");
        bio.setAnnoMorte("1893");
        bio.setAttivita("politico");
        bio.setAttivita2("patriota");
        bio.setAttivita("italiano");
    } // fine del metodo iniziale

    @Test
    public void didascaliaGiornoNato() {
        String didascaliaGiornoNato = new DidascaliaNatiGiorno(bio).getTesto();
        System.out.println(didascaliaGiornoNato);
    }// end of single test

}// end of test class
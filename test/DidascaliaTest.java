import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.didascalia.*;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.mese.Mese;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.vaadbio.nome.Nome;
import it.algos.vaadbio.secolo.Secolo;
import it.algos.webbase.web.lib.MeseEnum;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gac on 07 feb 2016.
 * .
 */
public class DidascaliaTest {

    private static String TITOLO_VOCE_BIOGRAFICA = "Silvio Spaventa";
    private String luogoNascita = "Bomba";
    private String luogoMorte = "Roma";
    private Bio bio;
    private Giorno giornoNatoPunta;
    private Giorno giornoMortoPunta;
    private Anno annoNatoPunta;
    private Anno annoMortoPunta;
    private Attivita attivitaPunta;
    private Attivita attivita2Punta;
    private Attivita attivita3Punta;
    private Nazionalita nazionalitaPunta;
    private Nome nomePunta;

    @Before
    @SuppressWarnings("all")
    // Setup logic here
    public void setUp() {
        giornoNatoPunta = new Giorno("12 maggio", 133, Mese.findByTitoloBreve("mag"));
        giornoMortoPunta = new Giorno("20 giugno", 172, Mese.findByTitoloBreve("giu"));
//        annoNatoPunta = new Anno("1822", 3822, Secolo.findByTitolo());
//        annoMortoPunta = new Anno("1893", 3893, Secolo.XIX);
        attivitaPunta = new Attivita("politico", "politici");
        attivita2Punta = new Attivita("patriota", "patrioti");
        nazionalitaPunta = new Nazionalita("italiano", "italiani");
        nomePunta = new Nome("Silvio", true, false, null);
        bio = new Bio();
        bio.setTitle("Silvio Spaventa");
        bio.setNome("Silvio");
        bio.setCognome("Spaventa");
        bio.setLuogoNascita(luogoNascita);
        bio.setLuogoNascitaLink("Bomba (Italia)");
        bio.setGiornoNatoPunta(giornoNatoPunta);
        bio.setAnnoNatoPunta(annoNatoPunta);
        bio.setLuogoMorte(luogoMorte);
        bio.setGiornoMortoPunta(giornoMortoPunta);
        bio.setAnnoMortoPunta(annoMortoPunta);
        bio.setAttivitaPunta(attivitaPunta);
        bio.setAttivita2Punta(attivita2Punta);
        bio.setNazionalitaPunta(nazionalitaPunta);

        System.out.println();
    } // fine del metodo iniziale

    @Test
    public void didascaliaGiornoNato() {
        String didascaliaGiornoNato = new DidascaliaNatiGiorno(bio).getTesto();
        System.out.println("didascaliaGiornoNato: " + didascaliaGiornoNato);

        bio.setGiornoNatoPunta(null);
        didascaliaGiornoNato = new DidascaliaNatiGiorno(bio).getTesto();
        System.out.println("didascaliaGiornoNato mancante: " + didascaliaGiornoNato);
    }// end of single test

    @Test
    public void didascaliaGiornoMorto() {
        String didascaliaGiornoMorto = new DidascaliaMortiGiorno(bio).getTesto();
        System.out.println("didascaliaGiornoMorto: " + didascaliaGiornoMorto);

        bio.setGiornoMortoPunta(null);
        didascaliaGiornoMorto = new DidascaliaMortiGiorno(bio).getTesto();
        System.out.println("didascaliaGiornoMorto mancante: " + didascaliaGiornoMorto);
    }// end of single test

    @Test
    public void didascaliaAnnoNato() {
        String didascaliaAnnoNato = new DidascaliaNatiAnno(bio).getTesto();
        System.out.println("didascaliaAnnoNato: " + didascaliaAnnoNato);

        bio.setAnnoNatoPunta(null);
        didascaliaAnnoNato = new DidascaliaNatiAnno(bio).getTesto();
        System.out.println("didascaliaAnnoNato mancante: " + didascaliaAnnoNato);
    }// end of single test

    @Test
    public void didascaliaAnnoMorto() {
        String didascaliaAnnoMorto = new DidascaliaMortiAnno(bio).getTesto();
        System.out.println("didascaliaAnnoMorto: " + didascaliaAnnoMorto);

        bio.setAnnoMortoPunta(null);
        didascaliaAnnoMorto = new DidascaliaMortiAnno(bio).getTesto();
        System.out.println("didascaliaAnnoMorto mancante: " + didascaliaAnnoMorto);
    }// end of single test

    @Test
    public void didascaliaListe() {
        String didascaliaListe;
        System.out.println("16 esempi con tutte le combinazioni di luogoNascita, annoNascita, òuogoMorte, annoMorte");

        didascaliaListe = getDidascalia(false, false, false, false);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, false, false, false);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, true, false, false);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, true, false, false);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, false, true, false);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, false, true, false);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, true, true, false);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, true, true, false);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, false, false, true);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, false, false, true);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, true, false, true);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, true, false, true);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, false, true, true);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, false, true, true);
        System.out.println(didascaliaListe);

        didascaliaListe = getDidascalia(false, true, true, true);
        System.out.println(didascaliaListe);
        didascaliaListe = getDidascalia(true, true, true, true);
        System.out.println(didascaliaListe);


    }// end of single test


    private String getDidascalia(boolean isLuogoNascita, boolean isAnnoNascita, boolean isLuogoMorte, boolean isAnnoMorte) {

        if (isLuogoNascita) {
            bio.setLuogoNascita(luogoNascita);
        } else {
            bio.setLuogoNascita("");
        }// end of if/else cycle

        if (isAnnoNascita) {
            bio.setAnnoNatoPunta(annoNatoPunta);
        } else {
            bio.setAnnoNatoPunta(null);
        }// end of if/else cycle

        if (isLuogoMorte) {
            bio.setLuogoMorte(luogoMorte);
        } else {
            bio.setLuogoMorte("");
        }// end of if/else cycle

        if (isAnnoMorte) {
            bio.setAnnoMortoPunta(annoMortoPunta);
        } else {
            bio.setAnnoMortoPunta(null);
        }// end of if/else cycle

        return new DidascaliaListe(bio).getTesto();
    }// end of single test

}// end of test class
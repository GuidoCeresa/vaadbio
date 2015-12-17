import it.algos.vaadbio.elabora.ElaboraTemplate;
import it.algos.vaadbio.lib.ParBio;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Elabora i contenuti di un template Bio
 * <p>
 * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
 * Crea un templateStandard con i parametri
 */
public class ElaboraTemplateTest {

    private final static String TMPL_SERVER_UNO = "{{Bio\n" +
            "|Nome = Ludovica\n" +
            "|Cognome = Caramis\n" +
            "|Sesso = F\n" +
            "|LuogoNascita = Roma\n" +
            "|GiornoMeseNascita = 29 luglio\n" +
            "|AnnoNascita = 1991\n" +
            "|Attività = showgirl\n" +
            "|Nazionalità = italiana\n" +
            "}}";
    private final static int SIZE_SERVER_UNO = 8;

    private final static String TMPL_STANDARD_UNO = "{{Bio\n" +
            "|Nome = Ludovica\n" +
            "|Cognome = Caramis\n" +
            "|Sesso = F\n" +
            "|LuogoNascita = Roma\n" +
            "|GiornoMeseNascita = 29 luglio\n" +
            "|AnnoNascita = 1991\n" +
            "|LuogoMorte = \n" +
            "|GiornoMeseMorte = \n" +
            "|AnnoMorte = \n" +
            "|Attività = showgirl\n" +
            "|Nazionalità = italiana\n" +
            "}}";
    private final static int SIZE_STANDARD_UNO = 11;


    private HashMap<String, String> mappaOttenuta;
    private ElaboraTemplate elabora;
    private String templateOttenuto;


    @Test
    /**
     * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
     */
    public void estraeMappa() {
        int size;

        elabora = new ElaboraTemplate(TMPL_SERVER_UNO);
        assertNotNull(elabora);
        assertTrue(elabora.isValido());
        mappaOttenuta = elabora.getMappaBio();
        assertNotNull(mappaOttenuta);
        size = mappaOttenuta.size();
        assertEquals(size, SIZE_SERVER_UNO);
        assertEquals(mappaOttenuta.get(ParBio.nome.getTag()), "Ludovica");
        assertEquals(mappaOttenuta.get(ParBio.cognome.getTag()), "Caramis");
        assertEquals(mappaOttenuta.get(ParBio.sesso.getTag()), "F");
        assertEquals(mappaOttenuta.get(ParBio.luogoNascita.getTag()), "Roma");
        assertEquals(mappaOttenuta.get(ParBio.giornoMeseNascita.getTag()), "29 luglio");
        assertEquals(mappaOttenuta.get(ParBio.annoNascita.getTag()), "1991");
        assertEquals(mappaOttenuta.get(ParBio.attivita.getTag()), "showgirl");
        assertEquals(mappaOttenuta.get(ParBio.nazionalita.getTag()), "italiana");

    }// end of single test


    @Test
    /**
     * Crea un templateStandard con i parametri
     */
    public void creaTemplateStandard() {
        templateOttenuto = new ElaboraTemplate(TMPL_SERVER_UNO).getTmplBioStandard();
        assertNotNull(templateOttenuto);
        assertEquals(templateOttenuto, TMPL_STANDARD_UNO);
    }// end of method

}// end of test class
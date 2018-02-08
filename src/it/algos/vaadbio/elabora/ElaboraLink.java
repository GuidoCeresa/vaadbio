package it.algos.vaadbio.elabora;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.vaadbio.nome.Nome;

import java.util.Date;

/**
 * Created by gac on 19 dic 2015.
 * .
 */
public class ElaboraLink {


    /**
     * Costruttore
     *
     * @param bio istanza da elaborare
     */
    public ElaboraLink(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    //--Elabora i link alle tavole collegate
    private void doInit(Bio bio) {

        if (bio != null) {
            fixGiornoNatoPunta(bio);
            fixGiornoMortoPunta(bio);
            fixAnnoNatoPunta(bio);
            fixAnnoMortoPunta(bio);
            fixAttivita(bio);
            fixAttivita2(bio);
            fixAttivita3(bio);
            fixNazionalita(bio);
            fixNome(bio);

//            bioGrails.attivitaLink = AttivitaService.getAttivita(bioGrails.attivita)
//            bioGrails.attivita2Link = AttivitaService.getAttivita(bioGrails.attivita2)
//            bioGrails.attivita3Link = AttivitaService.getAttivita(bioGrails.attivita3)
//            bioGrails.nazionalitaLink = NazionalitaService.getNazionalita(bioGrails.nazionalita)
//            bioGrails.luogoNatoLink = LocalitaService.getLuogoNascita(bioWiki)
//            bioGrails.luogoMortoLink = LocalitaService.getLuogoMorte(bioWiki)
//            bioGrails.nomeLink = antroponimoService.getAntroponimo(bioWiki.nome)
//            bioGrails.cognomeLink = cognomeService.getCognome(bioWiki.cognome)
//            bioGrails.save(flush: false)
        }// fine del blocco if
    }// end of method


    private void fixGiornoNatoPunta(Bio bio) {
        Giorno giorno = null;
        String giornoTestoValido;

        if (bio != null) {
            giornoTestoValido = bio.getGiornoMeseNascitaValido();
            giorno = getGiorno(giornoTestoValido);
            bio.setGiornoNatoPunta(giorno);
        }// fine del blocco if
    } // fine del metodo

    private void fixGiornoMortoPunta(Bio bio) {
        Giorno giorno = null;
        String giornoTestoValido;

        if (bio != null) {
            giornoTestoValido = bio.getGiornoMeseMorteValido();
            giorno = getGiorno(giornoTestoValido);
            bio.setGiornoMortoPunta(giorno);
        }// fine del blocco if
    } // fine del metodo


    private Giorno getGiorno(String giornoTestoValido) {
        Giorno giorno = null;

        if (giornoTestoValido != null && !giornoTestoValido.equals(CostBio.VUOTO)) {
            giorno = Giorno.findByTitolo(giornoTestoValido);
            if (giorno == null) {
                giorno = Giorno.findByTitolo(giornoTestoValido);
            }// end of if cycle
        }// fine del blocco if

        return giorno;
    } // fine del metodo


    private void fixAnnoNatoPunta(Bio bio) {
        Anno anno = null;
        String annoTestoValido;

        if (bio != null) {
            annoTestoValido = bio.getAnnoNascitaValido();
            anno = getAnno(annoTestoValido);
            bio.setAnnoNatoPunta(anno);
        }// fine del blocco if
    } // fine del metodo

    private void fixAnnoMortoPunta(Bio bio) {
        Anno anno = null;
        String annoTestoValido;
        int deltaOrdinamento = 2000;
        int deltaData = 1900;
        int ordinamento;
        int annoNumero;
        long ora = System.currentTimeMillis();
        Date oggi = new Date(ora);
        int annoCorrente = oggi.getYear() + deltaData;

        if (bio != null) {
            annoTestoValido = bio.getAnnoMorteValido();
            anno = getAnno(annoTestoValido);

            if (anno != null) {
                ordinamento = anno.getOrdinamento();
                annoNumero = ordinamento - deltaOrdinamento;
                if (annoNumero > annoCorrente) {
                    bio.setAnnoMortoPunta(null);
                } else {
                    bio.setAnnoMortoPunta(anno);
                }// end of if/else cycle
            }// end of if cycle

        }// fine del blocco if
    } // fine del metodo

    private Anno getAnno(String annoTestoValido) {
        Anno anno = null;

        if (annoTestoValido != null && !annoTestoValido.equals(CostBio.VUOTO)) {
            anno = Anno.findByTitolo(annoTestoValido);
        }// fine del blocco if

        return anno;
    } // fine del metodo

    private void fixAttivita(Bio bio) {
        Attivita attivita = null;
        String attivitaValida;

        if (bio != null) {
            attivitaValida = bio.getAttivitaValida();
            attivita = getAttivita(attivitaValida);
            bio.setAttivitaPunta(attivita);
        }// fine del blocco if
    } // fine del metodo

    private void fixAttivita2(Bio bio) {
        Attivita attivita = null;
        String attivitaValida;

        if (bio != null) {
            attivitaValida = bio.getAttivita2Valida();
            attivita = getAttivita(attivitaValida);
            bio.setAttivita2Punta(attivita);
        }// fine del blocco if
    } // fine del metodo

    private void fixAttivita3(Bio bio) {
        Attivita attivita = null;
        String attivitaValida;

        if (bio != null) {
            attivitaValida = bio.getAttivita3Valida();
            attivita = getAttivita(attivitaValida);
            bio.setAttivita3Punta(attivita);
        }// fine del blocco if
    } // fine del metodo

    private Attivita getAttivita(String attivitaValida) {
        Attivita attivita = null;

        if (attivitaValida != null && !attivitaValida.equals(CostBio.VUOTO)) {
            attivita = Attivita.findBySingolare(attivitaValida);
        }// fine del blocco if

        return attivita;
    } // fine del metodo


    private void fixNazionalita(Bio bio) {
        Nazionalita nazionalita = null;
        String nazionalitaValida;

        if (bio != null) {
            nazionalitaValida = bio.getNazionalitaValida();
            if (nazionalitaValida != null && !nazionalitaValida.equals(CostBio.VUOTO)) {
                nazionalita = Nazionalita.findBySingolare(nazionalitaValida);
            }// fine del blocco if
            bio.setNazionalitaPunta(nazionalita);
        }// fine del blocco if
    } // fine del metodo


    private void fixNome(Bio bio) {
        Nome nome = null;
        String nomeValido;

        if (bio != null) {
            nomeValido = bio.getNomeValido();
            if (nomeValido != null && !nomeValido.equals(CostBio.VUOTO)) {
                nome = Nome.getEntityByNome(nomeValido);
            }// fine del blocco if
            bio.setNomePunta(nome);
        }// fine del blocco if
    } // fine del metodo

}// end of class

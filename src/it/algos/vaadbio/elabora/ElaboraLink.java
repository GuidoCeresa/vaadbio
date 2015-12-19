package it.algos.vaadbio.elabora;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.LibBio;

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

        if (giornoTestoValido != null && !giornoTestoValido.equals("")) {
            giorno = Giorno.findByNome(giornoTestoValido);
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

        if (bio != null) {
            annoTestoValido = bio.getAnnoMorteValido();
            anno = getAnno(annoTestoValido);
            bio.setAnnoMortoPunta(anno);
        }// fine del blocco if
    } // fine del metodo

    private Anno getAnno(String annoTestoValido) {
        Anno anno = null;

        if (annoTestoValido != null && !annoTestoValido.equals("")) {
            anno = Anno.findByNome(annoTestoValido);
        }// fine del blocco if

        return anno;
    } // fine del metodo

}// end of class

package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.LibBio;

/**
 * Elabora valori validi dei parametri significativi
 */
public class ElaboraValidi {

    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraValidi(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    //--Elabora valori validi dei parametri significativi
    private void doInit(Bio bio) {

        if (bio != null) {
            fixGiornoNatoValido(bio);
            fixGiornoMortoValido(bio);
            fixAnnoNatoValido(bio);
            fixAnnoMortoValido(bio);

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


    private void fixGiornoNatoValido(Bio bio) {
        String giornoValido = "";
        String giornoGrezzo = "";

        if (bio != null) {
            giornoGrezzo = bio.getGiornoMeseNascita();
            giornoValido = giornoGrezzo.trim();
            bio.setGiornoMeseNascitaValido(giornoValido);
        }// fine del blocco if
    } // fine del metodo

    private void fixGiornoMortoValido(Bio bio) {
        String giornoValido = "";
        String giornoGrezzo = "";

        if (bio != null) {
            giornoGrezzo = bio.getGiornoMeseMorte();
            giornoValido = giornoGrezzo.trim();
            bio.setGiornoMeseMorteValido(giornoValido);
        }// fine del blocco if
    } // fine del metodo

    private String getGiornoValido(String giornoGrezzo) {
        String giornoValido = "";

        if (giornoGrezzo == null || giornoGrezzo.equals("") || !giornoGrezzo.contains(" ")) {
            return "";
        }// end of if cycle

        giornoGrezzo = LibBio.fixCampoGiorno(giornoGrezzo);
        giornoValido = Giorno.fix(giornoGrezzo);

        return giornoValido;
    } // fine del metodo

    private void fixAnnoNatoValido(Bio bio) {
        String annoValido = "";
        String annoGrezzo = "";

        if (bio != null) {
            annoGrezzo = bio.getAnnoNascita();

            annoValido = annoGrezzo.trim();
            bio.setAnnoNascitaValido(annoValido);
        }// fine del blocco if
    } // fine del metodo

    private void fixAnnoMortoValido(Bio bio) {
        String annoValido = "";
        String annoGrezzo = "";

        if (bio != null) {
            annoGrezzo = bio.getAnnoMorte();

            annoValido = annoGrezzo.trim();
            bio.setAnnoMorteValido(annoValido);
        }// fine del blocco if
    } // fine del metodo

}// end of class

package it.algos.vaadbio.lib;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;

/**
 * Created by gac on 28 set 2015.
 * .
 */
public enum ParBio {

    titolo("Titolo", true, false, false, false, false, Bio_.titolo) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setTitolo((String) value);
            } else {
                bio.setTitolo(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getTitolo();
        }// end of method
    },// end of single enumeration
    nome("Nome", true, true, true, false, false, Bio_.nome) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNome((String) value);
            } else {
                bio.setNome(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNome();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setNomeValido(LibBio.fixCampo(istanza.getNome()));
        }// end of method
    },// end of single enumeration
    cognome("Cognome", true, true, true, false, false, Bio_.cognome) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setCognome((String) value);
            } else {
                bio.setCognome(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getCognome();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setCognomeValido(LibBio.fixCampo(istanza.getCognome()));
        }// end of method
    },// end of single enumeration
    cognomePrima("CognomePrima", false, false, false, false, false, Bio_.cognomePrima) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setCognomePrima((String) value);
            } else {
                bio.setCognomePrima(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getCognomePrima();
        }// end of method
    },// end of single enumeration
    postCognome("PostCognome", false, false, false, false, false, Bio_.postCognome) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPostCognome((String) value);
            } else {
                bio.setPostCognome(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPostCognome();
        }// end of method
    },// end of single enumeration
    postCognomeVirgola("PostCognomeVirgola", false, false, false, false, false, Bio_.postCognomeVirgola) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPostCognomeVirgola((String) value);
            } else {
                bio.setPostCognomeVirgola(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPostCognomeVirgola();
        }// end of method
    },// end of single enumeration
    forzaOrdinamento("ForzaOrdinamento", false, false, false, false, false, Bio_.forzaOrdinamento) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setForzaOrdinamento((String) value);
            } else {
                bio.setForzaOrdinamento(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getForzaOrdinamento();
        }// end of method
    },// end of single enumeration
    preData("PreData", false, false, false, false, false, Bio_.preData) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPreData((String) value);
            } else {
                bio.setPreData(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPreData();
        }// end of method
    },// end of single enumeration
    sesso("Sesso", true, true, true, false, false, Bio_.sesso) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setSesso((String) value);
            } else {
                bio.setSesso(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getSesso();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setSessoValido(LibBio.fixCampoSesso(istanza.getSesso()));
        }// end of method
    },// end of single enumeration


    luogoNascita("LuogoNascita", true, true, true, false, false, Bio_.luogoNascita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoNascita((String) value);
            } else {
                bio.setLuogoNascita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getLuogoNascita();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setLuogoNascitaValido(LibBio.fixCampoLuogo(istanza.getLuogoNascita()));
        }// end of method
    },// end of single enumeration
    luogoNascitaLink("LuogoNascitaLink", false, false, true, false, false, Bio_.luogoNascitaLink) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoNascitaLink((String) value);
            } else {
                bio.setLuogoNascitaLink(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getLuogoNascitaLink();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setLuogoNascitaLinkValido(LibBio.fixCampoLuogo(istanza.getLuogoNascitaLink()));
        }// end of method
    },// end of single enumeration
    luogoNascitaAlt("LuogoNascitaAlt", false, false, false, false, false, Bio_.luogoNascitaAlt) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoNascitaAlt((String) value);
            } else {
                bio.setLuogoNascitaAlt(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        public String getValue(Bio bio) {
            return bio.getLuogoNascitaAlt();
        }// end of method
    },// end of single enumeration
    giornoMeseNascita("GiornoMeseNascita", true, true, true, false, false, Bio_.giornoMeseNascita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setGiornoMeseNascita((String) value);
            } else {
                bio.setGiornoMeseNascita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getGiornoMeseNascita();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setGiornoMeseNascitaValido(LibBio.fixGiornoValido(istanza.getGiornoMeseNascita()));
        }// end of method
    },// end of single enumeration
    annoNascita("AnnoNascita", true, true, true, false, false, Bio_.annoNascita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAnnoNascita((String) value);
            } else {
                bio.setAnnoNascita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        public String getValue(Bio bio) {
            return bio.getAnnoNascita();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setAnnoNascitaValido(LibBio.fixAnnoValido(istanza.getAnnoNascita()));
        }// end of method
    },// end of single enumeration
    noteNascita("NoteNascita", false, false, false, false, false, Bio_.noteNascita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNoteNascita((String) value);
            } else {
                bio.setNoteNascita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNoteNascita();
        }// end of method
    },// end of single enumeration


    luogoMorte("LuogoMorte", true, true, true, false, false, Bio_.luogoMorte) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoMorte((String) value);
            } else {
                bio.setLuogoMorte(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getLuogoMorte();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setLuogoMorteValido(LibBio.fixCampoLuogo(istanza.getLuogoMorte()));
        }// end of method
    },// end of single enumeration
    luogoMorteLink("LuogoMorteLink", false, false, true, false, false, Bio_.luogoMorteLink) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoMorteLink((String) value);
            } else {
                bio.setLuogoMorteLink(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getLuogoMorteLink();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setLuogoMorteLinkValido(LibBio.fixCampoLuogo(istanza.getLuogoMorteLink()));
        }// end of method
    },// end of single enumeration
    luogoMorteAlt("LuogoMorteAlt", false, false, false, false, false, Bio_.luogoMorteAlt) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setLuogoMorteAlt((String) value);
            } else {
                bio.setLuogoMorteAlt(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getLuogoMorteAlt();
        }// end of method
    },// end of single enumeration
    giornoMeseMorte("GiornoMeseMorte", true, true, true, false, false, Bio_.giornoMeseMorte) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setGiornoMeseMorte((String) value);
            } else {
                bio.setGiornoMeseMorte(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getGiornoMeseMorte();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setGiornoMeseMorteValido(LibBio.fixGiornoValido(istanza.getGiornoMeseMorte()));
        }// end of method
    },// end of single enumeration
    annoMorte("AnnoMorte", true, true, true, false, false, Bio_.annoMorte) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAnnoMorte((String) value);
            } else {
                bio.setAnnoMorte(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getAnnoMorte();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setAnnoMorteValido(LibBio.fixAnnoValido(istanza.getAnnoMorte()));
        }// end of method
    },// end of single enumeration
    noteMorte("NoteMorte", false, false, false, false, false, Bio_.noteMorte) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNoteMorte((String) value);
            } else {
                bio.setNoteMorte(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNoteMorte();
        }// end of method
    },// end of single enumeration


    epoca("Epoca", false, false, false, false, false, Bio_.epoca) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setEpoca((String) value);
            } else {
                bio.setEpoca(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getEpoca();
        }// end of method
    },// end of single enumeration
    epoca2("Epoca2", false, false, false, false, false, Bio_.epoca2) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setEpoca2((String) value);
            } else {
                bio.setEpoca2(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getEpoca2();
        }// end of method
    },// end of single enumeration
    preAttivita("PreAttività", false, false, false, false, false, Bio_.preAttivita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPreAttivita((String) value);
            } else {
                bio.setPreAttivita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPreAttivita();
        }// end of method
    },// end of single enumeration
    attivita("Attività", true, true, true, false, false, Bio_.attivita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAttivita((String) value);
            } else {
                bio.setAttivita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getAttivita();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setAttivitaValida(LibBio.fixCampo(istanza.getAttivita()));
        }// end of method
    },// end of single enumeration
    attivita2("Attività2", false, false, true, false, false, Bio_.attivita2) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAttivita2((String) value);
            } else {
                bio.setAttivita2(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getAttivita2();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setAttivita2Valida(LibBio.fixCampo(istanza.getAttivita2()));
        }// end of method
    },// end of single enumeration
    attivita3("Attività3", false, false, true, false, false, Bio_.attivita3) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAttivita3((String) value);
            } else {
                bio.setAttivita3(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getAttivita3();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setAttivita3Valida(LibBio.fixCampo(istanza.getAttivita3()));
        }// end of method
    },// end of single enumeration
    attivitaAltre("AttivitàAltre", false, false, false, false, false, Bio_.attivitaAltre) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setAttivitaAltre((String) value);
            } else {
                bio.setAttivitaAltre(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getAttivitaAltre();
        }// end of method
    },// end of single enumeration


    nazionalita("Nazionalità", true, true, true, false, false, Bio_.nazionalita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNazionalita((String) value);
            } else {
                bio.setNazionalita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNazionalita();
        }// end of method

        @Override
        public void setBioValida(Bio istanza) {
            istanza.setNazionalitaValida(LibBio.fixCampo(istanza.getNazionalita()));
        }// end of method
    },// end of single enumeration
    cittadinanza("Cittadinanza", false, false, false, false, false, Bio_.cittadinanza) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setCittadinanza((String) value);
            } else {
                bio.setCittadinanza(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getCittadinanza();
        }// end of method
    },// end of single enumeration
    nazionalitaNaturalizzato("NazionalitàNaturalizzato", false, false, false, false, false, Bio_.nazionalitaNaturalizzato) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNazionalitaNaturalizzato((String) value);
            } else {
                bio.setNazionalitaNaturalizzato(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNazionalitaNaturalizzato();
        }// end of method
    },// end of single enumeration
    postNazionalita("PostNazionalità", false, false, false, false, false, Bio_.postNazionalita) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPostNazionalita((String) value);
            } else {
                bio.setPostNazionalita(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPostNazionalita();
        }// end of method
    },// end of single enumeration


    categorie("Categorie", false, false, false, false, false, Bio_.categorie) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setCategorie((String) value);
            } else {
                bio.setCategorie(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getCategorie();
        }// end of method
    },// end of single enumeration
    fineIncipit("FineIncipit", false, false, false, false, false, Bio_.fineIncipit) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setFineIncipit((String) value);
            } else {
                bio.setFineIncipit(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getFineIncipit();
        }// end of method
    },// end of single enumeration
    punto("Punto", false, false, false, false, false, Bio_.punto) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setPunto((String) value);
            } else {
                bio.setPunto(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getPunto();
        }// end of method
    },// end of single enumeration
    immagine("Immagine", false, false, false, false, false, Bio_.immagine) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setImmagine((String) value);
            } else {
                bio.setImmagine(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getImmagine();
        }// end of method
    },// end of single enumeration
    didascalia("Didascalia", false, false, false, false, false, Bio_.didascalia) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setDidascalia((String) value);
            } else {
                bio.setDidascalia(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getDidascalia();
        }// end of method
    },// end of single enumeration
    didascalia2("Didascalia2", false, false, false, false, false, Bio_.didascalia2) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setDidascalia2((String) value);
            } else {
                bio.setDidascalia2(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getDidascalia2();
        }// end of method
    },// end of single enumeration
    dimImmagine("DimImmagine", false, false, false, false, false, Bio_.dimImmagine) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setDimImmagine((String) value);
            } else {
                bio.setDimImmagine(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getDimImmagine();
        }// end of method
    },// end of single enumeration


    nomeValido("nomeValido", false, false, false, true, false, Bio_.nomeValido) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setNomeValido((String) value);
            } else {
                bio.setNomeValido(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getNomeValido();
        }// end of method
    },// end of single enumeration
    cognomeValido("cognomeValido", false, false, false, true, false, Bio_.cognomeValido) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setCognomeValido((String) value);
            } else {
                bio.setCognomeValido(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getCognomeValido();
        }// end of method
    },// end of single enumeration
    sessoValido("sessoValido", false, false, false, true, false, Bio_.sessoValido) {
        @Override
        public Bio setBio(Bio bio, Object value) {
            if (value != null && value instanceof String) {
                bio.setSessoValido((String) value);
            } else {
                bio.setSessoValido(VUOTA);
            }// end of if/else cycle
            return bio;
        }// end of method

        @Override
        public String getValue(Bio bio) {
            return bio.getSessoValido();
        }// end of method
    };// end of last enumeration

    private static String VUOTA = CostBio.VUOTO;
    private String tag = "";
    private boolean visibileLista = false;
    private boolean campoNormale = false;
    private boolean campoSignificativo = false;
    private SingularAttribute<Bio, String> attributo;
    private boolean campoValido = false;
    private boolean campoPunta = false;

    ParBio(String tag, boolean visibileLista, boolean campoNormale, boolean campoSignificativo, boolean campoValido, boolean campoPunta, SingularAttribute<Bio, String> attributo) {
        this.setTag(tag);
        this.setVisibileLista(visibileLista);
        this.setCampoNormale(campoNormale);
        this.setCampoSignificativo(campoSignificativo);
        this.setCampoValido(campoValido);
        this.setCampoPunta(campoPunta);
        this.setAttributo(attributo);
    }// end of general constructor

    public static Attribute<?, ?>[] getCampiLista() {
        Attribute<?, ?>[] matrice;
        ArrayList<Attribute> lista = new ArrayList<Attribute>();

        lista.add(Bio_.pageid);
        lista.add(Bio_.title);
        for (ParBio par : ParBio.values()) {
            if (par.isVisibileLista()) {
                lista.add(par.getAttributo());
            }// fine del blocco if
        } // fine del ciclo for-each

        matrice = lista.toArray(new Attribute[lista.size()]);
        return matrice;
    }// end of method

    public static Attribute<?, ?>[] getCampiForm() {
        Attribute<?, ?>[] matrice;
        ArrayList<Attribute> lista = new ArrayList<Attribute>();

        lista.add(Bio_.pageid);
        lista.add(Bio_.title);
        for (ParBio par : ParBio.values()) {
            lista.add(par.getAttributo());
        } // fine del ciclo for-each

        matrice = lista.toArray(new Attribute[lista.size()]);
        return matrice;
    }// end of method

    public static Attribute<?, ?>[] getCampiValida() {
        Attribute<?, ?>[] matrice;
        ArrayList<Attribute> lista = new ArrayList<Attribute>();

        lista.add(Bio_.pageid);
        lista.add(Bio_.title);
        for (ParBio par : ParBio.values()) {
            if (par.isCampoSignificativo()) {
                lista.add(par.getAttributo());
            }// fine del blocco if
        } // fine del ciclo for-each

        matrice = lista.toArray(new Attribute[lista.size()]);
        return matrice;
    }// end of method

    public static ArrayList<ParBio> getCampiSignificativi() {
        ArrayList<ParBio> lista = new ArrayList<ParBio>();

        for (ParBio par : ParBio.values()) {
            if (par.isCampoSignificativo()) {
                lista.add(par);
            }// fine del blocco if
        } // fine del ciclo for-each

        return lista;
    }// end of method

    public static ArrayList<ParBio> getCampiNormali() {
        ArrayList<ParBio> lista = new ArrayList<ParBio>();

        for (ParBio par : ParBio.values()) {
            if (par.isCampoNormale()) {
                lista.add(par);
            }// fine del blocco if
        } // fine del ciclo for-each

        return lista;
    }// end of method

    public static ArrayList<ParBio> getCampiValidi() {
        ArrayList<ParBio> lista = new ArrayList<ParBio>();

        for (ParBio par : ParBio.values()) {
            if (par.isCampoValido()) {
                lista.add(par);
            }// fine del blocco if
        } // fine del ciclo for-each

        return lista;
    }// end of method

    public static ArrayList<ParBio> getCampiPunta() {
        ArrayList<ParBio> lista = new ArrayList<ParBio>();

        for (ParBio par : ParBio.values()) {
            if (par.isCampoPunta()) {
                lista.add(par);
            }// fine del blocco if
        } // fine del ciclo for-each

        return lista;
    }// end of method


    /**
     * Inserisce nell'istanza il valore passato come parametro
     * La property dell'istanza ha lo stesso nome della enumeration
     * DEVE essere sovrascritto (implementato)
     *
     * @param bio   istanza da regolare
     * @param value valore da inserire
     * @return istanza regolata
     */
    public abstract Bio setBio(Bio bio, Object value);

    /**
     * Recupera il valore del parametro da Originale
     * Inserisce il valore del parametro in Valida
     * La property dell'istanza ha lo stesso nome della enumeration
     * DEVE essere sovrascritto (implementato) per i campi campoSignificativo=true
     *
     * @param originale istanza da cui estrarre (elaborare) il valore del parametro
     * @param valida    istanza in cui inserire il valore del parametro
     */
    public void setBioValida(Bio istanza) {
    }// end of method

    /**
     * Recupera dall'istanza il valore
     * La property dell'istanza ha lo stesso nome della enumeration
     * DEVE essere sovrascritto (implementato)
     *
     * @param bio istanza da elaborare
     * @return value sotto forma di text
     */
    public abstract String getValue(Bio bio);

    /**
     * Recupera dall'istanza la key ed il valore
     * La property dell'istanza ha lo stesso nome della enumeration
     * DEVE essere sovrascritto (implementato)
     *
     * @param bio istanza da elaborare
     * @return testo della coppia key e value
     */
    public String getKeyValue(Bio bio) {
        String value = getValue(bio);

        if (!value.equals("") || this.isCampoNormale()) {
            return "|" + tag + " = " + value + "\n";
        } else {
            return "";
        }// end of if/else cycle
    }// end of method

    /**
     * Recupera dall'istanza la key ed il valore
     * La property dell'istanza ha lo stesso nome della enumeration
     *
     * @param value del parametro
     * @return testo della coppia key e value
     */
    public String getKeyValue(String value) {
        if (!value.equals("") || this.isCampoNormale()) {
            return "|" + tag + " = " + value + "\n";
        } else {
            return "";
        }// end of if/else cycle
    }// end of method

    /**
     * Recupera il valore del parametro sesso da Originale
     * Inserisce il valore del parametro sesso in Valida
     *
     * @param originale istanza da cui estrarre (elaborare) il valore del parametro
     * @param valida    istanza in cui inserire il valore del parametro
     */
    public void setBioValidaSesso(Bio istanza) {
    }// end of method

    public String getTag() {
        return tag;
    }// end of getter method

    public void setTag(String tag) {
        this.tag = tag;
    }//end of setter method

    public boolean isVisibileLista() {
        return visibileLista;
    }// end of getter method

    public void setVisibileLista(boolean visibileLista) {
        this.visibileLista = visibileLista;
    }//end of setter method

    public boolean isCampoSignificativo() {
        return campoSignificativo;
    }// end of getter method

    public void setCampoSignificativo(boolean campoSignificativo) {
        this.campoSignificativo = campoSignificativo;
    }//end of setter method

    public SingularAttribute<Bio, String> getAttributo() {
        return attributo;
    }// end of getter method

    public void setAttributo(SingularAttribute<Bio, String> attributo) {
        this.attributo = attributo;
    }//end of setter method

    public boolean isCampoNormale() {
        return campoNormale;
    }// end of getter method

    public void setCampoNormale(boolean campoNormale) {
        this.campoNormale = campoNormale;
    }//end of setter method

    public boolean isCampoValido() {
        return campoValido;
    }// end of getter method

    public void setCampoValido(boolean campoValido) {
        this.campoValido = campoValido;
    }//end of setter method

    public boolean isCampoPunta() {
        return campoPunta;
    }// end of getter method

    public void setCampoPunta(boolean campoPunta) {
        this.campoPunta = campoPunta;
    }//end of setter method
}

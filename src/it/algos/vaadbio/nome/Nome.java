package it.algos.vaadbio.nome;


import it.algos.webbase.web.entity.BaseEntity;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Nome extends BaseEntity {

    @NotEmpty
    private String nome = "";


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Nome() {
    }// end of nullary constructor


    /**
     * Costruttore completo
     *
     * @param nome      del giorno
     */
    public Nome(String nome) {
        super();
        this.setNome(nome);
    }// end of general constructor


    public String getNome() {
        return nome;
    }// end of getter method


    public void setNome(String nome) {
        this.nome = nome;
    }//end of setter method


}// end of entity class

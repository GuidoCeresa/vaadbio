package it.algos.vaadbio.attivita;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Attivita.class)
public class Attivita_ extends BaseEntity_ {
	public static volatile SingularAttribute<Attivita, String> singolare;
	public static volatile SingularAttribute<Attivita, String> plurale;
}// end of entity class

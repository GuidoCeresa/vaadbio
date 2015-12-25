package it.algos.vaadbio.nazionalita;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Nazionalita.class)
public class Nazionalita_ extends BaseEntity_ {
	public static volatile SingularAttribute<Nazionalita, String> singolare;
	public static volatile SingularAttribute<Nazionalita, String> plurale;
}// end of entity class

package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate; // Importer LocalDate
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Registration implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long numRegistration;

	@Min(value = 1, message = "Le numéro de semaine doit être au moins 1")
	int numWeek;

	LocalDate startDate; // Ajout de l'attribut startDate

	@JsonIgnore
	@ManyToOne
	Skier skier;

	@JsonIgnore
	@ManyToOne
	Course course;
}
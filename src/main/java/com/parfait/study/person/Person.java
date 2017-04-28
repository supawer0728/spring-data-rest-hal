package com.parfait.study.person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Person {
	@Id
	@GeneratedValue
	Long seq;
	String name;
	Integer age;
}

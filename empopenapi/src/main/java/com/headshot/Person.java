package com.headshot;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * 
 * @author nikheel.patel
 *
 */
@Schema(name = "Person", description = "simple pojo class representing Person")
public class Person {

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Schema(required = true, example = "abcd")
	public String name;

	@Schema(required = true, example = "20")
	public int age;
}

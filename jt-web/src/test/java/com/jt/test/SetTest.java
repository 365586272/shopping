package com.jt.test;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

public class SetTest {

	public static void main(String[] args) {
		Set<person> s=new HashSet<>();
        person p1=new person("熊大","男");	
         person p2=new person("熊三","男");	
        person p3=new person("熊二","男");	
        person p4=new person("熊二","男");	
	    s.add(p1);
	    s.add(p2);
	    s.add(p3);
	    for(person p:s) {
	    	System.out.println(p);
	    }
//		Set<String> set=new HashSet<>();
//		set.add("熊大");
//		set.add("熊二");
//		set.add("熊三");
//		set.add("熊四");
//		set.add("熊二");
//		set.forEach((m)->System.out.println(m));      
	}
	
	
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
class person{
	//private int id;
	private String name;
	private String sex;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		person other = (person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}
	
	
}

package com.poc.Model;

import java.beans.Transient;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Example {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = true, length = 64)
    private String photos;
	
//	private List<String> filePath;

	
	 @Transient
	    public String getPhotosImagePath() {
	        if (photos == null) {
	        	return null;
	        } 
	         
	        return "/user-photos/" + id + "/" + photos;
	    }
	
	
	
	
}

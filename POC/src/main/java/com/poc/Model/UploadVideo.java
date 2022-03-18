package com.poc.Model;
import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class UploadVideo{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   
   @Lob
   @Column
   private String videos; 
  

	 @Transient
	    public String getVideoImagePath() {
	        if (videos == null) {
	        	return null;
	        } 
	         
	        return "/user-video/" + id + "/" + videos;
	    }

   
}
package grupo3.retoFinalBD;

import javax.persistence.*;

@Entity  
@Table(name="prueba")   
public class PruebaEntity {
	
	@Id   
	private int id;    
	private String firstName,lastName;    
	    
	public int getId() {    
	    return id;    
	} 
	
	public void setId(int id) {    
	    this.id = id;    
	}
	
	public String getFirstName() {    
	    return firstName;    
	}
	
	public void setFirstName(String firstName) {    
	    this.firstName = firstName;    
	}
	
	public String getLastName() {    
	    return lastName;    
	}
	
	public void setLastName(String lastName) {    
	    this.lastName = lastName;    
	}    

}

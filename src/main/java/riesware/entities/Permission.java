package riesware.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Permission {
    
    @Id
    @GeneratedValue
    private Long id;
       
    private String permissionString; // permission string from google play

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPermissionString() {
        return permissionString;
    }

    public void setPermissionString(String permission_string) {
        this.permissionString = permission_string;
    }    
    
}

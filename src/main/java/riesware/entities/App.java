
package riesware.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class App {
        
    /*
     * id of the app inside google play, can be used for getting the url to
     * the app: https://play.google.com/store/apps/details?id={app_id}
     */
    
    @Id
    
    private String appId;
    
    private String name; // App name
    
    private String description; // app description
    
    private float price; // Price has to be converted from scraped string to 
    
    private String installations; // range of installations
    
    private String category; // App category
    
    private float average_rating; // average user rating for the app
    
    private int number_of_ratings; // number of user reviews for the app     

    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Permission> permissions;

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }
    
    public void addPermission (Permission p) {
        if (permissions == null) {
            permissions = new ArrayList<Permission>();
        }
        
        permissions.add(p);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String app_id) {
        this.appId = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInstallations() {
        return installations;
    }

    public void setInstallations(String installations) {
        this.installations = installations;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getAverageRating() {
        return average_rating;
    }

    public void setAverageRating(float average_rating) {
        this.average_rating = average_rating;
    }

    public int getNumberOfRatings() {
        return number_of_ratings;
    }

    public void setNumberOfRatings(int number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
    }
    
    
}

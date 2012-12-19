
package riesware.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import riesware.entities.App;

public interface AppRepository extends JpaRepository<App, String> {    
}


package riesware.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import riesware.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Long> {  
    
    public Permission findByPermissionString(String permissionString);
}

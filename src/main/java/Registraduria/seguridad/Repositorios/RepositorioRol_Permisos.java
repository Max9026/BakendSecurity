package Registraduria.seguridad.Repositorios;
import Registraduria.seguridad.Modelos.Rol_Permisos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioRol_Permisos extends MongoRepository<Rol_Permisos,String> {
    /**
    * @getPermiso Consulta realizada
     * Me trae un permiso segun la informacion que se va a enviar
     * */
    @Query("{'rol.$id': ObjectId(?0),'permiso.$id': ObjectId(?1)}")
    Rol_Permisos getPermisoRol(String id_rol,String id_permiso);
}

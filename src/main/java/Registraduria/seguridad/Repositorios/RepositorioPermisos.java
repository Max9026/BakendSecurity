package Registraduria.seguridad.Repositorios;
import Registraduria.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioPermisos extends MongoRepository<Permiso,String>{
    /**
     * @getPermiso
    * Trae permiso solicitado segun la consulta*/
    @Query("{'url':?0,'metodo':?1}")
    Permiso getPermiso(String url, String metodo);
}

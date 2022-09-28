package Registraduria.seguridad.Repositorios;
import Registraduria.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RepositorioRol extends MongoRepository<Rol,String> {
    List<Rol> findByNombre(String rol);
}
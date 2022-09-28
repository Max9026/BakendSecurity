package Registraduria.seguridad.Repositorios;
import Registraduria.seguridad.Modelos.Rol;
import Registraduria.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IRepositorioUsuario extends MongoRepository<Usuario,String>{
    /**
     * Se hace una consulta a la base de datos para saber si el correo existe en la base de datos
    */

    @Query("{'correo': ?0}")
    public Usuario getUserByEmail(String correo);
/**
 *@findByCorreo lo usamos para traer los correos de usarios y estos no se repitan
 *
 **/

    List<Usuario> findByCorreo(String correo);

    /**
     *@findBySeudonimo lo usamos para traer los seudonimos de usarios y estos no se repitan
     *
     **/

    List<Usuario> findBySeudonimo(String seudonimo);
}

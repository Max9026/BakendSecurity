package Registraduria.seguridad.Controladores;

import Registraduria.seguridad.Modelos.Rol;
import Registraduria.seguridad.Modelos.Usuario;
import Registraduria.seguridad.Repositorios.IRepositorioUsuario;
import Registraduria.seguridad.Repositorios.RepositorioRol;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CrossOrigin                                     // Decorador que permite las transacciones al servidor desde el mismo computador, de no tenerlo se obtendira un problema de  CORS
@RestController                                  //Decorador que permite definir que esta clase servirá como puerta de entrada al servidor para llevar a cabo todas las tareas del CRUD
@RequestMapping("/roles")                          // Decorador que permite definir la sub ruta de acceso para activar los metodos programados en la clase
public class ControladorRol {
    @Autowired
    //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private RepositorioRol miRepositorioRol;    //parametro de la clase que servira para llevar a cabo transacciones desde el controlador a la base de datos

    @Autowired
    private IRepositorioUsuario miRepositorioUsuario;

    @GetMapping("")
    // Decorador que define que mediante una peticion GET se active el metodo index para obtener todos los elementos mediante el metodo findAll()
    public List<Rol> index() {
        return this.miRepositorioRol.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    // Decorador que define que mediante una peticion POST se realice la creacion de un nuevo objeto que se guardara en la BD, al activar el metodo create
    public Rol create(@RequestBody Rol infoRol) {
        if (infoRol.getNombre() == null || infoRol.getDescripcion() == null) {   // el decorador @RequestBody permite recibir la informacion del permiso en JSON
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos");                           //el almacenamiento en la bd se realiza mediante el metodo save

        }
        return this.miRepositorioRol.save(infoRol);
    }


    @GetMapping("{id}")
    public Rol show(@PathVariable String id) {                                //el metodo show permite mostrar la informacion de un solo elemento de la coleccion
        Rol RolActual = this.miRepositorioRol.findById(id).orElse(null);
        if (RolActual == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no existe");
        }

        return RolActual;
    }

    @PutMapping("{id}")
    public Rol update(@PathVariable String id, @RequestBody Rol infoRol) { //el metodo update permite la actualizacion de un registro en la coleccion
        Rol RolActual = this.miRepositorioRol.findById(id).orElse(null);
        if (RolActual != null) {
            RolActual.setNombre(infoRol.getNombre()); //el metodo set permite la actualizacion de los parametros
            RolActual.setDescripcion(infoRol.getDescripcion());

            return this.miRepositorioRol.save(RolActual);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"rol no existe");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Rol RolActual = this.miRepositorioRol.findById(id).orElse(null);
        if (RolActual != null) {
            List<Usuario> users = this.miRepositorioUsuario.findAll();
            System.out.println(users);
            for (Usuario item : users) {
                System.out.println(item);
                if (item.getRol().getId().equals(RolActual.getId())) {

                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar, tiene Usuarios asignados a este rol");
                }

            }
        this.miRepositorioRol.delete(RolActual);
            throw new ResponseStatusException(HttpStatus.OK,"Eliminacion Exitosa");

        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No Existe ese Rol");
        }
    }
}


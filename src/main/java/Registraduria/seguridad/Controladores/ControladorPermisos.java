package Registraduria.seguridad.Controladores;

import Registraduria.seguridad.Modelos.Permiso;
import Registraduria.seguridad.Repositorios.RepositorioPermisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin // Decorador que permite las transacciones al servidor desde el mismo computador, de no tenerlo se obtendira un problema de  CORS
@RestController //Decorador que permite definir que esta clase servirá como puerta de entrada al servidor para llevar a cabo todas las tareas del CRUD
@RequestMapping("/permisos") // Decorador que permite definir la sub ruta de acceso para activar los metodos programados en la clase
public class ControladorPermisos {
    @Autowired //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private RepositorioPermisos miRepositorioPermiso; //parametro de la clase que servira para llevar a cabo transacciones desde el controlador a la base de datos

    @GetMapping("") // Decorador que define que mediante una peticion GET se active el metodo index para obtener todos los elementos mediante el metodo findAll()
    public List<Permiso> index(){
        return this.miRepositorioPermiso.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping    // Decorador que define que mediante una peticion POST se realice la creacion de un nuevo objeto que se guardara en la BD, al activar el metodo create
        public Permiso create(@RequestBody Permiso infoPermiso){                                                    // el decorador @RequestBody permite recibir la informacion del permiso en JSON
       if (infoPermiso.getUrl() == null||infoPermiso.getNombre() == null||infoPermiso.getMetodo()==null){             //el almacenamiento en la bd se realiza mediante el metodo save
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos");

        }
        return this.miRepositorioPermiso.save(infoPermiso);
    }

    @GetMapping("{id}")
    public Permiso show(@PathVariable String id){ //el metodo show permite mostrar la informacion de un solo elemento de la coleccion
        Permiso permisoActual=this.miRepositorioPermiso.findById(id).orElse(null);
        if(permisoActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "permiso no existe");

        }


        return permisoActual;
    }

    @PutMapping("{id}")
    public Permiso update(@PathVariable String id, @RequestBody Permiso infoPermiso){ //el metodo update permite la actualizacion de un registro en la coleccion
        Permiso permisoActual=this.miRepositorioPermiso.findById(id).orElse(null);
        if(permisoActual!=null){
            permisoActual.setMetodo(infoPermiso.getMetodo()); //el metodo set permite la actualizacion de los parametros
            permisoActual.setNombre(infoPermiso.getNombre());
            permisoActual.setUrl(infoPermiso.getUrl());

            return this.miRepositorioPermiso.save(permisoActual);

        }else{

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"permiso no existe");

        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Permiso permisoActual=this.miRepositorioPermiso.findById(id).orElse(null);
        if (permisoActual!=null){
            this.miRepositorioPermiso.delete(permisoActual);

            throw new ResponseStatusException(HttpStatus.OK,"Eliminacion Exitosa");
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Existe ese Permiso");
        }

    }
}

package Registraduria.seguridad.Controladores;

import Registraduria.seguridad.Modelos.Permiso;
import Registraduria.seguridad.Modelos.Rol;
import Registraduria.seguridad.Modelos.Rol_Permisos;
import Registraduria.seguridad.Repositorios.RepositorioRol_Permisos;
import Registraduria.seguridad.Repositorios.RepositorioRol;
import Registraduria.seguridad.Repositorios.RepositorioPermisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CrossOrigin                                                            // Decorador que permite las transacciones al servidor desde el mismo computador, de no tenerlo se obtendira un problema de  CORS
@RestController                                                         //Decorador que permite definir que esta clase servirá como puerta de entrada al servidor para llevar a cabo todas las tareas del CRUD
@RequestMapping("/rolpermisos")                                         // Decorador que permite definir la sub ruta de acceso para activar los metodos programados en la clase
public class ControladorRol_Permisos {
    @Autowired                                                          //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private RepositorioRol_Permisos miRepositorioRol_Permisos;          //parametro de la clase que servira para llevar a cabo transacciones desde el controlador a la base de datos

    @Autowired                                                          //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private RepositorioRol miRepositorioRol;

    @Autowired                                                          //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private RepositorioPermisos miRepositorioPermisos;
    @GetMapping("")                                                     // Decorador que define que mediante una peticion GET se active el metodo index para obtener todos los elementos mediante el metodo findAll()
    public List<Rol_Permisos> index(){
        return this.miRepositorioRol_Permisos.findAll();
    }

    /**
     * Asignación rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public Rol_Permisos create (@PathVariable String id_rol,@PathVariable String id_permiso){
        Rol_Permisos nuevo=new Rol_Permisos ();
        Rol elRol=this.miRepositorioRol.findById(id_rol).orElse(null);
        Permiso elPermiso=this.miRepositorioPermisos.findById(id_permiso).orElse(null);

        if (elRol!=null && elPermiso!=null){
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.miRepositorioRol_Permisos.save(nuevo);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol o permiso inexistente");
        }
    }


    @GetMapping("{id}")
    public Rol_Permisos show(@PathVariable String id){                          //el metodo show permite mostrar la informacion de un solo elemento de la coleccion
        Rol_Permisos Rol_PermisosActual=this.miRepositorioRol_Permisos.findById(id).orElse(null);
        return Rol_PermisosActual;
    }

     @PutMapping("{id}/roles/{id_rol}/permiso/{id_permiso}")
     public Rol_Permisos update(@PathVariable String id,@PathVariable String id_rol, @PathVariable String id_permiso){ //el metodo update permite la actualizacion de un registro en la coleccion
        Rol_Permisos RolPermisoActual=this.miRepositorioRol_Permisos.findById(id).orElse(null);
        Rol unRol=this.miRepositorioRol.findById(id_rol).orElse(null);
        Permiso unPermiso=this.miRepositorioPermisos.findById(id_permiso).get();
        if(RolPermisoActual!=null && unPermiso!=null && unRol!=null) {

            RolPermisoActual.setPermiso(unPermiso);                              //el metodo set permite la actualizacion de los parametros
            RolPermisoActual.setRol(unRol);

            return this.miRepositorioRol_Permisos.save(RolPermisoActual);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol o permiso inexistente");

        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Rol_Permisos Rol_PermisosActual=this.miRepositorioRol_Permisos.findById(id).orElse(null);
        if (Rol_PermisosActual!=null){
            this.miRepositorioRol_Permisos.delete(Rol_PermisosActual);
            throw new ResponseStatusException(HttpStatus.OK,"Eliminacion Exitosa");
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Existe ese Rol Permiso");
        }
    }
    @GetMapping("validar-permiso/rol/{id_rol}")
    public Rol_Permisos getPermiso(@PathVariable String id_rol,@RequestBody Permiso infoPermiso){
        Permiso elPermiso=this.miRepositorioPermisos.getPermiso(infoPermiso.getUrl(),infoPermiso.getMetodo());
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        if (elPermiso!=null && elRol!=null){
            return
                    this.miRepositorioRol_Permisos.getPermisoRol(elRol.getId(),elPermiso.getId());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
        }
    }

}

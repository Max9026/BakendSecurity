package Registraduria.seguridad.Controladores;

import Registraduria.seguridad.Modelos.Rol;
import Registraduria.seguridad.Modelos.Usuario;
import Registraduria.seguridad.Repositorios.IRepositorioUsuario;
import Registraduria.seguridad.Repositorios.RepositorioRol;

import java.io.IOException;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin // Decorador que permite las transacciones al servidor desde el mismo computador, de no tenerlo se obtendira un problema de  CORS
@RestController //Decorador que permite definir que esta clase servirá como puerta de entrada al servidor para llevar a cabo todas las tareas del CRUD
@RequestMapping("/usuarios") // Decorador que permite definir la sub ruta de acceso para activar los metodos programados en la clase
public class ControladorUsuario {
    @Autowired //Decorador que permite inyectar la dependencia del objeto implícitamente. Utiliza internamente inyección de setter o constructor
    private IRepositorioUsuario simpleMongoRepositorioUsuario; //parametro de la clase que servira para llevar a cabo transacciones desde el controlador a la base de datos

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("") // Decorador que define que mediante una peticion GET se active el metodo index para obtener todos los elementos mediante el metodo findAll()
    public List<Usuario> index(){
        List<Usuario> all = this.simpleMongoRepositorioUsuario.findAll();

        return all;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping // Decorador que define que mediante una peticion POST se realice la creacion de un nuevo objeto que se guardara en la BD, al activar el metodo create
    public Usuario create(@RequestBody  Usuario infoUsuario){ // el decorador @RequestBody permite recibir la informacion del usuario en JSON
        if (infoUsuario.getSeudonimo() == null||infoUsuario.getContrasena() == null||infoUsuario.getCorreo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos");
        }
        if(infoUsuario.getRol()==null){
        var rolCiudadano = this.miRepositorioRol.findByNombre("Ciudadano").get(0);
        infoUsuario.setRol(rolCiudadano);
        }
        else{
            var rolUsuario = this.miRepositorioRol.findById(infoUsuario.getRol().getId()).orElse(null);
            if(rolUsuario==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ROL no existe");
            }else {
                infoUsuario.setRol(rolUsuario);

            }    }
        var correos = this.simpleMongoRepositorioUsuario.findByCorreo(infoUsuario.getCorreo());
        var seudonimos = this.simpleMongoRepositorioUsuario.findBySeudonimo(infoUsuario.getSeudonimo());
        if(correos.isEmpty()){
            infoUsuario.setCorreo(infoUsuario.getCorreo());
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo ya registrado");
        }
        if(seudonimos.isEmpty()) {
            infoUsuario.setSeudonimo(infoUsuario.getSeudonimo());
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "seudonimo ya registrado");
        }
        infoUsuario.setContrasena(encriptarContraseña(infoUsuario.getContrasena()));
        return this.simpleMongoRepositorioUsuario.save(infoUsuario);
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){ //el metodo show permite mostrar la informacion de un solo elemento de la coleccion
        Usuario usuarioActual=this.simpleMongoRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no existe");

        }
        return usuarioActual;

    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody  Usuario infoUsuario){ //el metodo update permite la actualizacion de un registro en la coleccion
        Usuario usuarioActual=this.simpleMongoRepositorioUsuario.findById(id).orElse(null);
        if (infoUsuario.getSeudonimo() == null||infoUsuario.getContrasena() == null||infoUsuario.getCorreo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos");
        }
        if(usuarioActual!=null){
            var correos = this.simpleMongoRepositorioUsuario.findByCorreo(infoUsuario.getCorreo());
            var seudonimos = this.simpleMongoRepositorioUsuario.findBySeudonimo(infoUsuario.getSeudonimo());
            if(correos.isEmpty()||seudonimos.isEmpty()){
                infoUsuario.setCorreo(infoUsuario.getCorreo());
                infoUsuario.setSeudonimo(infoUsuario.getSeudonimo());
            }
            else{

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo o seudonimo ya registrado");
            }

            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo()); //el metodo set permite la actualizacion de los parametros
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(encriptarContraseña(infoUsuario.getContrasena()));
            return this.simpleMongoRepositorioUsuario.save(usuarioActual);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO existe el Usuario a actualizar");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){

        Usuario usuarioActual=this.simpleMongoRepositorioUsuario.findById(id).orElse(null);

        if (usuarioActual!=null){
            this.simpleMongoRepositorioUsuario.delete(usuarioActual);
            throw new ResponseStatusException(HttpStatus.OK,"Eliminacion Exitosa");
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Existe ese Usuario");
        }
    }
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id,@PathVariable String id_rol){
        Usuario usuarioActual=this.simpleMongoRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario solicitado no existe");
        }
        Rol rolActual=this.miRepositorioRol.findById(id_rol).orElse(null);
        if(rolActual==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El rol solicitado no existe");
        }

        usuarioActual.setRol(rolActual);
        return this.simpleMongoRepositorioUsuario.save(usuarioActual);


    }
    public String encriptarContraseña(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    @PostMapping("/validate")
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException {
        Usuario usuarioActual=this.simpleMongoRepositorioUsuario.getUserByEmail(infoUsuario.getCorreo());
        if (usuarioActual!=null && usuarioActual.getContrasena().equals(encriptarContraseña(infoUsuario.getContrasena()))) {
            usuarioActual.setContrasena("");
            return usuarioActual;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}

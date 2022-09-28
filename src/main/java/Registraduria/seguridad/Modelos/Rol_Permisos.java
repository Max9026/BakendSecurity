package Registraduria.seguridad.Modelos;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data //Decorador usado para definir que la clase sera almacenada en la DB mongo
@Document //Decorador usado para definir que la clase sera almacenada en la DB mongo
public class Rol_Permisos {
    @Id
    private String id;
    @DBRef
    private Rol rol;
    @DBRef
    private Permiso permiso;

    public Rol_Permisos() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}



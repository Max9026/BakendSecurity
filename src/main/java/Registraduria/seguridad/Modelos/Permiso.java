package Registraduria.seguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data //Decorador usado para definir que la clase sera almacenada en la DB mongo
@Document //Decorador usado para definir que la clase sera almacenada en la DB mongo
public class Permiso {
    @Id //Decorador que le da a entender a Spring Boot que ese será el campo único del documento en la colección de la base de datos.
    private String id;
    private String url;
    private String nombre;
    private String metodo;


// Constructor de la clase Permisos
    public Permiso(String url, String metodo, String nombre) {
        this.url = url;
        this.metodo = metodo;
        this.nombre = nombre;
     }
// Metodos Getter y setter de la clase Permisos

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

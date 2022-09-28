package Registraduria.seguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



@Data //Decorador usado para definir que la clase sera almacenada en la DB mongo
@Document //Decorador usado para definir que la clase sera almacenada en la DB mongo
public class Rol {
    @Id
//Decorador que le da a entender a Spring Boot que ese será el campo único del documento en la colección de la base de datos.
    private String id;
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }



}
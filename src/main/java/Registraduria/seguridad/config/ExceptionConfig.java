package Registraduria.seguridad.config;


import Registraduria.seguridad.config.exception.BadRequestException;
import Registraduria.seguridad.config.exception.InternalServerErrorException;
import Registraduria.seguridad.config.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;


@ControllerAdvice(annotations = RestController.class)   //informa que será un controlador responsable del manejo de los errores indicados
public class ExceptionConfig {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
         public ResponseEntity<?> inernalServerErrorhandle(Exception e) {


             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
         }



}


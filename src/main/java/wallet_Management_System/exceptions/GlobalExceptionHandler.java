package wallet_Management_System.exceptions;


import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            StaleObjectStateException.class
    })
    public ResponseEntity<Error> handleOptimisticClock(Exception ex){

        Error error= new Error("Another transaction is happening , please try again");
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }
}

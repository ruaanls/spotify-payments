package br.com.payments.spotify.infra.web.handler;

import br.com.payments.spotify.application.exception.ErrorClassException;
import br.com.payments.spotify.application.exception.PaymentData;
import br.com.payments.spotify.application.exception.PaymentProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionGlobalHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(PaymentProcess.class)
    private ResponseEntity<ErrorClassException> processPaymentFailed(PaymentProcess exception)
    {
        ErrorClassException Exception = new ErrorClassException(HttpStatus.UNPROCESSABLE_ENTITY,exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Exception);
    }

    @ExceptionHandler(PaymentData.class)
    private ResponseEntity<ErrorClassException> paymentNotFound(PaymentProcess exception)
    {
        ErrorClassException Exception = new ErrorClassException(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Exception);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorClassException> genericExceptionHandler(Exception exception)
    {
        ErrorClassException error = new ErrorClassException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}

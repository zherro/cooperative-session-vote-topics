package br.com.southsystem.cooperative.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> tratarBadCredentialsException(ResourceNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorDetails error = ErrorDetails.builder()
                .code(status.value())
                .title("Resource Not Found")
                .msg(ex.getMessage())
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {

        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ex.printStackTrace();

        ErrorDetails error = ErrorDetails.builder()
                .code(status.value())
                .title("Erro de sistema!")
                .msg(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .title("Recurso não encontrado")
                .msg(String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL()))
                .build();

        return this.handleExceptionInternal(ex, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null) {
            body = ErrorDetails.builder()
                    .title(status.getReasonPhrase())
                    .msg(ex.getMessage())
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
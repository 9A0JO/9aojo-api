package br.com.fiap.abctechapi.handler;
import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.handler.exception.MaxAssistsException;
import br.com.fiap.abctechapi.handler.exception.MinimumAssistsRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> error(Exception exception) {
        return getErrorMessageResponseEntity(exception.getMessage(), exception.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageResponse>> errorValidationException(MethodArgumentNotValidException exception) {
        return handler(exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageResponse> errorArgumentInvalid() {
        return getErrorMessageResponseEntity("Invalid argument", "o valor do argumento passado para o método é inválido", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> errorEntityNotFound(IdNotFoundException exception) {
        return getErrorMessageResponseEntity(exception.getMessage(), exception.getDescription(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MinimumAssistsRequiredException.class)
    public ResponseEntity<ErrorMessageResponse> errorMinAssistRequired(MinimumAssistsRequiredException exception) {
        return getErrorMessageResponseEntity(exception.getMessage(), exception.getDescription(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxAssistsException.class)
    public ResponseEntity<ErrorMessageResponse> errorMaxAssistException(MaxAssistsException exception) {
        return getErrorMessageResponseEntity(exception.getMessage(), exception.getDescription(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorMessageResponse> getErrorMessageResponseEntity(String message, String description, HttpStatus statusCode) {
        ErrorMessageResponse error = createErrorMessageResponse(message, description, statusCode);
        return new ResponseEntity<>(error, statusCode);
    }

    private ResponseEntity<List<ErrorMessageResponse>> handler(MethodArgumentNotValidException exception) {
        List<ErrorMessageResponse> listError = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String description = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorMessageResponse error = createErrorMessageResponse("A propriedade " + e.getField(), description, HttpStatus.BAD_REQUEST);
            listError.add(error);
        });

        return new ResponseEntity<>(listError, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessageResponse createErrorMessageResponse(String message, String description, HttpStatus statusCode) {
        ErrorMessageResponse error = new ErrorMessageResponse();
        error.setMessage(message);
        error.setDescription(description);
        error.setStatusCode(statusCode.value());
        error.setTimestamp(new Date());
        return error;
    }



}

package application.controllers;

import application.dtos.response.ApiResponse;
import domain.Exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice intercepta excepciones de TODOS los controllers.
// Sin esto, cualquier error devuelve un HTTP 500 genérico.
// Con esto, BusinessException → HTTP 400 con mensaje legible.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores de negocio (cliente no existe, saldo insuficiente, etc.)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }

    // Cualquier otro error inesperado → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error interno del servidor: " + ex.getMessage()));
    }
}

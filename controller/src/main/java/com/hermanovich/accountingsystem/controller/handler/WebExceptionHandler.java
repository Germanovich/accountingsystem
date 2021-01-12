package com.hermanovich.accountingsystem.controller.handler;

import com.hermanovich.accountingsystem.common.exception.ApplicationException;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.common.exception.DaoException;
import com.hermanovich.accountingsystem.dto.exception.ExceptionDto;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(MessageForUser.MESSAGE_REQUEST_METHOD_NOT_SUPPORTED.get());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(MessageForUser.MESSAGE_MEDIA_TYPE_NOT_SUPPORTED.get());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(MessageForUser.MESSAGE_MEDIA_TYPE_NOT_ACCEPTABLE.get());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageForUser.MESSAGE_SERVER_ERROR.get());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageForUser.MESSAGE_SERVER_ERROR.get());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageForUser.MESSAGE_SERVER_ERROR.get());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageForUser.MESSAGE_BAD_REQUEST.get());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(MessageForUser.MESSAGE_NO_HANDLER_FOUND.get());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest webRequest) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(MessageForUser.MESSAGE_SERVICE_UNAVAILABLE.get());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageForUser.MESSAGE_SERVER_ERROR.get());
    }

    @ExceptionHandler(DaoException.class)
    protected ResponseEntity<ExceptionDto> onDaoException(DaoException daoException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getExceptionDto(daoException));
    }


    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ExceptionDto> onApplicationException(ApplicationException applicationException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getExceptionDto(applicationException));
    }


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ExceptionDto> onBusinessException(BusinessException businessException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getExceptionDto(businessException));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(getExceptionDto(ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(getExceptionDto(MessageForUser.MESSAGE_NOT_ENOUGH_RIGHTS.get()));
    }

    private ExceptionDto getExceptionDto(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return exceptionDto;
    }

    private ExceptionDto getExceptionDto(String message) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(message);
        log.error(message);
        return exceptionDto;
    }
}

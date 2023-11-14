package ivanovvasil.u5d5w2Project.exceptions;

import ivanovvasil.u5d5w2Project.payloads.exceptionsDTO.ErrorsListResponseDTO;
import ivanovvasil.u5d5w2Project.payloads.exceptionsDTO.ErrorsResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsListResponseDTO handleBadRequest(BadRequestException e) {
    if (e.getErrorList() != null) {
      List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
      return new ErrorsListResponseDTO(e.getMessage(), new Date(), errorsList);
    } else {
      return new ErrorsListResponseDTO(e.getMessage(), new Date(), new ArrayList<>());
    }
  }


  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleNotFound(NotFoundException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleMethodArgumentNotValidException(BadRequestException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(AccesDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorsResponseDTO handleAnauthorized(AccesDeniedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorsResponseDTO handleGeneric(Exception e) {
    e.printStackTrace();
    return new ErrorsResponseDTO("we are sorry at the moment we have some internal problems, we are trying to resolve them", new Date());
  }
}


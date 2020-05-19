package nsu.shserg.AutopartsDatabaseServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Not enough storage!")
public class CellNotEnoughStorageException extends RuntimeException {
}

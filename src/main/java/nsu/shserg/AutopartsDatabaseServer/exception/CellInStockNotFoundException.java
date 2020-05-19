package nsu.shserg.AutopartsDatabaseServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such cell!")
public class CellInStockNotFoundException extends RuntimeException {
}

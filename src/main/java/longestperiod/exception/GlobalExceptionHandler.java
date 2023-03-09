package longestperiod.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler
{
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileUploadException e)
    {
        return ResponseEntity.notFound().build();
    }
}

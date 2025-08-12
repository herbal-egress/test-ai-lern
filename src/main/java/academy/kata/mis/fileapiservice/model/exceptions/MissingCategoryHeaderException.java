package academy.kata.mis.fileapiservice.model.exceptions;

public class MissingCategoryHeaderException extends RuntimeException {
    public MissingCategoryHeaderException(String message) {
        super(message);
    }
}
package exceptions;

public class ADTException extends InterpreterException{
    public ADTException(){
        super();
    }

    public ADTException(String err_msg) {
        super(err_msg);
    }
}

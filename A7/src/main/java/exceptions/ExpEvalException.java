package exceptions;

public class ExpEvalException extends InterpreterException{
    public ExpEvalException(){
        super();
    }

    public ExpEvalException(String err_msg){
        super(err_msg);
    }
}

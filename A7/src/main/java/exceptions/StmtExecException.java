package exceptions;

public class StmtExecException extends InterpreterException{
    public StmtExecException(){
        super();
    }

    public StmtExecException(String err_msg){
        super(err_msg);
    }
}

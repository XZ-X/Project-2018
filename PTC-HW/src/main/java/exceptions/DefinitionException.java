package exceptions;

import java.io.IOException;

/**
 * @author XiangzheXu
 * create-time: 2018/12/28
 */
public class DefinitionException extends IOException {
    public DefinitionException(String message) {
        super(message);
    }
}

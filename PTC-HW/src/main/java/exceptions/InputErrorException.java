package exceptions;

import java.io.IOException;

/**
 * @author XiangzheXu
 * create-time: 2018/12/29
 */
public class InputErrorException extends IOException {
    public InputErrorException(String message) {
        super(message);
    }
}

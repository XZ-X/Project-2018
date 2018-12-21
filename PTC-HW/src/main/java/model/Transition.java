package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
public class Transition {
    private Map<Domain, Range> tf = new HashMap<>();

    /**
     * add definition to tf
     *
     * @param domain from
     * @param range  to
     */
    public void addTransition(Domain domain, Range range) {
        tf.put(domain, range);
    }

    /**
     * move one step
     *
     * @param curState current state
     * @param tape     type of the TM
     * @return new state
     */
    public String transition(String curState, Tape tape) {
        char tapeSymbol = tape.read();
        Domain domain = new Domain();
        domain.setState(curState);
        domain.setTapeSymbol(tapeSymbol);
        Range range = tf.get(domain);
        tape.write(range.getToWrite());
        switch (range.getDirection()) {
            case LEFT:
                tape.left();
                break;
            case RIGHT:
                tape.right();
                break;
            default:
                throw new RuntimeException();
        }
        return range.getNextState();
    }

}



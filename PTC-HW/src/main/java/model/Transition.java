package model;

import exceptions.HaltException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
@Data
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
    public String transition(String curState, Tape tape) throws HaltException {
        char tapeSymbol = tape.read();
        Domain domain = new Domain();
        domain.setState(curState);
        domain.setTapeSymbol(tapeSymbol);

        Range range = tf.get(domain);

        if (range == null) {
            // if the transition doesn't contains the specific
            domain.setTapeSymbol('*');
            range = tf.get(domain);
        }

        if (range == null) {
            throw new HaltException("Unknown transition function of domain " + domain);
        }

        //modify the tape
        tape.write(range.getToWrite());
        //move the header
        switch (range.getDirection()) {
            case LEFT:
                tape.left();
                break;
            case RIGHT:
                tape.right();
                break;
            case STAY:
                break;
            default:
                throw new RuntimeException();
        }
        return range.getNextState();
    }

}



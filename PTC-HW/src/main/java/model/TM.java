package model;

import lombok.Data;

import java.util.Set;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
@Data
public class TM {

    private Transition transition;

    private Tape tape;

    private Set<String> stateSet;

    private Set<String> acceptingState;

    private String initState;

    private String currentState;

    private Integer pc = 0;

    public TM(Transition transition, Tape tape, Set<String> stateSet, String initState, Set<String> finalState) {
        this.transition = transition;
        this.tape = tape;
        this.stateSet = stateSet;
        this.initState = initState;
        this.currentState = initState;
        this.acceptingState = finalState;
    }

    public void reset(String input) throws InputErrorException {
        this.tape.reset(input);
        this.currentState = initState;
        this.pc = 0;
    }
}

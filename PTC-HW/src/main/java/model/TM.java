package model;

import exceptions.HaltException;
import exceptions.InputErrorException;
import lombok.Data;

import java.io.PrintWriter;
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

    public void run(PrintWriter writer) throws HaltException {
        printID(writer);
        while (!acceptingState.contains(currentState)) {
            try {
                currentState = transition.transition(currentState, tape);
                pc++;
            } catch (HaltException e) {
                tape.printResult(writer);
                writer.println("==================== END ====================");
                throw e;
            }
            printID(writer);
        }
        tape.printResult(writer);
        writer.println("==================== END ====================");
    }

    private void printID(PrintWriter writer) {
        writer.println("Step  : " + pc);
        tape.printID(writer);
        writer.println("State : " + currentState);
        writer.println("---------------------------------------------");

    }
}

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author XiangzheXu
 * create-time: 2018/12/28
 */
public class InputProcessor {
    private Set<String> stateSet = new HashSet<>();
    private Set<Character> inputCharSet = new HashSet<>();
    private Set<Character> tapeCharSet = new HashSet<>();
    private String q0 = null;
    private Set<String> finalStates = new HashSet<>();
    private Transition transition = new Transition();

    /**
     * build the TM from the input file
     *
     * @param file the input file
     * @return the TM
     */
    public TM parseInput(File file) throws DefinitionException {

        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == ';') {
                    continue;
                }
                Pattern pattern = Pattern.compile("(.*);(.*)");
                Matcher matcher = pattern.matcher(line);
                String effectiveLine;
                if (matcher.matches()) {
                    effectiveLine = matcher.group(1);
                } else {
                    effectiveLine = line;
                }
                parseEffectiveLine(effectiveLine);

            }
            TM tm = new TM();
            tm.setStateSet(stateSet);
            tm.setTransition(transition);
            Tape tape = new Tape();
            tape.setInputSymbols(inputCharSet);
            tape.setTapeSymbols(tapeCharSet);
            tm.setTape(tape);
            return tm;
        } catch (FileNotFoundException e) {
            System.err.println("Fail to open file " + file);
        }

        return null;
    }

    /**
     * effective line can be any non-empty line except comment
     */
    private void parseEffectiveLine(String effectiveLine) throws DefinitionException {
        char firstChar = effectiveLine.charAt(0);
        if (firstChar != '#') {
            parseTransitionFunction(effectiveLine);
        } else {
            switch (effectiveLine.charAt(1)) {
                case 'Q':
                    parseAllTheStates(effectiveLine);
                    break;
                case 'S':
                    parseInputSymbols(effectiveLine);
                    break;
                case 'T':
                    parseTapeSymbols(effectiveLine);
                    break;
                case 'q':
                    parseInitState(effectiveLine);
                    break;
                case 'B':
                    //do nothing since the blank symbol is always _
                    break;
                case 'F':
                    parseFinalState(effectiveLine);
                    break;
                default:
                    throw new DefinitionException("Unknown element type : " + effectiveLine.charAt(1));
            }

        }
    }

    private void parseAllTheStates(String effectiveLine) throws DefinitionException {
        Pattern pattern = Pattern.compile("#Q[ ]*=[ ]*\\{(.*)}");
        Matcher matcher = pattern.matcher(effectiveLine);
        if (!matcher.matches()) {
            throw new DefinitionException("States with error format : " + effectiveLine);
        } else {
            String rawStates = matcher.group(1);
            String[] states = rawStates.split(",");
            if (!stateSet.isEmpty()) {
                throw new DefinitionException("Duplicate definition of states");
            } else {
                Collections.addAll(stateSet, states);
            }
        }

    }

    private void parseInputSymbols(String effectiveLine) throws DefinitionException {
        Pattern pattern = Pattern.compile("#S[ ]*=[ ]*\\{(.*)}");
        Matcher matcher = pattern.matcher(effectiveLine);
        if (!matcher.matches()) {
            throw new DefinitionException("Input symbols with error format : " + effectiveLine);
        } else {
            String rawSymbols = matcher.group(1);
            if (!inputCharSet.isEmpty()) {
                throw new DefinitionException("Duplicate definition of input charset");
            }
            inputCharSet = Arrays.stream(rawSymbols.split(","))
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toSet());
        }

    }

    private void parseTapeSymbols(String effectiveLine) throws DefinitionException {
        Pattern pattern = Pattern.compile("#T[ ]*=[ ]*\\{(.*)}");
        Matcher matcher = pattern.matcher(effectiveLine);
        if (!matcher.matches()) {
            throw new DefinitionException("Tape symbols with error format : " + effectiveLine);
        } else {
            String rawSymbols = matcher.group(1);
            if (!tapeCharSet.isEmpty()) {
                throw new DefinitionException("Duplicate definition of tape charset");
            }
            tapeCharSet = Arrays.stream(rawSymbols.split(","))
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toSet());
        }
    }

    private void parseInitState(String effectiveLine) throws DefinitionException {
        Pattern pattern = Pattern.compile("#q0[ ]*=[ ]*(.*)");
        Matcher matcher = pattern.matcher(effectiveLine);
        if (!matcher.matches()) {
            throw new DefinitionException("Init state with error format : " + effectiveLine);
        } else {
            String rawSymbols = matcher.group(1);
            if (q0 == null) {
                q0 = rawSymbols.trim();
            } else {
                throw new DefinitionException("Duplicate initial state");
            }
        }
    }

    private void parseFinalState(String effectiveLine) throws DefinitionException {
        Pattern pattern = Pattern.compile("#F[ ]*=[ ]*\\{(.*)}");
        Matcher matcher = pattern.matcher(effectiveLine);
        if (!matcher.matches()) {
            throw new DefinitionException("Final states with error format : " + effectiveLine);
        } else {
            String rawSymbols = matcher.group(1);
            if (!finalStates.isEmpty()) {
                throw new DefinitionException("Duplicate definition of final states");
            }
            Collections.addAll(finalStates, rawSymbols.split(","));
        }
    }

    /**
     * The input is surely a transition function
     *
     * @param effectiveLine
     * @throws DefinitionException
     */
    private void parseTransitionFunction(String effectiveLine) throws DefinitionException {
        //this is a tf
        String[] tfElements = effectiveLine.trim().split(" ");
        Domain domain = new Domain();
        if (tfElements.length != 5) {
            throw new DefinitionException("Transition function with error format : " + effectiveLine);
        }
        domain.setState(tfElements[0]);
        domain.setTapeSymbol(tfElements[1].toCharArray()[0]);
        Range range = new Range();
        range.setToWrite(tfElements[2].toCharArray()[0]);
        HeadDirection direction;
        switch (tfElements[3].toCharArray()[0]) {
            case 'l':
                direction = HeadDirection.LEFT;
                break;
            case 'r':
                direction = HeadDirection.RIGHT;
                break;
            case '*':
                direction = HeadDirection.STAY;
                break;
            default:
                throw new DefinitionException("Transition function with error format : " + effectiveLine);
        }
        range.setDirection(direction);
        range.setNextState(tfElements[4]);
        transition.addTransition(domain, range);
    }

}

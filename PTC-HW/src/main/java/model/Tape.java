package model;

import lombok.Data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
@Data
public class Tape {
    /**
     * the tape from -inf to -1, the head
     * of this arrayList is the -1st element
     */
    private ArrayList<Character> left = new ArrayList<>();

    /**
     * the tape from 0 to +inf, the head
     * of this arrayList is the 0th element
     */
    private ArrayList<Character> right = new ArrayList<>();


    private Set<Character> inputSymbols = new HashSet<>();

    private Set<Character> tapeSymbols = new HashSet<>();

    private int head = 0;


    /**
     * reset the tape
     *
     * @param string input string
     */
    public void reset(String string) throws InputErrorException {
        this.head = 0;
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        for (Character c : string.toCharArray()) {
            if (!inputSymbols.contains(c)) {
                throw new InputErrorException("Unsupported character " + c);
            }
            right.add(c);
        }
    }

    /**
     * read the character the head points to
     */
    public char read() {
        if (head < 0) {
            //head is at the left half
            int idx = Math.abs(head);

            if (idx > left.size()) {
                //head point to blanks
                return '_';
            } else {
                //head point to non-blank
                return left.get(idx - 1);
            }

        } else {
            //head is at the right half
            int idx = Math.abs(head);
            //the right half begins from 0
            if (idx >= right.size()) {
                //head point to blanks
                return '_';
            } else {
                //head point to non-blank
                return right.get(idx);
            }
        }
    }

    /**
     * write a character to where the head points to
     * <p>
     * if the head points to end blanks, the
     * blanks between the head and non-blank will
     * be added
     * <p>
     * if the character to write is wildcard, do not change the tape symbol
     *
     * @param c character to write
     */
    public void write(char c) {
        addBlanks();
        if (head < 0) {
            int idx = Math.abs(head);
            //this list begins from -1
            //if the character is wildcard, do nothing
            if (c != '*') {
                left.set(idx - 1, c);
            }
        } else {
            //this list begins from 0
            //wildcard
            if (c != '*') {
                right.set(head, c);
            }
        }
    }

    /**
     * move head one cell left
     */
    public void left() {
        head--;
    }

    /**
     * move head one cell right
     */
    public void right() {
        head++;
    }

    /**
     * add blanks between head and the input
     */
    private void addBlanks() {
        if (head < 0) {
            int idx = Math.abs(head);
            if (idx > left.size()) {
                int numbersToAdd = idx - left.size();
                for (int i = numbersToAdd; i > 0; i--) {
                    left.add('_');
                }
            }
        } else {
            if (head >= right.size()) {
                int numbersToAdd = head - (right.size() - 1);
                for (int i = numbersToAdd; i > 0; i--) {
                    right.add('_');
                }
            }
        }
    }

    public void printID(PrintWriter writer) {
        trim();
        addBlanks();
        StringBuilder index = new StringBuilder("Index :");
        StringBuilder tape = new StringBuilder("Tape  :");
        StringBuilder headPosition = new StringBuilder("Head  :");
        int leftIdx = left.size();
        for (; leftIdx > 0; leftIdx--) {
            index.append(" ").append(leftIdx);
            tape.append(" ").append(left.get(leftIdx - 1));
            headPosition.append(" ");

            if (head < 0 && Math.abs(head) == leftIdx) {
                headPosition.append("^");
            } else {
                headPosition.append(" ");
            }

            //align
            for (int i = (int) Math.log10(leftIdx); i > 0; i--) {
                tape.append(' ');
                headPosition.append(' ');
            }
        }

        if (right.size() != 0) {
            for (int i = 0; i < right.size(); i++) {
                index.append(" ").append(i);
                tape.append(" ").append(right.get(i));
                headPosition.append(" ");

                if (head == i) {
                    headPosition.append("^");
                } else {
                    headPosition.append(" ");
                }

                //align
                for (int pad = (int) Math.log10(leftIdx); pad > 0; pad--) {
                    tape.append(' ');
                    headPosition.append(' ');
                }

            }
        }
        writer.println(index);
        writer.println(tape);
        writer.println(headPosition);

    }

    public void printResult(PrintWriter writer) {
        trim();
        StringBuilder content = new StringBuilder();
        for (Character c : left) {
            content.insert(0, c);
        }
        for (Character c : right) {
            content.append(c);
        }
        content.insert(0, "Result: ");
        writer.println(content);
    }

    private void trim() {
        int leftIdx = left.size();
        for (; leftIdx > 0; leftIdx--) {
            if (left.get(leftIdx - 1) != '_') {
                break;
            }
            left.remove(leftIdx - 1);
        }

        int rightIdx = right.size();
        for (; rightIdx > 0; rightIdx--) {
            if (right.get(rightIdx - 1) != '_') {
                break;
            }
            right.remove(rightIdx - 1);
        }
    }

    @Override
    public String toString() {
        Collections.reverse(left);
        String ret = left.toString() + right.toString();
        Collections.reverse(left);
        return ret;
    }
}

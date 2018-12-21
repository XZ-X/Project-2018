import model.Tape;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
public class Main {
    public static void main(String[] args) {
        Tape tape = new Tape();
        tape.reset("helloworld");
        tape.left();
        tape.left();
        tape.left();
        tape.write('2');
        tape.right();
        tape.write('3');
        tape.right();
        tape.right();
        tape.right();
        tape.write('k');
        tape.right();
        tape.right();
        tape.right();
        tape.right();
        tape.write('p');
        System.out.println(tape);

    }
}

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



}

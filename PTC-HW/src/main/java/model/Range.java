package model;

import lombok.Data;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 * the range element of the transition function
 */
@Data
public class Range {
    private String nextState;
    private char toWrite;
    private HeadDirection direction;
}

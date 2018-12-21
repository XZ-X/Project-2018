package model;

import lombok.Data;

import java.util.Objects;

/**
 * the domain element of the transition function
 *
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
@Data
public class Domain {
    private String state;
    private char tapeSymbol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domain domain = (Domain) o;
        return tapeSymbol == domain.tapeSymbol &&
                Objects.equals(state, domain.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, tapeSymbol);
    }
}

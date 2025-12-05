package emptio.domain.common;

public abstract class Blockable {
    protected State state;

    boolean block() {
        if (state == State.ARCHIVED)
            return false;
        else {
            state = State.BLOCKED;
            return true;
        }
    }

    boolean liftBlock() {
        if (state == State.ARCHIVED)
            return false;
        else {
            state = State.ACTIVE;
            return true;
        }
    }
}

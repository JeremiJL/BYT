package emptio.domain.user;

public abstract class Blockable {
    State state;

    boolean block(){
        return switch (this.state) {
            case ACTIVE -> {
                this.state = State.BLOCKED;
                yield true;
            }
            case BLOCKED -> true;
            case ARCHIVED -> false;
        };
    }

    boolean liftBlock(){
        return switch (this.state) {
            case BLOCKED -> {
                this.state = State.ACTIVE;
                yield true;
            }
            case ACTIVE -> true;
            case ARCHIVED -> false;
        };
    }
}

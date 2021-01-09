package client;

import static client.ClientLoginState.values;

/**
 * @author GabrielSin (http://forum.ragezone.com/members/822844.html)
 */

public enum ClientLoginState {
    LOGIN_NOTLOGGEDIN(0),
    LOGIN_SERVER_TRANSITION(1),
    LOGIN_LOGGEDIN(2),
    LOGIN_WAITING(3),
    CHANGE_CHANNEL(4),
    NOT_FOUND(-1);

    private final int state;

    private ClientLoginState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public static ClientLoginState getStateByInt(int i) {
        for (ClientLoginState value : values()) {
            if (value.getState() == i) {
                return value;
            }
        }
        return ClientLoginState.NOT_FOUND;
    }
}

package client.player;

public enum PlayerSkin {
    NORMAL(0),
    DARK(1),
    BLACK(2),
    PALE(3),
    BLUE(4),
    WHITE(9);

    final int id;

    private PlayerSkin(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlayerSkin getById(int id) {
        for (PlayerSkin l : PlayerSkin.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }
}

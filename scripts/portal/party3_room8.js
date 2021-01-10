function enter(pi) {
    if (pi.getEventInstance().getIntProperty("statusStg8") == 1) {
        pi.playPortalSound();
        pi.warp(920011000, 0);
        return true;
    } else {
        pi.playerMessage(
            5,
            "O armazenamento est� inacess�vel no momento, j� que os poderes dos Pixies permanecem ativos dentro da torre."
        );
        return false;
    }
}

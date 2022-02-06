package fr.poulpogaz.isekai.game.renderer;

import fr.poulpogaz.isekai.game.renderer.utils.Disposable;

public interface IMesh extends Disposable {

    void render();

    void renderList();
}
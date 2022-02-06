package fr.poulpogaz.isekai.game.renderer.mesh;

import fr.poulpogaz.isekai.game.renderer.utils.Disposable;

public interface IMesh extends Disposable {

    void render(int primitive);

    VertexAttributes getVertexAttributes();

    int getUsage();

    @Override
    void dispose();
}

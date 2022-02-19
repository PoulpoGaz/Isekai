package fr.poulpogaz.isekai.game.renderer.mesh;

import fr.poulpogaz.isekai.game.renderer.utils.Disposable;

public interface IGLBuffer extends Disposable {

    boolean updateData();

    void markDirty();

    void bind();

    void unbind();

    int getUsage();

    @Override
    void dispose();
}

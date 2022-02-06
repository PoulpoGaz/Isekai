package fr.poulpogaz.isekai.game.renderer.io;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface ICamera {

    void update(float delta);

    boolean hasMoved();

    Matrix4f view();

    Vector3f getUP();

    Vector3f getPosition();

    Vector3f getTarget();

    Vector3f getCenter();
}
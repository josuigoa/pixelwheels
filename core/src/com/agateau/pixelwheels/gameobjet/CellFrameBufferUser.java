package com.agateau.pixelwheels.gameobjet;

import com.agateau.pixelwheels.ZLevel;
import com.agateau.ui.CellFrameBufferManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface CellFrameBufferUser {
    void init(CellFrameBufferManager manager);

    void drawToCell(Batch batch, Rectangle viewBounds);

    void drawCell(Batch batch, ZLevel z, Rectangle viewBounds);
}

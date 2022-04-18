package com.agateau.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class CellFrameBufferManager {
    private static final int SIZE = 1024;
    private final FrameBuffer mFrameBuffer;

    private final Vector2 mNextCellOrigin = new Vector2();
    private float mNextRowY = 0;
    private final Vector2 mTmp = new Vector2();

    private Batch mBatch;

    private final Matrix4 mOldProjectionMatrix = new Matrix4();
    private final Matrix4 mProjectionMatrix = new Matrix4();

    public CellFrameBufferManager() {
        mFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, SIZE, SIZE, false /* hasDepth */);
        mProjectionMatrix.setToOrtho2D(0, 0, SIZE, SIZE);
    }

    /** Returns a Vector2 pointing to the left-bottom corner of the cell */
    public Vector2 reserveCell(int width, int height) {
        if (mNextCellOrigin.x + width >= SIZE) {
            // Does not fit current row
            mNextCellOrigin.set(0, mNextRowY);
        }
        mTmp.set(mNextCellOrigin);

        mNextCellOrigin.x += width;
        float top = mNextCellOrigin.y + height;
        mNextRowY = Math.max(mNextRowY, top);
        return mTmp;
    }

    public void begin(Batch batch) {
        mBatch = batch;
        mOldProjectionMatrix.set(mBatch.getProjectionMatrix());

        mFrameBuffer.begin();
        mBatch.begin();
        mBatch.setProjectionMatrix(mProjectionMatrix);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void end() {
        mBatch.end();
        mFrameBuffer.end();

        mBatch.setProjectionMatrix(mOldProjectionMatrix);
    }

    public Texture getTexture() {
        return mFrameBuffer.getColorBufferTexture();
    }
}

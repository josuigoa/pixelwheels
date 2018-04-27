package com.agateau.tinywheels;

import com.agateau.tinywheels.map.Track;
import com.agateau.tinywheels.vehicledef.VehicleDef;
import com.badlogic.gdx.utils.Array;

public class QuickRaceGameInfo extends GameInfo {
    private Track mTrack;

    public static class Builder extends GameInfo.Builder<QuickRaceGameInfo> {
        private Track mTrack;

        public Builder(Array<VehicleDef> vehicleDefs, GameConfig gameConfig) {
            super(vehicleDefs, gameConfig);
        }

        public void setTrack(Track track) {
            mTrack = track;
            mGameConfig.track = mTrack.getId();
            mGameConfig.flush();
        }

        @Override
        public QuickRaceGameInfo build() {
            QuickRaceGameInfo gameInfo = new QuickRaceGameInfo();
            gameInfo.mTrack = mTrack;
            createEntrants(gameInfo);
            return gameInfo;
        }
    }

    @Override
    public Track getTrack() {
        return mTrack;
    }
}

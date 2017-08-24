package com.agateau.tinywheels.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main(String[] args) {
        String baseDir;
        if (args.length > 0) {
            baseDir = args[0];
        } else {
            baseDir = ".";
        }
        packTextures(baseDir);
    }

    private static void packTextures(String baseDir) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.filterMin = Texture.TextureFilter.Nearest;
        settings.filterMag = Texture.TextureFilter.Nearest;
        settings.pot = false;
        settings.combineSubdirectories = true;

        String inputDir = baseDir + "/core/assets/sprites";
        String outputDir = baseDir + "/android/assets/sprites";
        String packName = "sprites";
        TexturePacker.process(settings, inputDir, outputDir, packName);

        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        inputDir = baseDir + "/core/assets/ui";
        outputDir = baseDir + "/android/assets/ui";
        packName = "uiskin";
        TexturePacker.process(settings, inputDir, outputDir, packName);

        System.out.println("Done");
    }
}

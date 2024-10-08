package net.minecountry.renderer;

import net.minecountry.renderer.config.RenderConfig;
import net.minecountry.world.api.common.Point;
import net.minecountry.world.api.common.PointsMap;
import net.minecountry.world.api.structure.model.Region;
import net.minecountry.world.api.structure.model.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class WorldRenderTest {
    public static final String pathToRegionsFolder = "minecountry-world-renderer/src/test/resources/regions";
    public static final String pathToImage = "minecountry-world-renderer/src/test/resources";

    public static void main(String[] args) {
        World world = readWorld();
        BufferedImage worldMapImage = render(world);

        try {
            ImageIO.write(worldMapImage, "png", new File(pathToImage, "world-map.png"));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static World readWorld()  {
        return WorldLoader.getWorld(Path.of(pathToRegionsFolder));
    }

    private static BufferedImage render(World world) {
        BufferedImage worldMapImage = new BufferedImage(
            world.getWidth() * RenderConfig.RENDER_SCALE,
            world.getHeight() * RenderConfig.RENDER_SCALE,
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graphics = worldMapImage.createGraphics();

        graphics.setColor(Color.BLACK);

        PointsMap<Region> regionsMap = world.getRegions();

        for (Map.Entry<Point, Region> entry : regionsMap.getMap().entrySet()) {
            Region region = entry.getValue();
            BufferedImage regionImage = RegionRenderer.render(world, region);

            int xPos = (region.getRegionX() - regionsMap.getMinX()) * regionImage.getWidth();
            int yPos = (region.getRegionY() - regionsMap.getMinY()) * regionImage.getHeight();

            graphics.drawImage(regionImage, xPos, yPos, null);
            graphics.drawRect(xPos, yPos, xPos + regionImage.getWidth(), yPos + regionImage.getHeight());
        }

        graphics.dispose();

        return worldMapImage;
    }
}

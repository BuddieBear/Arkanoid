package uet.project.arkanoid.utils;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import uet.project.arkanoid.*;
import uet.project.arkanoid.game.*;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.BrickVariants.NormalBrick;

public class MapLoader {
    public void loadBricksFromTiled(GameSetup stage, String filePath) {
        try {
            // Read the TMX file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            // Find all objectgroups
            NodeList objectGroups = doc.getElementsByTagName("objectgroup");

            for (int i = 0; i < objectGroups.getLength(); i++) {
                Element group = (Element) objectGroups.item(i);

                // Only process the Bricks layer
                if (!"Bricks".equals(group.getAttribute("name"))) {
                    continue;
                }

                NodeList objects = group.getElementsByTagName("object");

                for (int j = 0; j < objects.getLength(); j++) {
                    Element obj = (Element) objects.item(j);

                    int gid = Integer.parseInt(obj.getAttribute("gid"));
                    int x = (int) Double.parseDouble(obj.getAttribute("x"));
                    int y = (int) Double.parseDouble(obj.getAttribute("y"));
                    int width = (int) Double.parseDouble(obj.getAttribute("width"));
                    int height = (int) Double.parseDouble(obj.getAttribute("height"));
                    y = y - height;

                    switch (gid) {
                        case 1 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 1, stage));
                        case 3 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 2, stage));
                        case 4 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 3, stage));
                        case 5 -> stage.getBricks().add(new IndestructibleBrick(x, y, width, height, stage));
                        default -> System.out.println("Unknown gid: " + gid);
                    }
                }
            }

            System.out.println("✅ Loaded bricks from " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package  app;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class LoadImage{
    private static Map<String, ImageIcon> cache = new HashMap<>();

    public static ImageIcon get(String path, int width, int height) {
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        ImageIcon icon = new ImageIcon(LoadImage.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaled = new ImageIcon(img);

        cache.put(path, scaled);

        return scaled;
    }
}

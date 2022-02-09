package simr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Config {

    HashMap<Integer, Float[]> offsetMap;
    int zstart;
    int zstop;

    public Config(String configfile)
            throws IOException
    {
        readConfig(configfile);
    }

    private void readConfig(String filename)
            throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        HashMap<Integer, Float[]> h = new HashMap<>();

        boolean isOffset = false;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("zstart")) {
                String[] v = line.split("=");
                zstart = Integer.parseInt(v[1]);
                continue;
            }
            if (line.startsWith("zstop")) {
                String[] v = line.split("=");
                zstop = Integer.parseInt(v[1]);
                continue;
            }
            if (line.startsWith("offsets=")) {
                isOffset = !isOffset;
                continue;
            }
            if (isOffset) {
                String[] v = line.split(",");
                int tile = Integer.parseInt(v[0]);
                float offsetX = Float.parseFloat(v[1]);
                float offsetY = Float.parseFloat(v[2]);
                h.put(tile, new Float[]{offsetX, offsetY});
            }
        }
        br.close();
        offsetMap = h;
    }

    public float getOffsetX(int tile) {
        return offsetMap.get(tile)[0];

    }
    public float getOffsetY(int tile) {
        return offsetMap.get(tile)[1];
    }

    public int getZstart() {
        return zstart;
    }

    public int getZstop() {
        return zstop;
    }

    public int[] getTiles()
    {
        Object[] tileset = offsetMap.keySet().toArray();
        int[] tiles = new int[tileset.length];
        for (int k = 0; k < tileset.length; k++) {
            tiles[k] = (int)tileset[k];
        }

        return tiles;
    }
}

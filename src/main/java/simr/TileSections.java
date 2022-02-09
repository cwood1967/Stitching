package simr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TileSections {

    File tilesroot = null;

    HashMap<Integer, HashMap<Integer, String>> tileImages = null;

    public TileSections(String tilepath) {
        this.tilesroot = new File(tilepath);

        tileImages = new HashMap<>();

        for (File f : tilesroot.listFiles()) {
            if (f.isDirectory() ){
                int tnum = Integer.parseInt(f.getName().substring(1));

                File[] images = f.listFiles();
                HashMap<Integer, String> h =  new HashMap<>();
                for (int j = 0; j < images.length; j++) {
                    String iname = images[j].getName();
                    if (!iname.endsWith(".tif")) {
                        continue;
                    }
                    int last = iname.lastIndexOf("_s");
                    String strZ = iname.substring(last + 2, last + 7);
                    int z = Integer.parseInt(strZ);
                    h.put(z, images[j].getAbsolutePath());
                }

                tileImages.put(tnum, h);
            }
        }
    }

    public HashMap<Integer, String> getSectionTiles(int section, int[] tiles) {

        HashMap<Integer, String> restiles = new HashMap<>();

        for (int i : tiles) {
            String sfile = tileImages.get(i).get(section);
            if (sfile != null){
                restiles.put(i, sfile);
            }
        }

        return restiles;
    }

}

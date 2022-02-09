package simr;

import stitching.model.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Testing {

    public static void main(String[] args) {

        int section = 2272;
        String tilesroot = "/Volumes/core/micro/asa/stn/mel/20-15_Irradiation_Datasets/20220107_MER_IMARE-110171_19_55_48hpa_C4/tiles/g0001";

        TileSections ts =  new TileSections(tilesroot);
        int[] tiles = {4,5,6,8,9,10};

        float[][] offsets = {
                {0, 0},
                {5944, 0},
                {11888, 0},
                {0, 4408},
                {5944, 4408},
                {11888, 4408}
        };

        Stitch_SBEM stitch = new Stitch_SBEM();
        HashMap<Integer, String>  fileMap = ts.getSectionTiles(section, tiles);
        ArrayList<String> filelist = new ArrayList<>();
        for (int t : tiles) {

            String f = fileMap.get(t);
            if (f != null) {
                filelist.add(fileMap.get(t));
            }
        }

        String[] imagenames = new String[filelist.size()];
        imagenames = filelist.toArray(imagenames);
        stitch.run(imagenames, offsets, "/Users/cjw/Desktop/tiles02/smaller");

    }
}

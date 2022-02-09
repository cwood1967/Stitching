package simr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args)
    {

        String configfile = "config.txt";
        String outdir = args[1];
        Config config = null;
        try {
            config = new Config(configfile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int[] tiles = config.getTiles();
        //String tilesroot = "/Volumes/core/micro/asa/stn/mel/20-15_Irradiation_Datasets/20220107_MER_IMARE-110171_19_55_48hpa_C4/tiles/g0001";
        String tilesroot = args[0];
        TileSections ts =  new TileSections(tilesroot);

        ArrayList<Section> slist = new ArrayList<>();

        for (int i = config.getZstart(); i <= config.getZstop(); i++) {
            Section section = new Section(i, config, ts, outdir);
            slist.add(section);
            System.out.println("added " + i + " " + slist.size());
        }

        slist.parallelStream()
                .forEach(x -> x.stitch());
//        HashMap<Integer, String>  fileMap = ts.getSectionTiles(z, tiles);
//        Stitch_SBEM stitch = new Stitch_SBEM();
//        stitch.run(fileMap, config, "/Users/cjw/Desktop/tiles02/smaller");


    }
}

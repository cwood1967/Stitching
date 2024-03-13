package simr;

import java.io.IOException;
import java.util.ArrayList;

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


    }
}

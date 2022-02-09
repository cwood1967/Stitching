package simr;

import java.util.HashMap;

public class Section {

    int z;
    String outdir;
    Config config = null;
    HashMap<Integer, String>  fileMap = null;
    int[] tiles = null;

    public Section(int z, Config config, TileSections tileSections,
                   String outdir)
    {
        this.z = z;
        this.outdir = outdir;
        this.config = config;
        tiles =  config.getTiles();
        fileMap = tileSections.getSectionTiles(z, tiles);

    }

    public void stitch()
    {
        Stitch_SBEM s = new Stitch_SBEM();
        s.run(z, fileMap, config, outdir);
    }

    public float[] getOffsets()
    {
        return null;
    }
}

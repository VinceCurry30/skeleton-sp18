import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 *
 * @author Lin Shen
 */
public class Rasterer {
    private final double[] IMG_LonDPP;

    public Rasterer() {
        IMG_LonDPP = getImgLonDPP();
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        double user_lrlon = params.get("lrlon");
        double user_ullon = params.get("ullon");
        double user_ullat = params.get("ullat");
        double user_lrlat = params.get("lrlat");
        double user_w = params.get("w");

        double user_LonDPP = getLonDPP(user_lrlon, user_ullon, user_w);
        int depth = getDepth(user_LonDPP);
        int k = (int)Math.pow(2, depth);
        double total_width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double unit_lon = total_width / k;
        double total_height = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        double unit_lat = total_height / k;

        boolean query_success = getQuerySuccess(user_lrlon, user_ullon, user_ullat, user_lrlat);
        int startX = getStartingX(user_ullon, depth);
        int endX = getEndingX(user_lrlon, depth);
        int startY = getStartingY(user_ullat, depth);
        int endY = getEndingY(user_lrlat, depth);

        String[][] render_grid = new String[endY - startY + 1][endX - startX + 1];
        for (int i = 0; i < render_grid.length; i++) {
            for (int j = 0; j < render_grid[0].length; j++) {
                render_grid[i][j] = "d" + depth + "_x" + (startX + j) + "_y" + (startY + i) + ".png";
            }
        }

        double raster_lr_lon = (endX + 1) * unit_lon + MapServer.ROOT_ULLON;
        double raster_ul_lon = startX * unit_lon + MapServer.ROOT_ULLON;
        double raster_ul_lat = MapServer.ROOT_ULLAT - startY * unit_lat;
        double raster_lr_lat = MapServer.ROOT_ULLAT - (endY + 1) * unit_lat;

        results.put("render_grid", render_grid);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);

        return results;
    }

    private double getLonDPP(double lrlon, double ullon, double w) {
        return (lrlon - ullon) / w;
    }

    private double[] getImgLonDPP() {
        double[] result = new double[8];
        for (int i = 0; i < 8; i++) {
            result[i] = getLonDPP(MapServer.ROOT_LRLON, MapServer.ROOT_ULLON, MapServer.TILE_SIZE) / (Math.pow(2, i));
        }
        return result;
    }

    private int getDepth(double required_LonDPP) {
        for (int i = 0; i < 8; i++) {
            if (IMG_LonDPP[i] <= required_LonDPP) {
                return i;
            }
        }
        return 7;
    }

    private boolean getQuerySuccess(double lrlon, double ullon, double ullat, double lrlat) {
        if (lrlon < ullon || ullat < lrlat) {
            return false;
        }
        if (lrlon < MapServer.ROOT_ULLON || ullon > MapServer.ROOT_LRLON) {
            return false;
        }
        if (ullat < MapServer.ROOT_LRLAT || lrlat > MapServer.ROOT_ULLAT) {
            return false;
        }
        return true;
    }

    private int getEndingX(double lrlon, int depth) {
        int k = (int)Math.pow(2, depth);
        if (lrlon > MapServer.ROOT_LRLON) {
            return k - 1;
        }
        double total_width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double unit_lon = total_width / k;
        return (int)((lrlon - MapServer.ROOT_ULLON) / unit_lon);
    }

    private int getStartingX(double ullon, int depth) {
        int k = (int)Math.pow(2, depth);
        if (ullon < MapServer.ROOT_ULLON) {
            return 0;
        }
        double total_width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double unit_lon = total_width / k;
        return (int)((ullon - MapServer.ROOT_ULLON) / unit_lon);
    }

    private int getStartingY(double ullat, int depth) {
        int k = (int)Math.pow(2, depth);
        if (ullat > MapServer.ROOT_ULLAT) {
            return 0;
        }
        double total_height = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        double unit_lat = total_height / k;
        return (int)((MapServer.ROOT_ULLAT - ullat) / unit_lat);
    }

    private int getEndingY(double lrlat, int depth) {
        int k = (int)Math.pow(2, depth);
        if (lrlat < MapServer.ROOT_LRLAT) {
            return k - 1;
        }
        double total_height = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        double unit_lat = total_height / k;
        return  (int)((MapServer.ROOT_ULLAT - lrlat) / unit_lat);
    }
}

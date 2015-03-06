package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.DB;
import play.mvc.Result;
import play.mvc.Results;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapTileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(MapTileController.class);

    public Result tile(int zoom, int x, int y) {

        DataSource ds = DB.getDataSource();
        try (final Connection connection = ds.getConnection()) {
            final PreparedStatement stmt = connection
                    .prepareStatement("SELECT tile_data FROM tiles WHERE zoom_level=? AND tile_column=? AND tile_row=?");
            stmt.setInt(1, zoom);
            stmt.setInt(2, x);
            stmt.setInt(3, y);
            final ResultSet resultSet = stmt.executeQuery();

            if (! resultSet.next()) {
                return Results.notFound();
            }

            final InputStream stream = resultSet.getBinaryStream(1);
            return Results.ok(stream).as("image/png");
        } catch (SQLException e) {
            log.error("Could not read map tile", e);
            return Results.internalServerError(e.getMessage());
        }
    }
}

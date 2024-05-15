package com.tkbbank.address_service.procedures;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressServicePkg {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
        config.setJdbcUrl("jdbc:h2:mem:testdb");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
    }

    public static void idxAddressInsert(String isActive, String isActual, String joinTable, String insertTable, Integer batchSize) throws SQLException {
        String selectQuery = "SELECT sys_guid() AS row_id, object_id, par_object_id, object_guid, name, type_cd, level_cd, full_name, path, region_object_id, addr_active_flg, addr_actual_flg, rel_active_flg FROM (SELECT t1.object_id, t2.par_object_id, t1.object_guid, t1.name, t1.type_cd, t1.level_cd, '' AS full_name, t2.path, (CASE WHEN REGEXP_SUBSTR(t2.path, '[^.]+', 1, 1) = t1.object_id THEN NULL ELSE REGEXP_SUBSTR(t2.path, '[^.]+', 1, 1) END) AS region_object_id, t1.active_flg AS addr_active_flg, t1.actual_flg AS addr_actual_flg, t2.active_flg AS rel_active_flg" + (insertTable.toUpperCase().contains("HIST") ? ", ROW_NUMBER() OVER (PARTITION BY t2.path ORDER BY t2.active_flg ASC) AS rn" : "") + " FROM S_ADDR t1 JOIN " + joinTable + " t2 ON t1.object_id = t2.object_id WHERE (1 = 1)" + (!isActive.equals("NULL") ? " AND t1.active_flg = " + (isActive.equals("1") ? true : false) : "") + (!isActual.equals("NULL") ? " AND t1.actual_flg = " + (isActual.equals("1") ? true : false) : "") + ") t3 WHERE (1 = 1)" + (insertTable.toUpperCase().contains("HIST") ? " AND rn = 1" : "");
        String insertQuery = "";

        try (Connection connection = dataSource.getConnection(); PreparedStatement selectPS = connection.prepareStatement(selectQuery); ResultSet rs = selectPS.executeQuery()) {
            while (rs.next()) {
                insertQuery = "INSERT INTO " + insertTable + " (ROW_ID, OBJECT_ID, PAR_OBJECT_ID, OBJECT_GUID, NAME, TYPE_CD, LEVEL_CD, FULL_NAME, PATH, REGION_OBJECT_ID, ADDR_ACTIVE_FLG, ADDR_ACTUAL_FLG, REL_ACTIVE_FLG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertPS = connection.prepareStatement(insertQuery)) {
                    insertPS.setObject(1, rs.getObject("row_id"));
                    insertPS.setLong(2, rs.getLong("object_id"));
                    insertPS.setLong(3, rs.getLong("par_object_id"));
                    insertPS.setObject(4, rs.getObject("object_guid"));
                    insertPS.setString(5, rs.getString("name"));
                    insertPS.setString(6, rs.getString("type_cd"));
                    insertPS.setInt(7, rs.getInt("level_cd"));
                    insertPS.setString(8, rs.getString("full_name"));
                    insertPS.setString(9, rs.getString("path"));
                    insertPS.setLong(10, rs.getLong("region_object_id"));
                    insertPS.setBoolean(11, rs.getBoolean("addr_active_flg"));
                    insertPS.setBoolean(12, rs.getBoolean("addr_actual_flg"));
                    insertPS.setBoolean(13, rs.getBoolean("rel_active_flg"));
                    insertPS.executeUpdate();
                }
            }
        }
    }

    public static void idxAddressUpdate(String updateTable, Integer batchSize) {

    }
}

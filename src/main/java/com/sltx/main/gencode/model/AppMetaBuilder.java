package com.sltx.main.gencode.model;

import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Custom MetaBuilder
 * @author Rlax
 *
 */
public class AppMetaBuilder extends MetaBuilder {

    private String[] skipPre = null;

    public AppMetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected boolean isSkipTable(String tableName) {
        for (String skip : skipPre) {
            if (tableName.startsWith(skip)) {
                return true;
            }
        }
        return false;
    }

    public void setSkipPre(String... skipPre) {
        this.skipPre = skipPre;
    }

    @Override
    protected ResultSet getTablesResultSet() throws SQLException {
        String schemaPattern = dialect instanceof OracleDialect ? dbMeta.getUserName() : null;
        return dbMeta.getTables(conn.getCatalog(), schemaPattern, null, new String[]{"TABLE", "VIEW"});
    }

    @Override
    protected void buildPrimaryKey(TableMeta tableMeta) throws SQLException {
        if (tableMeta.name.toLowerCase().endsWith("_view")) {
            tableMeta.primaryKey = "id";
        } else {
            super.buildPrimaryKey(tableMeta);
        }
    }
}

package com.philippwinter.db.generation;

import com.philippwinter.db.Database;
import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class ReverseAutoIncrementingPrimaryKeysStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            for(Column c : table.getColumns()) {
                if(c.isPartOfKey() && c.isAutoIncrement()) {
                    pw.append("ALTER INDEX ");
                    pw.append(Database.generatePrimaryKeyIndexName(c));
                    pw.append(" REBUILD REVERSE ONLINE;");
                    pw.append("\n");
                }
            }
        }
    }

}

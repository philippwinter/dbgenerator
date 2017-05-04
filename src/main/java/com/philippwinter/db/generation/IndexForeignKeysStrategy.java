package com.philippwinter.db.generation;

import com.philippwinter.db.Database;
import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class IndexForeignKeysStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            for(Column column : table.getColumns()) {
                if(column.isForeignKey()) {
                    String indexName = Database.generateForeignKeyIndexName(column);
                    pw.append("CREATE INDEX ").append(indexName);
                    pw.append(" ON \"").append(table.getName()).append("\"");
                    pw.append("(\"").append(column.getName()).append("\")");
                    pw.append(";\n");
                }
            }
        }
    }

}

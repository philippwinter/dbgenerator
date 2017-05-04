package com.philippwinter.db.generation;

import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class DropTableStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            pw.append("DROP TABLE \"").append(table.getName()).append("\";\n");
        }
    }

}

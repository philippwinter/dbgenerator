package com.philippwinter.db.generation;

import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class ShowRowCountsStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            pw.write("SELECT COUNT(*) FROM \"");
            pw.write(table.getName());
            pw.write("\";\n");
        }
    }

}

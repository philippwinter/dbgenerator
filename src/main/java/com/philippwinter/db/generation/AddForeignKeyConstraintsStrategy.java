package com.philippwinter.db.generation;

import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.ForeignKeyConstraints;
import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static com.philippwinter.db.Database.generateForeignKeyConstraintName;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class AddForeignKeyConstraintsStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            List<Column> foreignKeyColumns = table.getColumns().stream().filter(column -> column.getForeignKeyConstraints() != null).collect(Collectors.toList());

            for(Column column : foreignKeyColumns) {
                ForeignKeyConstraints constraints = column.getForeignKeyConstraints();

                pw.append("ALTER TABLE \"").append(table.getName()).append("\"");
                pw.append(" ADD CONSTRAINT ");
                pw.append(generateForeignKeyConstraintName(column));
                pw.append(" FOREIGN KEY (\"").append(column.getName()).append("\")").append("");
                pw.append(" REFERENCES ").append("\"");
                pw.append(constraints.getReferenced().getTable().getName());
                pw.append("\"").append(" (\"");
                pw.append(constraints.getReferenced().getName()).append("\")");
                pw.append(" ON DELETE CASCADE");
                pw.append(";\n");
            }
        }
    }

}

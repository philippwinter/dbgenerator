package com.philippwinter.db.generation;

import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.Table;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class CreateTableStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        for(Table table : tables) {
            pw.append("CREATE TABLE \"").append(table.getName()).append("\"\n(\n");

            int columnCount = table.getColumns().size();
            int i = 0;
            for(Column column : table.getColumns()) {
                i++;

                pw.append("\t\"").append(column.getName()).append("\" ");
                pw.append(column.getDataType().toSQLType());
                if(column.isUnique()) {
                    pw.append(" UNIQUE");
                }
                if(!column.isNullable()) {
                    pw.append(" NOT NULL");
                }
                if(i != columnCount) {
                    pw.append(", ");
                }
                pw.append("\n");
            }

            List<Column> primaryKeyParts = table.getColumns().stream().filter(Column::isPartOfKey).collect(Collectors.toList());

            if(primaryKeyParts.size() > 0) {
                pw.append(",\tCONSTRAINT ").append(table.getName()).append("_PK").append(" PRIMARY KEY (");
                pw.append(StringUtils.join(primaryKeyParts.stream().map((c) -> ("\"" + c.getName() + "\"")).collect(Collectors.toList()), ", ")).append(")\n");
            }

            pw.append(");\n\n");
        }
    }

}

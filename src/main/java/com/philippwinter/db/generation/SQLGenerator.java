package com.philippwinter.db.generation;

import com.philippwinter.db.model.Table;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class SQLGenerator {

    private List<Table> tables;

    public List<Table> getTables() {
        return tables;
    }

    public SQLGenerator setTables(List<Table> tables) {
        this.tables = tables;
        return this;
    }

    public SQLGenerator(List<Table> tables) {
        this.tables = tables;
    }

    @SafeVarargs
    public final void applyStrategies(OutputStream stream, Class<? extends GenerationStrategy>... strategies) {
        PrintWriter pw = new PrintWriter(stream);

        for(Class<? extends GenerationStrategy> strategyClass : strategies) {
            try {
                pw.append("-- ").append(strategyClass.getName()).append("\n");
                strategyClass.newInstance().apply(pw, tables);
                pw.append("\n\n");
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Could not generate SQL", e);
            }
        }

        pw.flush();
        pw.close();
    }
}

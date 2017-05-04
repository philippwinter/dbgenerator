package com.philippwinter.db.mocking;

import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.ForeignKeyConstraints;
import com.philippwinter.db.model.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class DataSet {

    private Table table;

    private List<List<Object>> primaryKeysUsed;
    private List<Row> rows;

    private DataGenerationDelegate dataGenerationDelegate;

    private Random random = new Random();
    private Map<String, Integer> indicesOfColumns;

    public DataSet(Table table, DataGenerationDelegate dataGenerationDelegate) {
        this.table = table;
        this.rows = new ArrayList<>();
        this.dataGenerationDelegate = dataGenerationDelegate;

        List<Column> columns = table.getColumns();
        int columnCount = columns.size();
        this.indicesOfColumns = new HashMap<>(columnCount);
        for(int i = 0; i < table.getColumns().size(); i++) {
            this.indicesOfColumns.put(columns.get(i).getName(), i);
        }
    }

    public List<Row> getRows() {
        return rows;
    }

    public DataSet setRows(List<Row> rows) {
        this.rows = rows;
        return this;
    }

    public DataGenerationDelegate getDataGenerationDelegate() {
        return dataGenerationDelegate;
    }

    public DataSet setDataGenerationDelegate(DataGenerationDelegate dataGenerationDelegate) {
        this.dataGenerationDelegate = dataGenerationDelegate;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public DataSet setTable(Table table) {
        this.table = table;
        return this;
    }

    public Row randomRow() {
        return this.rows.get(this.random.nextInt(this.rows.size()));
    }

    public void createRows(int offset, int n) {
        System.out.println(String.format("Create %d rows for table %s", n, this.table.getName()));

        this.primaryKeysUsed = new ArrayList<>(n);

        Row row;
        for (int i = offset + 1; i <= n; i++) {
            row = new Row();

            List<Column> columns = this.table.getColumns();

            int j = 0;

                for (Column c : columns) {
                    if (!c.isForeignKey()) {
                        Object val = c.isAutoIncrement() ? i : dataGenerationDelegate.generate(c);

                        row.put(c.getName(), val);
                    }
                }

            this.rows.add(row);
        }
    }

    public String toInsertStatements() {
        StringBuilder sb = new StringBuilder();


        for (Map<String, Object> row : this.rows) {
            List<Map.Entry<String, Object>> entries = row
                    .entrySet()
                    .stream()
                    .sorted(
                        (t, o) -> NumberUtils.compare(indexOfColumn(t.getKey()), indexOfColumn(o.getKey()))
                    )
                    .collect(Collectors.toList());

            String columns = StringUtils.joinWith(", ", entries.stream().map(e -> "\"" + e.getKey() + "\"").toArray());
            String values = StringUtils.joinWith(", ", entries.stream().map(Map.Entry::getValue).toArray());

            sb.append("INSERT INTO \"").append(table.getName()).append("\" (");
            sb.append(columns);
            sb.append(")\n");
            sb.append("VALUES (");
            sb.append(values);
            sb.append(")\n");
            sb.append(";\n");
        }

        return sb.toString();
    }

    private int indexOfColumn(String name) {
        return indicesOfColumns.get(name);
    }

    public void fillForeignKeys(Map<Table, DataSet> tableDataSetMap) {
        for (Row r : this.rows) {
            List<List<Object>> allPrimaryForeignKeys = new ArrayList<>();

            for (Column c : this.table.getColumns()) {
                List<Object> primaryForeignKeys;
                do {
                    primaryForeignKeys = new ArrayList<>();

                    if (c.isForeignKey() && !r.containsKey(c.getName())) {
                        ForeignKeyConstraints constraints = c.getForeignKeyConstraints();
                        Row target = tableDataSetMap.get(constraints.getReferenced().getTable()).randomRow();

                        Object val = target.get(constraints.getReferenced().getName());
                        r.put(c.getName(), val);

                        if (c.isPartOfKey()) {
                            primaryForeignKeys.add(val);
                        }
                    }
                } while (primaryForeignKeys.size() > 0 && allPrimaryForeignKeys.contains(primaryForeignKeys));


                allPrimaryForeignKeys.add(primaryForeignKeys);
            }
        }
    }

    public void outputDataToCsv(PrintWriter pw) {
        for (Map<String, Object> row : this.rows) {
            List<Map.Entry<String, Object>> entries = row
                    .entrySet()
                    .stream()
                    .sorted(
                            (t, o) -> NumberUtils.compare(indexOfColumn(t.getKey()), indexOfColumn(o.getKey()))
                    )
                    .collect(Collectors.toList());

            String values = StringUtils.joinWith(",", entries.stream().map(Map.Entry::getValue).toArray());

            pw.write(values);
            pw.write("\n");
        }
    }
}

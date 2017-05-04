package com.philippwinter.db.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class Table {

    private String name;

    private List<Column> columns;

    private int generateRows = 1000;

    public int getGenerateRows() {
        return generateRows;
    }

    public Table setGenerateRows(int generateRows) {
        this.generateRows = generateRows;
        return this;
    }

    public String getName() {
        return name;
    }

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        for(Column c : this.columns) {
            c.setTable(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Table table = (Table) o;

        return new EqualsBuilder()
                .append(generateRows, table.generateRows)
                .append(name, table.name)
                .append(columns, table.columns)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(columns)
                .append(generateRows)
                .toHashCode();
    }
}

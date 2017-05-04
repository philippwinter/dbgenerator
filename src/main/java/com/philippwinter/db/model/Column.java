package com.philippwinter.db.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class Column {

    public Column(Column column) {
        this.name = column.name;
        this.dataType = column.dataType;
        this.nullable = column.nullable;
        this.partOfKey = column.partOfKey;
        this.isUnique = column.isUnique;
        this.autoIncrement = column.autoIncrement;
        if(column.foreignKeyConstraints != null) {
            this.foreignKeyConstraints = new ForeignKeyConstraints(column.foreignKeyConstraints);
        }
    }

    public enum DataType {
        DATE, NUMBER, STRING, TEXT, FLOAT;

        public String toSQLType() {
            switch(this) {
                case DATE: return "DATE";
                case NUMBER: return "NUMBER";
                case STRING: return "VARCHAR2(255)";
                case TEXT: return "VARCHAR2(4000)";
                case FLOAT: return "FLOAT";

                default: return "";
            }
        }
    }

    private String name;
    private DataType dataType;
    private boolean nullable;
    private boolean partOfKey;
    private boolean isUnique;
    private Table table;

    public Table getTable() {
        return table;
    }

    public Column setTable(Table table) {
        this.table = table;
        return this;
    }

    public Column(String name, DataType dataType, boolean nullable, boolean partOfKey, boolean isUnique, boolean autoIncrement, ForeignKeyConstraints foreignKeyConstraints) {
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
        this.partOfKey = partOfKey;
        this.isUnique = isUnique;
        this.autoIncrement = autoIncrement;
        this.foreignKeyConstraints = foreignKeyConstraints;
    }

    public Column(String name, DataType dataType, boolean nullable) {
        this(name, dataType, nullable, false, false, false, null);
    }

    public ForeignKeyConstraints getForeignKeyConstraints() {
        return foreignKeyConstraints;
    }

    public Column setForeignKeyConstraints(ForeignKeyConstraints foreignKeyConstraints) {
        this.foreignKeyConstraints = foreignKeyConstraints;
        return this;
    }

    private boolean autoIncrement;
    private ForeignKeyConstraints foreignKeyConstraints;

    public boolean isUnique() {
        return isUnique;
    }

    public Column setUnique(boolean unique) {
        isUnique = unique;
        return this;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public Column setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
        return this;
    }

    public String getName() {
        return name;
    }

    public Column setName(String name) {
        this.name = name;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Column setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Column setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public boolean isPartOfKey() {
        return partOfKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Column column = (Column) o;

        return new EqualsBuilder()
                .append(nullable, column.nullable)
                .append(partOfKey, column.partOfKey)
                .append(isUnique, column.isUnique)
                .append(autoIncrement, column.autoIncrement)
                .append(name, column.name)
                .append(table.getName(), column.getTable().getName())
                .append(dataType, column.dataType)
                .append(foreignKeyConstraints, column.foreignKeyConstraints)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(dataType)
                .append(nullable)
                .append(partOfKey)
                .append(isUnique)
                .append(table.getName())
                .append(autoIncrement)
                .append(foreignKeyConstraints)
                .toHashCode();
    }

    public Column setPartOfKey(boolean partOfKey) {
        this.partOfKey = partOfKey;

        return this;
    }

    public boolean isForeignKey() {
        return this.foreignKeyConstraints != null;
    }
}

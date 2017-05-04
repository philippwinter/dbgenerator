package com.philippwinter.db.model;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class ForeignKeyConstraints {

    private Column referenced;

    public ForeignKeyConstraints(ForeignKeyConstraints constraints) {
        this.referenced = constraints.referenced;
    }

    public Column getReferenced() {
        return referenced;
    }

    public ForeignKeyConstraints setReferenced(Column referenced) {
        this.referenced = referenced;
        return this;
    }

    public ForeignKeyConstraints(Column referenced) {

        this.referenced = referenced;
    }
}

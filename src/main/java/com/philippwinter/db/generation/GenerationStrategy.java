package com.philippwinter.db.generation;

import com.philippwinter.db.model.Table;

import java.io.PrintWriter;
import java.util.List;

/**
 * All copyright by Philipp Winter, 2017.
 */
public interface GenerationStrategy {

    void apply(PrintWriter pw, List<Table> tables);
}

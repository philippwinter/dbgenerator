package com.philippwinter.db.generation;

import com.philippwinter.db.mocking.DataGenerationDelegate;
import com.philippwinter.db.mocking.DataSet;
import com.philippwinter.db.model.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class CreateMockedDataCsvStrategy implements GenerationStrategy {

    @Override
    public void apply(PrintWriter pw, List<Table> tables) {
        DataGenerationDelegate generationDelegate = new DataGenerationDelegate();

        Map<Table, DataSet> tableDataSetMap = new HashMap<>(tables.size());

        for(Table table : tables) {
            DataSet dataSet = new DataSet(table, generationDelegate);
            tableDataSetMap.put(table, dataSet);
            dataSet.createRows(0, table.getGenerateRows());
        }

        for(DataSet dataSet : tableDataSetMap.values()) {
            dataSet.fillForeignKeys(tableDataSetMap);
        }

        //pw.append("ALTER SESSION SET NLS_DATE_FORMAT='YYYY-MM-DD';");

        for(DataSet dataSet : tableDataSetMap.values()) {
            File file = new File("output/values_" + dataSet.getTable().getName() + ".csv");

            try {
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();

                try (PrintWriter dataSetPw = new PrintWriter(new FileOutputStream(file))) {
                    dataSet.outputDataToCsv(dataSetPw);
                    dataSetPw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

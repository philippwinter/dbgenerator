package com.philippwinter.db;

import com.philippwinter.db.generation.*;

import java.io.*;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class Program {

    public static void main(String[] args) {
        SQLGenerator generator = new SQLGenerator(Database.getInstance().getTables());

        File outputFile = new File("output/output.sql");

        try {
            if (outputFile.exists()) {
                if (!outputFile.delete()) {
                    throw new RuntimeException("Could not delete former output file");
                }
            }
            if (!outputFile.createNewFile()) {
                throw new RuntimeException("Could not create output file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create output file", e);
        }

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            //noinspection unchecked
            generator.applyStrategies(
                    bufferedOutputStream,
                    ShowRowCountsStrategy.class,
                    DropForeignKeyConstraintsStrategy.class,
                    DropTableStrategy.class,
                    CreateTableStrategy.class,
                    AddForeignKeyConstraintsStrategy.class,
                    CreateMockedDataCsvStrategy.class,
                    AddForeignKeyConstraintsStrategy.class,
                    IndexForeignKeysStrategy.class,
                    ReverseAutoIncrementingPrimaryKeysStrategy.class
                    );
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");

    }

}

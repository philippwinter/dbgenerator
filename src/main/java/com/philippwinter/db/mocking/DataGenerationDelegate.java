package com.philippwinter.db.mocking;

import com.philippwinter.db.model.Column;

import java.sql.Date;
import java.util.Random;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class DataGenerationDelegate {

    private Random random = new Random();

    private NormalDistributionParameters stringLength = new NormalDistributionParameters(12.0, 2.5, 0.0);
    private NormalDistributionParameters integerValue = new NormalDistributionParameters(43.0, 8.1);
    private NormalDistributionParameters floatValue = new NormalDistributionParameters(207.3, 22.8);
    private NormalDistributionParameters sentenceLength = new NormalDistributionParameters(5, 0.5, 0.0);

    public Object generate(Column column) {
        switch(column.getDataType()) {
            case DATE: return generateDate();
            case TEXT: return generateText();
            case FLOAT: return generateFloat();
            case NUMBER: return generateInteger();
            case STRING: return generateString();

            default: return "null";
        }
    }

    private char generateChar() {
        char ch = (char) (random.nextInt(26) + 'a');
        return random.nextBoolean() ? Character.toUpperCase(ch) : ch;
    }

    private String generateRawString() {
        int length = stringLength.generateInt(this.random);
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(generateChar());
        }

        return sb.toString();
    }

    private String generateString() {
        return generateRawString();
    }

    private Integer generateInteger() {
        return integerValue.generateInt(this.random);
    }

    private Double generateFloat() {
        return floatValue.generateDouble(this.random);
    }

    private String generateText() {
        int sentences = sentenceLength.generateInt(this.random);
        StringBuilder sb = new StringBuilder((int) (sentenceLength.getMean() * stringLength.getMean()));

        for(int i = 0; i < sentences; i++) {
            sb.append(generateRawString());
            if(i != sentences - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private String generateDate() {
        // Date between 1970 and 2015
        @SuppressWarnings("NumericOverflow") long ms = (Math.abs(random.nextLong()) % (46L * 365 * 24 * 60 * 60 * 1000));

        return new Date(ms).toString();
    }

}

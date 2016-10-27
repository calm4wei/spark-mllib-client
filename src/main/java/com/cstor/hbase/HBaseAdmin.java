package com.cstor.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created on 2016/10/18
 *
 * @author feng.wei
 */
public class HBaseAdmin {

    private static final String TABLE_NAME = "MY_TABLE_NAME_TOO";
    private static final String CF_DEFAULT = "DEFAULT_COLUMN_FAMILY";
    public static final byte[] CF = "cf".getBytes();
    public static final byte[] ATTR = "attr".getBytes();

    /**
     * 预创建regions
     *
     * @param admin
     * @param table
     * @param startKey
     * @param endKey
     * @param numOfRegsions
     * @throws IOException
     */
    public static void preCreateRegions(Admin admin, HTableDescriptor table, byte[] startKey, byte[] endKey, int numOfRegsions) throws IOException {

        admin.createTable(table, startKey, endKey, numOfRegsions);

    }

    public static void preCreateSplits(Admin admin, HTableDescriptor table, byte[][] splits) throws IOException {
        admin.createTable(table, splits);
    }

    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            table.addFamily(new HColumnDescriptor(CF_DEFAULT).setCompressionType(Compression.Algorithm.NONE));

            System.out.print("Creating table. ");
            createOrOverwrite(admin, table);
            System.out.println(" Done.");
        }
    }

    public static byte[][] getHexSplits(String startKey, String endKey, int numRegions) {
        byte[][] splits = new byte[numRegions - 1][];
        BigInteger lowestKey = new BigInteger(startKey, 16);
        BigInteger highestKey = new BigInteger(endKey, 16);
        BigInteger range = highestKey.subtract(lowestKey);
        BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
        lowestKey = lowestKey.add(regionIncrement);
        for (int i = 0; i < numRegions - 1; i++) {
            BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
            byte[] b = String.format("%016x", key).getBytes();
            splits[i] = b;
        }
        return splits;
    }

    /**
     * row scan
     *
     * @param table
     * @throws IOException
     */
    public static void scanRow(Table table) throws IOException {
        Scan scan = new Scan();
        scan.addColumn(CF, ATTR);
        scan.setRowPrefixFilter(Bytes.toBytes("row"));
        ResultScanner rs = table.getScanner(scan);
        try {
            for (Result r = rs.next(); r != null; r = rs.next()) {

            }
        } finally {
            rs.close();
        }
    }

}

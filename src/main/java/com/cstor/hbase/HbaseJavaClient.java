package com.cstor.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.regionserver.HRegionServer;
import org.apache.hadoop.hbase.regionserver.MetricsRegionServer;

import java.io.IOException;

/**
 * Created on 2016/7/4
 *
 * @author feng.wei
 */
public class HbaseJavaClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "datacube151,datacube154");
        conf.set("zookeeper.znode.parent", "/hbase");
        HRegionServer hRegionServer = new HRegionServer(conf);
        MetricsRegionServer regionLoad = hRegionServer.getRegionServerMetrics();
        long readCounts = regionLoad.getRegionServerWrapper().getReadRequestsCount();

//        long readCounts = regionLoad.getReadRequestsCount();
//        long writeCounts = regionLoad.getWriteRequestsCount();
//        System.out.println("read count = " + readCounts + " , write counts = " + writeCounts);

    }
}

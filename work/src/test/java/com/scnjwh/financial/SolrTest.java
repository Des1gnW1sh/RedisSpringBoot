package com.scnjwh.intellectreport;


import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;


public class SolrTest {
    private static final String URL = "http://192.168.2.103:8080/solr/db";
    
    private HttpSolrClient server = null;
     
    @Before
    public void init() {
        // 创建 server
        server = new HttpSolrClient(URL);
    }
    
    @Test
    public void addDoc() {
 
        SolrInputDocument doc = new SolrInputDocument();
 
        doc.addField("id", "this is id");
        doc.addField("title", "this is document");
 
        try {
 
            UpdateResponse response = server.add(doc);
            // 提交
            server.commit();
 
            System.out.println("########## Query Time :" + response.getQTime());
            System.out.println("########## Elapsed Time :" + response.getElapsedTime());
            System.out.println("########## Status :" + response.getStatus());
            
        } catch (SolrServerException | IOException e) {
            System.err.print(e);
        }
    }
    
    
    /**
     * 查询
     */
    @Test
    public void testQuery() {
        String queryStr = "*:*";
        SolrQuery params = new SolrQuery(queryStr);
        params.set("rows", 10);
        try {
            QueryResponse response = null;
            response = server.query(params);
            SolrDocumentList list = response.getResults();
            System.out.println("########### 总共 ： " + list.getNumFound() + "条记录");
            for (SolrDocument doc : list) {
                System.out.println("######### id : " + doc.get("id") + "  title : " + doc.get("title"));
            }
        } catch (SolrServerException e) {
            System.err.print(e);
        }
    }
}
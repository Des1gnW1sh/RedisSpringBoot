package com.scnjwh.intellectreport.common.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scnjwh.intellectreport.common.persistence.Page;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.modules.sys.entity.User;
  
/**
 * solr工具类
 * @author timo
 *
 */
@Component  
public class SpringSolr {  
  
//    @Autowired  
//    private HttpSolrServer httpSolrServer;  
//    public Object getUser(Long id) throws SolrServerException {  
//  
//        //创建查询条件  
//        SolrQuery query = new SolrQuery();  
//        query.setQuery("id:" + id);  
//          
//        //查询并返回结果  
//        QueryResponse queryResponse = this.httpSolrServer.query(query);  
//        return queryResponse.getBeans(User.class);  
//    }  
    private final  static String URL= Global.getConfig("solr.Url");
    public SolrClient  server=new HttpSolrClient(SpringSolr.URL);
    //插入索引
    public void add(String id ,String title,String content) throws SolrServerException, IOException{
    	SolrInputDocument doc=new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("msg_title", title);
        doc.addField("msg_content", content);
        this.server.add(doc);
        this.server.commit();
    }
    //删除索引
    public void delete(String id) throws SolrServerException, IOException{
    	  server.deleteById(id);
          server.commit();//先删除 基于query的删除 会删除所有建立的索引文件
    }
    //分页高亮查询
    public Page<Map<String, String>> query(Page<Map<String, String>> page, String content) throws Exception{
         SolrQuery query = new SolrQuery(content);
         query.setStart(page.getPageNo());//起始页
         query.setRows(page.getPageSize());//每页显示数量
         query.setParam("hl.fl", "msg_title,msg_content");//设置哪些字段域会高亮显示
         query.setHighlight(true).setHighlightSimplePre("<span class='hight'>").setHighlightSimplePost("</span>");
         QueryResponse rsp = server.query( query );
         SolrDocumentList results = rsp.getResults();
         page.setCount(results.getNumFound());
         List<Map<String, String>> li = new ArrayList<>();
         for(SolrDocument doc:results){
        	 Map<String, String> map = new HashMap<>();
        	 String id = (String) doc.getFieldValue("id"); //id is the uniqueKey field
        	 map.put("id", id);
        	 if(rsp.getHighlighting().get(id)!=null){
                List<String> a = rsp.getHighlighting().get(id).get("msg_title");  //高亮必须要求存储 不存储的话 没法添加高亮
                List<String> b = rsp.getHighlighting().get(id).get("msg_content");
                if(b!=null){
                	 map.put("content", a.toString());
                }else{
                	 map.put("content", "");
                }
                if(a!=null){
	               	 map.put("title", a.toString());
                }else{
	               	 map.put("title", "");
                }
             }
        	 li.add(map);
         }
         
         page.setList(li);
         return page;
    }
	
}  
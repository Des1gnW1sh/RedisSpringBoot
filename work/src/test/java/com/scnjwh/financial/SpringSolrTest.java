package com.scnjwh.intellectreport;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import com.scnjwh.intellectreport.common.solr.SpringSolr;

/**
 * 
 * @项目名称：spring-solr
 * @类名称：SpringSolrTest
 * @类描述：测试类
 * @创建人：millery
 * @创建时间：2015年11月5日 上午10:56:06 
 * @version：
 */
public class SpringSolrTest {

	private SpringSolr springSolr;
	public SolrClient  server=null;
	private final  static String URL="http://192.168.2.103:8080/solr/db";
	@Before
	public void setUp() throws Exception {
//		// 初始化Spring容器
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring-context.xml", "classpath*:applicationContext-solr.xml");
//		//获取对象
//		this.springSolr = applicationContext.getBean(SpringSolr.class);
		 server=new HttpSolrClient(URL);
	}

//	@Test
//	public void test() throws SolrServerException {
//		// 测试方法，输出结果
//		Object user = springSolr.getUser((long) 1);
//		System.out.println(user);
//	}
	//删除所有分词
    @Test
    public void testDel() throws Exception{
        server.deleteByQuery("*:*");
        server.commit();//先删除 基于query的删除 会删除所有建立的索引文件
    }
    @Test
    public void testAdd() throws Exception{
        SolrInputDocument doc=new SolrInputDocument();
        doc.addField("id", "13");
        doc.addField("msg_title", "新浪微博");
        doc.addField("msg_content", "我有一帐个微博帐号名字叫做帐什么帐呢？");
        server.add(doc);
        server.commit();
    }
    @Test
    public void test03() throws Exception{
        List<Message> msgs=new ArrayList<Message>();
        msgs.add(new Message("4", "第四个测试solr测试文件", new String[]{"中华人民共和国万岁","中华上下五千年那年"}));
        msgs.add(new Message("5", "第5个好朋友是什么意思呢？", new String[]{"上海是个好地方","歌唱我们亲爱的祖国曾经走过千山万水"}));
        server.addBeans(msgs);
        server.commit();
    }
    @Test
    public void test04() throws Exception{
        //定义查询内容 * 代表查询所有    这个是基于结果集
         SolrQuery query = new SolrQuery("微博帐号");
         query.setStart(0);//起始页
         query.setRows(3);//每页显示数量
         QueryResponse rsp = server.query( query );
         SolrDocumentList results = rsp.getResults();
         System.out.println(results.getNumFound());//查询总条数
         for(SolrDocument doc:results){
             System.out.println(doc.get("msg_content"));
         }
    }
    @Test
    public void test05() throws Exception{
         SolrQuery query = new SolrQuery("帐");// * 号 是查询 所有的数据
         QueryResponse rsp = server.query( query );
         SolrDocumentList results = rsp.getResults();
         System.out.println(results.getNumFound());//查询总条数
         
         List<Message> beans = rsp.getBeans(Message.class);//这个不能获取查询的总数了 也不能高亮
         for(Message message:beans){
             System.out.println(message.toString());
         }
    }
    @Test
    public void test06() throws Exception{
    	 SolrQuery query = new SolrQuery("主键");// * 号 是查询 所有的数据
         query.setStart(0);//起始页
         query.setRows(5);//每页显示数量
         query.setParam("hl.fl", "msg_title,msg_content");//设置哪些字段域会高亮显示
         query.setHighlight(true).setHighlightSimplePre("<span class='hight'>").setHighlightSimplePost("</span>");
         
         QueryResponse rsp = server.query( query );
         SolrDocumentList results = rsp.getResults();
         System.out.println(results.getNumFound());//查询总条数
         
         for(SolrDocument doc:results){
             String id = (String) doc.getFieldValue("id"); //id is the uniqueKey field
             System.out.println(id);
             if(rsp.getHighlighting().get(id)!=null){
                 //高亮必须要求存储 不存储的话 没法添加高亮
            	 System.out.println(rsp.getHighlighting().get(id));
                 System.out.println(rsp.getHighlighting().get(id).get("msg_content"));
                 System.out.println(rsp.getHighlighting().get(id).get("msg_title"));
             }
         }
    }
}
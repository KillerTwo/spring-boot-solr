package lwt.org.springbootsolr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSolrApplicationTests {

  @Autowired
  private SolrClient solrClient;
  
	@Test
	public void contextLoads() {
	  System.err.println("进入查询。。。");
	  SolrQuery query = new SolrQuery();
	  query.setQuery("my_content:中国");
	  query.setStart(0);
	  query.setRows(5);
	  try {
      QueryResponse response = solrClient.query(query);
      SolrDocumentList solrDocumentList = response.getResults();
      for (SolrDocument solrDocument : solrDocumentList) {
        System.out.println("数据id："+"<<"+solrDocument.get("id")+">>");
        System.out.println("数据内容："+"<<"+solrDocument.get("my_content")+">>");
      }
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	  
	}

}

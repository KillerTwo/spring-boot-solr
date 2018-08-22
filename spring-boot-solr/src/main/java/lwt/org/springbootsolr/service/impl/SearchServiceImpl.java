package lwt.org.springbootsolr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lwt.org.springbootsolr.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

  @Autowired
  private SolrClient solrClient;
  
  @Override
  public List<String> query(String keyword, int pageNum, int pageSize) {
    List<String> ResultContent = new ArrayList<>();
    SolrQuery query = new SolrQuery();
    query.setQuery("my_content:"+keyword);
    
    if( pageNum != 0 && pageSize != 0) {
      // 设置分页，默认查询10条
      System.err.println(pageNum+ "==" + pageSize);
      query.setStart((pageNum-1)*pageSize);
      query.setRows(pageSize);
    }else {
      query.setStart(0);
      query.setRows(10000);
    }
    
    //开启高亮
    query.setHighlight(true);
    //高亮域
    query.addHighlightField("my_content");
    //前缀
    query.setHighlightSimplePre("<span style='color:red; cursor:pointer;'>");
    //后缀
    query.setHighlightSimplePost("</span>");
    QueryResponse response;
    try {
      response = solrClient.query(query);
      Map<String, Map<String, List<String>>> highlightMap = response.getHighlighting();
      
      SolrDocumentList solrDocumentList = response.getResults();
      for (SolrDocument solrDocument : solrDocumentList) {
        /*String content = (String) solrDocument.get("my_content");
        System.out.println("content:"+ content);
        ResultContent.add(content);*/
        Map<String, List<String>> map = highlightMap.get(solrDocument.get("id"));
        List<String> list = map.get("my_content");
        System.out.println(list);
        ResultContent.add(list.get(0));
      }
      return ResultContent;
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return ResultContent;
  }

}

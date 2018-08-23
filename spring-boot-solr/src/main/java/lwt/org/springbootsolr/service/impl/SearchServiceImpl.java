package lwt.org.springbootsolr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
  public Map<String, List<String>> query(String keyword, int pageNum, int pageSize) {
    List<Map<String, String>> resultList = new ArrayList<>();
    Map<String, String> keyMapedContent = new HashMap<>();
    Map<String, List<String>> resultMap = new HashMap<>();
    List<String> resultContent = new ArrayList<>();
    List<String> keyWordContent = new ArrayList<>();
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
      List<String> count = new ArrayList<>();
      count.add(solrDocumentList.getNumFound()+"");
      for (SolrDocument solrDocument : solrDocumentList) {
        String content = (String) solrDocument.get("my_content");
        /*
        System.out.println("content:"+ content);
        */
        resultContent.add(content);                 // 包含全部的内容
        Map<String, List<String>> map = highlightMap.get(solrDocument.get("id"));
        List<String> list = map.get("my_content");
        //System.out.println(list);
        keyWordContent.add(list.get(0));            // 截取关键词前后的一段
        //keyMapedContent.put(list.get(0), content);
      }
      System.err.println("符合条件的数量： 》》 "+count);
      System.err.println("关键字：     " +keyWordContent.get(0));
      System.err.println("***********************************************");
      System.err.println("内容： "+resultContent.get(0));
      resultMap.put("count", count);
      resultMap.put("content", resultContent);
      resultMap.put("keyWordContent", keyWordContent);
      return resultMap;
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return resultMap;
  }

}

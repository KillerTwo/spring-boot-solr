package lwt.org.springbootsolr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lwt.org.springbootsolr.service.SearchService;

@RestController()
@RequestMapping(value="/solr")
@CrossOrigin(value="*")
public class SearchController {
  
  @Autowired
  private SearchService searchService;
  
  @GetMapping(value="/description")
  public Map<String,Object> searchForPaginat(@RequestParam String keyword, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    //List<String> results = searchService.query(keyword, pageNum, pageSize);
    Map<String, List<String>> results = searchService.query(keyword, pageNum, pageSize);
    Map<String, Object> map = new HashMap<>();
    map.put("msg", "success");
    map.put("result", results);
    return map;
  }
  
  
}

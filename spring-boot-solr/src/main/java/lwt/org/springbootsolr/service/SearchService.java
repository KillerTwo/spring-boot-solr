package lwt.org.springbootsolr.service;

import java.util.List;
/**
 * 
 * @author Administrator
 *
 */
public interface SearchService {
  List<String> query(String keyword, int pageNum, int pageSize);
}

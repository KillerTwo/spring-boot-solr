package lwt.org.springbootsolr.service;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author Administrator
 *
 */
public interface SearchService {
  Map<String, List<String>> query(String keyword, int pageNum, int pageSize);
}

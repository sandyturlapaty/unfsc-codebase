/**
 * 
 */
package corp.ospreys.edu.dao;

import java.util.List;

import corp.ospreys.edu.dto.SearchDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface SearchDetailsDao {
	
	List<SearchDetails> retrieveSearchDetails(String value);

}

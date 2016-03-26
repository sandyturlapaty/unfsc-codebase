/**
 * 
 */
package corp.ospreys.edu.service;

import java.util.List;

import corp.ospreys.edu.dto.SearchDetails;


/**
 * @author sandyturlapaty
 *
 */
public interface UnfscSearchService {
	
	List<SearchDetails> retrieveSearchDetails(String value);

} 
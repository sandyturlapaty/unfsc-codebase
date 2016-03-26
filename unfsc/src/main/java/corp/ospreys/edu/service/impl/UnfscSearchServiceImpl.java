/**
 * 
 */
package corp.ospreys.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.SearchDetailsDao;
import corp.ospreys.edu.dto.SearchDetails;
import corp.ospreys.edu.service.UnfscSearchService;

/**
 * @author ppenamak
 *
 */
@Service("unfscSearchService")
public class UnfscSearchServiceImpl implements UnfscSearchService {
	
	@Autowired
	private SearchDetailsDao searchDetailsDao;

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.service.UnfscSearchService#retrieveSearchDetails(java.lang.String)
	 */
	@Override
	public List<SearchDetails> retrieveSearchDetails(String value) {
		return searchDetailsDao.retrieveSearchDetails(value);
	}

}

package corp.ospreys.edu.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "searchList")
public class SearchDetailsList {
	
	private List<SearchDetails> searchList;

	/**
	 * @return the searchList
	 */
	public List<SearchDetails> getSearchList() {
		return searchList;
	}

	/**
	 * @param searchList the searchList to set
	 */
	public void setSearchList(List<SearchDetails> searchList) {
		this.searchList = searchList;
	}

	
}



package com.cs371group2.admin;

import java.util.List;

/**
 * This class represents a PagedResponse in the database, and is used to return a list of elements along with paging
 * information to the administrative website via the AdminApi.
 *
 * History property: Instances of this class are mutable from the time they are created.
 *
 * Created on 2017-03-13.
 */
public class PagedResponse<E>{

    /** The list of elements received from the response */
    private List<E> responseList;

    /** The paging index of the first concern in the list .*/
    private int pageStartIndex;

    /** The paging index of the last concern in the list. */
    private int pageEndIndex;

    /** The total number of items returned from the response */
    private int totalItemsCount;

    /**
     * Creates a new PagedResponse containing a list of entities and the paging information associated with it.
     *
     * @param responseList The list of elements represented by the response
     * @param pageStartIndex The index of the first element (starting at 1)
     * @precond responseList != null
     */
    public PagedResponse(List<E> responseList, int pageStartIndex) {
        assert(responseList != null);
        this.responseList = responseList;
        this.totalItemsCount = responseList.size();
        this.pageStartIndex = pageStartIndex;
        this.pageEndIndex = pageStartIndex + totalItemsCount;

    }

    public int getStartIndex() {
        return pageStartIndex;
    }

    public int getEndIndex() {
        return pageEndIndex;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<E> getItems(){
        return responseList;
    }
}

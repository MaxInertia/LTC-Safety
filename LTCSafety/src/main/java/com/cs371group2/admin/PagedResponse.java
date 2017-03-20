package com.cs371group2.admin;

import com.cs371group2.Dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a PagedResponse in the database, and is used to return a list of elements along with paging
 * information to the administrative website via the AdminApi.
 *
 * History property: Instances of this class are mutable from the time they are created.
 *
 * Created on 2017-03-13.
 */
public class PagedResponse {

    /** The paging index of the first concern in the list .*/
    int pageStartIndex;

    /** The paging index of the last concern in the list. */
    int pageEndIndex;

    /** The total number of items returned from the response */
    int totalItemsCount;

    /**
     * Creates a new PagedResponse containing the paging information associated with it.
     *
     * @param startIndex The index of the first element (indexed starting at 1)
     * @param endIndex The index of the last element (indexed starting at 1
     * @param totalItems The total number of items in the datastore
     * @precond totalItems >= 0
     * @precond startIndex <= endIndex
     */
    public PagedResponse(int startIndex, int endIndex, int totalItems) {
        assert (0 <= totalItems);
        assert (startIndex <= endIndex);
        this.totalItemsCount = totalItems;
        this.pageStartIndex = startIndex;
        this.pageEndIndex = endIndex;
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
}

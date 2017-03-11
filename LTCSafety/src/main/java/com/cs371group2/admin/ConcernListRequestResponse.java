package com.cs371group2.admin;

import com.cs371group2.concern.Concern;

import java.util.List;

/**
 * This class represents the response from the concern request list in the AdminAPI for easier website paging.
 * This includes the list of concerns requested, the concern count, and the first and last concern on the list.
 *
 * History property: This class is immutable from the time it is created
 *
 * Created on 2017-03-02.
 */
public class ConcernListRequestResponse {

    /** The index of the first concern in the list */
    private int firstIndex;

    /** The index of the last concern in the list */
    private int lastIndex;

    /** The total number of concern in the datastore */
    private int totalConcerns;

    /** The list of requested concerns*/
    private List<Concern> concernList;

    /**
     * Creates a new ConcernListRequestResponse based on the given list of concerns.
     * @param list The list of concerns to be represented in the response.
     * @precond list != null
     */
    public ConcernListRequestResponse(List<Concern> list, int startIndex, int endIndex, int totalSize){
        assert(list != null);

        concernList = list;
        firstIndex = startIndex;
        lastIndex = endIndex;
        totalConcerns = totalSize;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public int getTotalConcerns() {
        return totalConcerns;
    }

    public List<Concern> getConcernList(){
        return concernList;
    }
}

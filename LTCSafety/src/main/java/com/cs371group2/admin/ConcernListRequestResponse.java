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

    /** The first concern in the list */
    private Concern firstConcern;

    /** The last concern in the list */
    private Concern lastConcern;

    /** The list of requested concerns*/
    private List<Concern> concernList;

    /** The number of concerns in the concern list*/
    private int concernCount;

    /**
     * Creates a new ConcernListRequestResponse based on the given list of concerns.
     * @param list The list of concerns to be represented in the response.
     * @precond list != null
     */
    public ConcernListRequestResponse(List<Concern> list){
        assert(list != null);
        concernList = list;
        concernCount = list.size();

        if(0 < concernCount){
            firstConcern = list.get(0);
            lastConcern = list.get(concernCount - 1);
        }
    }

    public Concern getFirstConcern() {
        return firstConcern;
    }

    public Concern getLastConcern() {
        return lastConcern;
    }

    public int getConcernCount() {
        return concernCount;
    }

    public List<Concern> getConcernList(){
        return concernList;
    }
}

package com.cs371group2.admin;

import com.cs371group2.concern.Concern;

import java.util.List;

/**
 * This class represents the response from the concern request list in the AdminAPI for easier website paging.
 * This includes the list of concerns requested, the concern count, and the first and last concern on the list.
 *
 * History property: Instances of this class are mutable from the time they are created,
 * due to referential nature of the list of elements.
 *
 * Invariance Properties: This class assumes that an administrator has requested a list of concerns
 * and has been validly authenticated. It also assumed the list it is given is not null, the start index
 * is smaller or equal to the end index, and the total size is non-negative.
 *
 * Created on 2017-03-02.
 */
public final class ConcernListResponse extends PagedResponse {

    /** The list of requested concerns*/
    private List<Concern> concernList;

    /**
     * Creates a new ConcernListResponse based on the given list of concerns.
     *
     * @param list The list of concerns to be represented in the response.
     * @param startIndex The paging index of the first concern (starting at 1)
     * @param endIndex The paging index of the last concern (starting at 1)
     * @param totalSize The total amount concerns stored in the database
     *
     * @precond list != null
     * @precond startIndex <= endIndex
     * @precond totalSize >= 0
     */
    public ConcernListResponse(List<Concern> list, int startIndex, int endIndex, int totalSize){
        super(startIndex, endIndex, totalSize);
        assert(list != null);

        concernList = list;
    }

    public List<Concern> getConcernList(){
        return concernList;
    }
}

package com.cs371group2.concern;

import com.cs371group2.client.OwnerToken;

import com.googlecode.objectify.annotation.Index;

import java.util.LinkedList;

/**
 *
 * The OwnerTokenListWrapper is used by any backend api methods that need to take in a list of
 * owner tokens as a parameter. This has to be done since google cloud endpoints does not allow
 * for arrays or lists as method parameters.
 *
 * History properties: Instances of this class are mutable from the time they are created.
 *
 * Invariance properties: This class makes no assumptions about the information it is given.
 *
 * Created by on 2017-03-01.
 */
public class OwnerTokenListWrapper {

    /**
     * The list of owner tokens that the wrapper contains.
     */
    @Index
    public LinkedList<OwnerToken> tokens;


    public LinkedList<OwnerToken> getTokens() {
        return tokens;
    }

    /**
     * A constructor method that takes in a list of owner tokens.
     * @param tokens The list of owner tokens
     */
    public OwnerTokenListWrapper(LinkedList<OwnerToken> tokens){
        this.tokens = tokens;
    }

    public OwnerTokenListWrapper(){
        this.tokens = new LinkedList<OwnerToken>();
    }


}

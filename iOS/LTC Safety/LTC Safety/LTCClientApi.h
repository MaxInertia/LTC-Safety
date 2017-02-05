//
//  LTCClientApi.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCConcern+CoreDataClass.h"
#import "GTLRClient.h"

/**
    The completion block that will be used to bring back a possible error message, and a
    datastore concern response, which contains the concern that was submitted, and
    an ownerToken for that concern.
*/
typedef void(^LTCSubmitConcernCompletion)(GTLRClient_SubmitConcernResponse *concernResponse, NSError *error);
/**
    The completion block that will be used to bring back a possible error message.
*/
typedef void(^LTCRetractConcernCompletion)(NSError *error);

/**
 The LTCClientApi will provide the application with the nececcary queries to interact with the 
 app engine data store.
 */
@interface LTCClientApi : NSObject
/**
 Submits a concern to the datastore using a datastore query.
 
 @param concern         The datastore concern data to be submitted.
 @param completion      The completion block that will be used to bring back a possible error message, and a datastore concern response, which contains the concern that was submitted, and an ownerToken for that concern.
 */
- (void)submitConcern:(GTLRClient_ConcernData *)concern completion:(LTCSubmitConcernCompletion)completion;
/**
 Retracts a concern to the datastore using a datastore query.
 
 @param ownerToken      The string representation of the ownerToken to be retracted.
 @param completion      The completion block that will be used to bring back a possible error message.
 */
- (void)retractConcern:(NSString *)ownerToken completion:(LTCRetractConcernCompletion)completion;
@end

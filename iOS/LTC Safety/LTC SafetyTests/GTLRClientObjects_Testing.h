//
//  GTLRClientObjects_Testing.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "GTLRClient.h"

/**
 A GTLRClient_Concern class extension to create GTLRClient_Concern's with pre-populated test data.
 */
@interface GTLRClient_Concern (Testing)

/**
 Creates a GTLRClient_Concern object pre-populated with random test data.

 @return A GTLRClient_Concern object.
 */
+ (GTLRClient_Concern *)testConcern;
@end

/**
 A GTLRClient_ConcernData class extension to create GTLRClient_ConcernData with pre-populated test data.
 */
@interface GTLRClient_ConcernData (Testing)

/**
 Creates a GTLRClient_ConcernData object pre-populated with random test data.
 
 @return A GTLRClient_ConcernData object.
 */
+ (GTLRClient_ConcernData *)testConcernData;
@end

/**
 A GTLRClient_SubmitConcernResponse class extension to create GTLRClient_SubmitConcernResponse with pre-populated test data.
 */
@interface GTLRClient_SubmitConcernResponse (Testing)

/**
 Creates a GTLRClient_SubmitConcernResponse object pre-populated with random test data.
 
 @return A GTLRClient_SubmitConcernResponse object.
 */
+ (GTLRClient_SubmitConcernResponse *)testSubmitConcernResponse;
@end

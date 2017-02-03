//
//  GTLRClientObjects_Testing.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GTLRClientObjects_Testing.h"

@implementation GTLRClient_Concern (Testing)

+ (GTLRClient_Concern *)testConcern {
    
    GTLRClient_Concern *concern = [[GTLRClient_Concern alloc] init];
    
    concern.archived = @NO;
    concern.identifier = [NSNumber numberWithLongLong:123456];
    concern.submissionDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    concern.data = [GTLRClient_ConcernData testConcernData];

    GTLRClient_ConcernStatus *status = [[GTLRClient_ConcernStatus alloc] init];
    status.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    status.type = @"PENDING";
    concern.statuses = [NSArray arrayWithObject:status];

    return concern;
}

@end

@implementation GTLRClient_ConcernData (Testing)

+ (GTLRClient_ConcernData *)testConcernData {
    
    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = [NSUUID UUID].UUIDString;
    reporter.phoneNumber = [NSUUID UUID].UUIDString;
    reporter.email = [NSUUID UUID].UUIDString;
    
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = [NSUUID UUID].UUIDString;
    location.roomName = [NSUUID UUID].UUIDString;
    
    GTLRClient_ConcernData *data = [[GTLRClient_ConcernData alloc] init];
    data.reporter = reporter;
    data.location = location;
    data.concernNature = [NSUUID UUID].UUIDString;
    data.actionsTaken = [NSUUID UUID].UUIDString;
    
    return data;
}

@end

@implementation GTLRClient_SubmitConcernResponse (Testing)

+ (GTLRClient_SubmitConcernResponse *)testSubmitConcernResponse {
    
    GTLRClient_OwnerToken *ownerToken = [[GTLRClient_OwnerToken alloc] init];
    ownerToken.token = [NSUUID UUID].UUIDString;
    
    GTLRClient_SubmitConcernResponse *submitConcernResponse = [[GTLRClient_SubmitConcernResponse alloc] init];
    submitConcernResponse.concern = [GTLRClient_Concern testConcern];
    submitConcernResponse.ownerToken = ownerToken;
    
    return submitConcernResponse;
}

@end

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
    concern.data = [self testConcernData];

    GTLRClient_ConcernStatus *status = [[GTLRClient_ConcernStatus alloc] init];
    status.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    status.type = @"PENDING";
    concern.statuses = [NSArray arrayWithObject:status];

    return concern;
}

+ (GTLRClient_ConcernData *)testConcernData {
    
    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = @"Name";
    reporter.phoneNumber = @"Phone number";
    reporter.email = @"Email";
    
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = @"Facility";
    location.roomName = @"Room";
    
    GTLRClient_ConcernData *data = [[GTLRClient_ConcernData alloc] init];
    data.reporter = reporter;
    data.location = location;
    data.concernNature = @"Nature of concern";
    data.actionsTaken = @"Actions taken";
    
    return data;
}

@end

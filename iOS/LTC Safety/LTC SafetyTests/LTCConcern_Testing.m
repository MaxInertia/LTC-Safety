//
//  LTCConcern_Testing.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcern_Testing.h"

@implementation LTCConcern (Testing)

+ (instancetype)testConcernWithContext:(NSManagedObjectContext *)context {

    NSEntityDescription *locationDescription = [NSEntityDescription entityForName:@"LTCLocation" inManagedObjectContext:context];
    LTCLocation *location = [[LTCLocation alloc] initWithEntity:locationDescription insertIntoManagedObjectContext:context];
    location.roomName = @"A room name";
    location.facilityName = @"A facility name";
    
    NSEntityDescription *reportDescription = [NSEntityDescription entityForName:@"LTCReporter" inManagedObjectContext:context];
    LTCReporter *reporter = [[LTCReporter alloc] initWithEntity:reportDescription insertIntoManagedObjectContext:context];
    reporter.name = @"A reporter name";
    reporter.phoneNumber = @"A reporter phone number";
    reporter.email = @"A reporter email";
    
    NSEntityDescription *concernDescription = [NSEntityDescription entityForName:@"LTCConcern" inManagedObjectContext:context];
    LTCConcern *concern = [[LTCConcern alloc] initWithEntity:concernDescription insertIntoManagedObjectContext:context];
    concern.reporter = reporter;
    concern.location = location;
    concern.concernNature = @"The nature of the concern";
    concern.actionsTaken = @"The actions taken";
    concern.identifier = @"1234678990";
    concern.submissionDate = [NSDate date];
    concern.ownerToken = [NSUUID UUID].UUIDString;
    
    NSMutableOrderedSet *statuses = [[NSMutableOrderedSet alloc] initWithCapacity:5];
    for (int i = 0; i < 5; i++) {
        
        NSEntityDescription *statusDescription = [NSEntityDescription entityForName:@"LTCConcernStatus" inManagedObjectContext:context];
        LTCConcernStatus *status = [[LTCConcernStatus alloc] initWithEntity:statusDescription insertIntoManagedObjectContext:context];
        status.creationDate = [NSDate date];
        status.concernType = @"PENDING";
        [statuses addObject:status];
    }
    
    
    concern.statuses = [statuses copy];
    
    return concern;
}


@end

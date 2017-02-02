//
//  LTCConcernTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClient.h"
#import "LTCConcern+CoreDataClass.h"

@interface LTCConcernTests : XCTestCase

@end

@implementation LTCConcernTests

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testConcernWithConcern {

    GTLRClient_Concern *concern = [[GTLRClient_Concern alloc] init];
    concern.archived = @NO;
    concern.identifier = @100000000;
    
    concern.submissionDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    
    GTLRClient_ConcernStatus *status = [[GTLRClient_ConcernStatus alloc] init];
    status.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    status.type = @"PENDING";
    concern.statuses = [NSArray arrayWithObject:status];
    
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
    
    concern.data = data;
    
    LTCConcern *localConcern = [LTCConcern concernWithConcern:concern];
    XCTAssertNotNil(localConcern);
    
    XCTAssertEqual(concern.submissionDate.date, localConcern.submissionDate);
    
    XCTAssertEqual([concern.identifier unsignedLongValue], localConcern.identifier);
    XCTAssertEqual(concern.data.actionsTaken, localConcern.actionsTaken);
    XCTAssertEqual(concern.data.concernNature, localConcern.concernNature);
    
    XCTAssertEqual(concern.data.reporter.phoneNumber, localConcern.reporter.phoneNumber);
    XCTAssertEqual(concern.data.reporter.email, localConcern.reporter.email);
    XCTAssertEqual(concern.data.reporter.name, localConcern.reporter.name);

    XCTAssertEqual(concern.data.location.facilityName, localConcern.location.facilityName);
    XCTAssertEqual(concern.data.location.roomName, localConcern.location.roomName);
    
    for (int i = 0; i < concern.statuses.count; i++) {
        GTLRClient_ConcernStatus *status = [concern.statuses objectAtIndex:i];
        LTCConcernStatus *localStatus = [localConcern.statuses objectAtIndex:i];

        XCTAssertEqual(status.type, localStatus.concernType);
        XCTAssertEqual(status.creationDate.date, localStatus.creationDate);
    }
}

@end

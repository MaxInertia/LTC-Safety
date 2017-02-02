//
//  LTCLocationTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClient.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCCoreDataTestCase.h"

@interface LTCLocationTests : LTCCoreDataTestCase
@property (nonatomic, strong) GTLRClient_Location *location;
@end

@implementation LTCLocationTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testLocationWithData {
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = @"Facility";
    location.roomName = @"Room";
    
    LTCLocation *localLocation = [LTCLocation locationWithData:location inManagedObjectContext:self.container.viewContext];
    XCTAssertNotNil(localLocation);
    
    XCTAssertEqual(location.facilityName, localLocation.facilityName);
    XCTAssertEqual(location.roomName, localLocation.roomName);
    
    
}
@end

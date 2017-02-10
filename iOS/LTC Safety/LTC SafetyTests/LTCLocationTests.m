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
/**
 Unit tests for the LTCConcernLocation Core Data class.
 */
@interface LTCLocationTests : LTCCoreDataTestCase
/**
 The datastore concern location that will be used to compare with the local concern location.
 */
@property (nonatomic, strong) GTLRClient_Location *location;
@end

@implementation LTCLocationTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}
/**
 Tests the concern location with data method by creating a local concern location and a datastore concern location then comparing them to make sure all of their data is the same.
 */
- (void)testLocationWithData {
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = @"Facility";
    location.roomName = @"Room";
    
    LTCLocation *localLocation = [LTCLocation locationWithData:location inManagedObjectContext:self.context];
    XCTAssertNotNil(localLocation);
    
    XCTAssertEqual(location.facilityName, localLocation.facilityName);
    XCTAssertEqual(location.roomName, localLocation.roomName);
    
    
}
@end

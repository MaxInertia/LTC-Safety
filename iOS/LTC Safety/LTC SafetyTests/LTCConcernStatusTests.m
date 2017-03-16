//
//  LTCConcernStatusTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClient.h"
#import "LTCConcernStatus+CoreDataClass.h"
#import "LTCCoreDataTestCase.h"

/**
 Unit tests for the LTCConcernStatus Core Data class.
 */
@interface LTCConcernStatusTests : LTCCoreDataTestCase
/**
 The datastore concern status that will be used to compare with the local concern status.
 */
@property (nonatomic, strong) GTLRClient_ConcernStatus *concernStatus;
@end

@implementation LTCConcernStatusTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}
/**
 Tests the concern status with data method by creating a local concern status and a datastore concern status then comparing them to make sure all of their data is the same.
 */
- (void)testConcernStatusWithData {
    GTLRClient_ConcernStatus *concernStatus = [[GTLRClient_ConcernStatus alloc] init];
    concernStatus.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    concernStatus.type = @"PENDING";
    
    LTCConcernStatus *localConcernStatus = [LTCConcernStatus statusWithData:concernStatus inManagedObjectContext:self.context];
    XCTAssertNotNil(localConcernStatus);
    
    XCTAssertEqual(concernStatus.type, localConcernStatus.concernType);
    XCTAssertTrue([concernStatus.creationDate.date isEqualToDate:localConcernStatus.creationDate]);
    
}
@end

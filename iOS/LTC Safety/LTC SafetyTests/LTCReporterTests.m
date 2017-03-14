//
//  LTCReporterTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClient.h"
#import "LTCReporter+CoreDataClass.h"
#import "LTCCoreDataTestCase.h"
/**
 Unit tests for the LTCConcernReporter Core Data class.
 */
@interface LTCReporterTests : LTCCoreDataTestCase
/**
 The datastore concern reporter that will be used to compare with the local concern reporter.
 */
@property (nonatomic, strong) GTLRClient_Reporter *reporter;
@end

@implementation LTCReporterTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}
/**
 Tests the concern reporter with data method by creating a local concern reporter and a datastore concern reporter then comparing them to make sure all of their data is the same.
 */
- (void)testReporterWithData {
    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = @"Name";
    reporter.phoneNumber = @"Phone number";
    reporter.email = @"Email";
    
    LTCReporter *localReporter = [LTCReporter reporterWithData:reporter inManagedObjectContext:self.context];
    XCTAssertNotNil(localReporter);
    
    XCTAssertEqual(reporter.name, localReporter.name);
    XCTAssertEqual(reporter.phoneNumber, localReporter.phoneNumber);
    XCTAssertEqual(reporter.email, localReporter.email);
    
    
}
@end

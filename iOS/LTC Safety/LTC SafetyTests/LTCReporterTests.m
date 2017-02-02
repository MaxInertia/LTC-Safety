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

@interface LTCReporterTests : LTCCoreDataTestCase
@property (nonatomic, strong) GTLRClient_Reporter *reporter;
@end

@implementation LTCReporterTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testReporterWithData {
    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = @"Name";
    reporter.phoneNumber = @"Phone number";
    reporter.email = @"Email";
    
    LTCReporter *localReporter = [LTCReporter reporterWithData:reporter inManagedObjectContext:self.container.viewContext];
    XCTAssertNotNil(localReporter);
    
    XCTAssertEqual(reporter.name, localReporter.name);
    XCTAssertEqual(reporter.phoneNumber, localReporter.phoneNumber);
    XCTAssertEqual(reporter.email, localReporter.email);
    
    
}
@end

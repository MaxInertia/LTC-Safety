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

@interface LTCConcernStatusTests : LTCCoreDataTestCase
@property (nonatomic, strong) GTLRClient_ConcernStatus *concernStatus;
@end

@implementation LTCConcernStatusTests : LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testConcernStatusWithData {
    GTLRClient_ConcernStatus *concernStatus = [[GTLRClient_ConcernStatus alloc] init];
    concernStatus.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    concernStatus.type = @"PENDING";
    
    LTCConcernStatus *localConcernStatus = [LTCConcernStatus statusWithData:concernStatus inManagedObjectContext:self.container.viewContext];
    XCTAssertNotNil(localConcernStatus);
    
    XCTAssertEqual(concernStatus.type, localConcernStatus.concernType);
    XCTAssertTrue([concernStatus.creationDate.date isEqualToDate:localConcernStatus.creationDate]);
    
}
@end

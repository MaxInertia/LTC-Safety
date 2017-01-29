//
//  LTCConcernViewModelTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCConcern+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCConcernViewModel.h"
#import "LTCCoreDataTestCase.h"
@import CoreData;

@interface LTCConcernViewModelTests : LTCCoreDataTestCase

@end

@implementation LTCConcernViewModelTests

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testAddConcern {
    
    XCTAssertNotNil(self.container.viewContext, @"Attempted to run test with a nil object context.");

    LTCLocation *location = [[LTCLocation alloc] initWithContext:self.container.viewContext];
    location.roomName = @"A room name";
    location.facilityName = @"A facility name";
    
    LTCReporter *reporter = [[LTCReporter alloc] initWithContext:self.container.viewContext];
    reporter.name = @"A reporter name";
    reporter.phoneNumber = @"A reporter phone number";
    reporter.email = @"A reporter email";
    
    LTCConcern *concern = [LTCConcern concernInManagedObjectContext:self.container.viewContext];
    concern.reporter = reporter;
    concern.location = location;
    concern.concernNature = @"The nature of the concern";
    concern.actionsTaken = @"The actions taken";
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.container.viewContext];
    [viewModel addConcern:concern error:&error];
    
    XCTAssertNil(error, @"%@", error);
}

- (void)testRemoveConcern {
    
}

- (void)testConcernAtIndexPath {
    
}

- (void)testRowCountForSection {

}

@end

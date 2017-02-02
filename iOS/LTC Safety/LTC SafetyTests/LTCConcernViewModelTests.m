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
#import "UICKeyChainStore.h"
#import "LTCConcern_Testing.h"
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

    LTCConcern *concern = [LTCConcern testConcernWithContext:self.container.viewContext];
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.container.viewContext];
    [viewModel addConcern:concern error:&error];
    
    XCTAssertNil(error, @"%@", error);
    XCTAssertFalse(concern.objectID.temporaryID);
    
    
    NSString *bundleIdentifier = [NSBundle mainBundle].bundleIdentifier;
    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:bundleIdentifier];    
    XCTAssertNotNil( [keychain valueForKey:concern.identifier]);
}

- (void)testRemoveConcern {
    
    XCTAssertNotNil(self.container.viewContext, @"Attempted to run test with a nil object context.");
    
    LTCConcern *concern = [LTCConcern testConcernWithContext:self.container.viewContext];

    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.container.viewContext];
    
    [viewModel addConcern:(concern) error:nil];
    
    NSError *error = nil;
    [viewModel removeConcern:(concern) error:(&error)];
    
    XCTAssertNil(error, @"%@", error);

}

- (void)testConcernAtIndexPath {
    
    XCTAssertNotNil(self.container.viewContext, @"Attempted to run test with a nil object context.");

    LTCConcern *concern1 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern2 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern3 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern4 = [LTCConcern testConcernWithContext:self.container.viewContext];

    NSIndexPath *indexPath1 = [NSIndexPath indexPathForRow:0 inSection:0];
    NSIndexPath *indexPath2 = [NSIndexPath indexPathForRow:1 inSection:0];
    NSIndexPath *indexPath3 = [NSIndexPath indexPathForRow:2 inSection:0];
    NSIndexPath *indexPath4 = [NSIndexPath indexPathForRow:3 inSection:0];

    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.container.viewContext];

    [viewModel addConcern:concern1 error:&error];
    [viewModel addConcern:concern2 error:&error];
    [viewModel addConcern:concern3 error:&error];
    [viewModel addConcern:concern4 error:&error];

    XCTAssertEqual(concern1, [viewModel concernAtIndexPath:(indexPath1)]);
    XCTAssertEqual(concern2, [viewModel concernAtIndexPath:(indexPath2)]);
    XCTAssertEqual(concern3, [viewModel concernAtIndexPath:(indexPath3)]);
    XCTAssertEqual(concern4, [viewModel concernAtIndexPath:(indexPath4)]);

}

- (void)testRowCountForSection {
    
    XCTAssertNotNil(self.container.viewContext, @"Attempted to run test with a nil object context.");
    
    LTCConcern *concern1 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern2 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern3 = [LTCConcern testConcernWithContext:self.container.viewContext];
    LTCConcern *concern4 = [LTCConcern testConcernWithContext:self.container.viewContext];
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.container.viewContext];
    
    [viewModel addConcern:concern1 error:&error];
    [viewModel addConcern:concern2 error:&error];
    [viewModel addConcern:concern3 error:&error];
    [viewModel addConcern:concern4 error:&error];

    XCTAssertEqual([viewModel rowCountForSection:0], 4);
}

@end

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
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>

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
    
    XCTAssertNotNil(self.context, @"Attempted to run test with a nil object context.");

    LTCConcern *concern = [LTCConcern testConcernWithContext:self.context];
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    [viewModel addConcern:concern error:&error];
    
    XCTAssertNil(error);
    XCTAssertFalse(concern.objectID.temporaryID);
    
    NSString *bundleIdentifier = [NSBundle mainBundle].bundleIdentifier;
    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:bundleIdentifier];
    
    XCTAssertTrue([[keychain stringForKey:concern.identifier] isEqualToString:concern.ownerToken]);
}

- (void)testRemoveConcern {
    
    XCTAssertNotNil(self.context, @"Attempted to run test with a nil object context.");
    
    id <LTCConcernViewModelDelegate> delegate = mockProtocol(@protocol(LTCConcernViewModelDelegate));
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    
    // Add mock delegate
    viewModel.delegate = delegate;
    
    LTCConcern *concern = [LTCConcern testConcernWithContext:self.context];
    [viewModel addConcern:concern error:&error];
    
    ([verify(delegate) viewModelWillBeginUpdates:anything()]);
    ([verify(delegate) viewModel:anything() didInsertConcernsAtIndexPaths:anything()]);
    ([verify(delegate) viewModelDidFinishUpdates:anything()]);

    XCTAssertNil(error);
    XCTAssertEqual([viewModel concernAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]].identifier, concern.identifier);
    
    error = nil;
    [viewModel removeConcern:concern error:&error];
    XCTAssertNil(error);
    
    XCTAssertEqual([viewModel rowCountForSection:0], 0);
    
    ([verify(delegate) viewModelWillBeginUpdates:anything()]);
    ([verify(delegate) viewModel:anything() didDeleteConcernsAtIndexPaths:anything()]);
    ([verify(delegate) viewModelDidFinishUpdates:anything()]);    
}

- (void)testConcernAtIndexPath {
    
    XCTAssertNotNil(self.context, @"Attempted to run test with a nil object context.");

    LTCConcern *concern1 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern2 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern3 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern4 = [LTCConcern testConcernWithContext:self.context];

    NSIndexPath *indexPath1 = [NSIndexPath indexPathForRow:0 inSection:0];
    NSIndexPath *indexPath2 = [NSIndexPath indexPathForRow:1 inSection:0];
    NSIndexPath *indexPath3 = [NSIndexPath indexPathForRow:2 inSection:0];
    NSIndexPath *indexPath4 = [NSIndexPath indexPathForRow:3 inSection:0];

    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];

    [viewModel addConcern:concern1 error:&error];
    [viewModel addConcern:concern2 error:&error];
    [viewModel addConcern:concern3 error:&error];
    [viewModel addConcern:concern4 error:&error];

    XCTAssertEqual(concern1, [viewModel concernAtIndexPath:(indexPath4)]);
    XCTAssertEqual(concern2, [viewModel concernAtIndexPath:(indexPath3)]);
    XCTAssertEqual(concern3, [viewModel concernAtIndexPath:(indexPath2)]);
    XCTAssertEqual(concern4, [viewModel concernAtIndexPath:(indexPath1)]);
}

- (void)testRowCountForSection {
    
    XCTAssertNotNil(self.context, @"Attempted to run test with a nil object context.");
    
    LTCConcern *concern1 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern2 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern3 = [LTCConcern testConcernWithContext:self.context];
    LTCConcern *concern4 = [LTCConcern testConcernWithContext:self.context];
    
    NSError *error = nil;
    LTCConcernViewModel *viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    
    [viewModel addConcern:concern1 error:&error];
    [viewModel addConcern:concern2 error:&error];
    [viewModel addConcern:concern3 error:&error];
    [viewModel addConcern:concern4 error:&error];

    XCTAssertEqual([viewModel rowCountForSection:0], 4);
    XCTAssertEqual([viewModel sectionCount], 1);
}

@end

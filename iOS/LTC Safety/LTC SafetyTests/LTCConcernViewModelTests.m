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
#import "LTCConcernViewController.h"
#import "LTCCoreDataTestCase.h"
#import "UICKeyChainStore.h"
#import "LTCConcern_Testing.h"
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>


@import CoreData;



@interface LTCConcernViewModel ()
-(void)_updateConcernsStatus:(NSArray<GTLRClient_Concern *>*)concerns;
@end
/**
 Unit tests for the LTCConcernViewModelTests class.
 */
@interface LTCConcernViewModelTests : LTCCoreDataTestCase

@end


@implementation LTCConcernViewModelTests

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

/**
 Tests the addition of a concrn by creating a viewModel and then adding a newly allocated concern to it. If there is no error, the objectID.temporaryID is false, and the keychain has stored the correct owner token, the test passes.
 */
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

/**
 Tests the retraction of a concern. The test adds a concern to the allocated viewModel, then checks for errors, then removes the concern from the viewModel and checks again for any errors and also checks that the row count for the section is 0.
 */
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

/**
 Tests the concern at index path method in the concern view model by adding 4 concern to an allocated view model and making sure that the index paths of each of the models can be accessed and that they have been set in ascending order.
 */
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

/**
 Tests the row count method in the concern view model by creating 4 concerns and adding them to a view model and then making sure that the row cound is also returned as 4 and that there is 1 section.
 */
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

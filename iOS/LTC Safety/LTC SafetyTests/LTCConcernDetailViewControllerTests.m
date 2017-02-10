//
//  LTCConcernDetailViewControllerTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-08.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCConcernDetailViewController.h"
#import "LTCCoreDataTestCase.h"
#import "LTCConcern_Testing.h"


/**
 Tests the functionality of the new concern view controller.
 */
@interface LTCConcernDetailViewController ()

/**
 The viewModel that will be used to store the test concerns for this unit test class.
 */
@property (readwrite, nonatomic, strong) LTCConcernDetailViewModel *viewModel;

@end


@interface LTCConcernDetailViewControllerTests : LTCCoreDataTestCase

@end

@implementation LTCConcernDetailViewControllerTests
/**
 Tests that when a view controller is initialized with a view model that that view controller inherits the characteristics of the view model.
 */
- (void)testInitWithViewModel {
    LTCConcern *testConcern = [LTCConcern testConcernWithContext:self.context];
    LTCConcernDetailViewModel *viewModel = [[LTCConcernDetailViewModel alloc] initWithConcern:testConcern];
    LTCConcernDetailViewController *viewController = [[LTCConcernDetailViewController alloc] initWithViewModel:viewModel];
    
    XCTAssertEqual(viewModel, viewController.viewModel);
    XCTAssertNotNil(viewController.title);
}
@end

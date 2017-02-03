//
//  LTCNewConcernViewControllerTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCNewConcernViewController.h"
#import "LTCCoreDataTestCase.h"
#import "LTCNewConcernViewModelMock.h"

@interface LTCNewConcernViewController ()
@property (readonly, nonatomic, weak) LTCNewConcernViewModel *viewModel;
- (void)submit;
- (void)cancel;
@end


@interface LTCNewConcernViewControllerTests : LTCCoreDataTestCase <LTCNewConcernViewControllerDelegate>
@property (nonatomic, assign) BOOL receivedDelegateCallback;
@end

@implementation LTCNewConcernViewControllerTests

- (void)setUp {
    self.receivedDelegateCallback = NO;
    [super setUp];
}

- (void)testInitWithViewModel {
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.container.viewContext];
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModel];
    
    XCTAssertEqual(viewModel, viewController.viewModel);
    XCTAssertNotNil(viewController.title);
    XCTAssertNotNil(viewController.navigationItem.leftBarButtonItem);
}

- (void)testViewModel {
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.container.viewContext];
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModel];
    
    XCTAssertEqual(viewModel, viewController.viewModel);
    XCTAssertTrue([NSStringFromSelector(viewController.viewModel.submissionCallback) isEqualToString:NSStringFromSelector(@selector(submit))]);
}

- (void)testSubmit {
 
    // Pass the mock view model
    LTCNewConcernViewModelMock *viewModelMock = [[LTCNewConcernViewModelMock alloc] initWithContext:self.container.viewContext];
    viewModelMock.simulateError = NO;
    
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModelMock];
    viewController.delegate = self;
    [viewController submit];
    
    XCTAssertTrue(self.receivedDelegateCallback);
}

- (void)testSubmitError {
    
    // Pass the mock view model
    LTCNewConcernViewModelMock *viewModelMock = [[LTCNewConcernViewModelMock alloc] initWithContext:self.container.viewContext];
    viewModelMock.simulateError = YES;
    
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModelMock];
    viewController.delegate = self;
    [viewController submit];
    
    XCTAssertFalse(self.receivedDelegateCallback);
}

- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern {
    
    XCTAssertNotNil(viewController);
    XCTAssertNotNil(concern);
    
    self.receivedDelegateCallback = YES;
}

@end

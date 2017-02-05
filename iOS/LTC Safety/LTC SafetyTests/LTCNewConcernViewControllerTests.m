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
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>
#import "LTCConcern_Testing.h"

/**
 Tests the functionality of the new concern view controller.
 */
@interface LTCNewConcernViewController ()

/**
 The viewModel that will be used to store the test concerns for this unit test class.
 */
@property (readonly, nonatomic, weak) LTCNewConcernViewModel *viewModel;
- (void)submit;
- (void)cancel;
@end


@interface LTCNewConcernViewControllerTests : LTCCoreDataTestCase

@end

@implementation LTCNewConcernViewControllerTests
/**
 Tests that when a view controller is initialized with a view model that that view controller inherits the characteristics of the view model.
 */
- (void)testInitWithViewModel {
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModel];
    
    XCTAssertEqual(viewModel, viewController.viewModel);
    XCTAssertNotNil(viewController.title);
    XCTAssertNotNil(viewController.navigationItem.leftBarButtonItem);
}

/**
 Tests that when a view controller is initialized with a view model that that model is actually a part of the controller.
 */
- (void)testViewModel {
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModel];
    
    XCTAssertEqual(viewModel, viewController.viewModel);
    XCTAssertTrue([NSStringFromSelector(viewController.viewModel.submissionCallback) isEqualToString:NSStringFromSelector(@selector(submit))]);
}

/**
 Tests the submit concern functionality without an error of the new concern model controller by calling the submit method in the view model and make sure the delegate method is called in the view controller.
 */
- (void)testSubmit {
    
    id <LTCNewConcernViewControllerDelegate> delegate = mockProtocol(@protocol(LTCNewConcernViewControllerDelegate));
    LTCNewConcernViewModel *viewModelMock = mock([LTCNewConcernViewModel class]);
    
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModelMock];
    viewController.delegate = delegate;
    [viewController submit];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(viewModelMock) submitConcernData:(id)argument];
    void (^completion)(LTCConcern *concern, NSError *error) = [argument value];
    completion([LTCConcern testConcernWithContext:self.context], nil);
    
    [verify(delegate) viewController:viewController didSubmitConcern:anything()];
}

/**
 Tests the submit concern functionality with an error of the new concern model controller by calling the submit method in the view model and make sure the delegate method is never called in the view controller.
 */
- (void)testSubmitError {
    
    NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
    NSError *passedError = [NSError errorWithDomain:identifier code:0 userInfo:nil];
    
    id <LTCNewConcernViewControllerDelegate> delegate = mockProtocol(@protocol(LTCNewConcernViewControllerDelegate));
    LTCNewConcernViewModel *viewModelMock = mock([LTCNewConcernViewModel class]);
    
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModelMock];
    viewController.delegate = delegate;
    [viewController submit];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(viewModelMock) submitConcernData:(id)argument];
    void (^completion)(LTCConcern *concern, NSError *error) = [argument value];
    completion(nil, passedError);
    
    [verifyCount(delegate, never()) viewController:viewController didSubmitConcern:anything()];
}

@end

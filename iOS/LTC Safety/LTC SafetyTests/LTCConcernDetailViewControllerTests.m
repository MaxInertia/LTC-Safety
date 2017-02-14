//
//  LTCConcernDetailViewControllerTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-08.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCConcernDetailViewController.h"
#import "LTCConcernViewModel.h"
#import "LTCCoreDataTestCase.h"
#import "LTCConcern_Testing.h"
#import "LTCClientApi.h"
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>


/**
 Tests the functionality of the new concern view controller.
 */
@interface LTCConcernDetailViewController ()

/**
 The viewModel that will be used to store the test concerns for this unit test class.
 */
@property (readwrite, nonatomic, strong) LTCConcernDetailViewModel *viewModel;
@property (nonatomic, strong) NSNotificationCenter *notificationCenter;
@property (nonatomic, strong) LTCClientApi *clientApi;

- (void)_retractConcern;
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
/**
 Tests that the retract functionality works by mocking a client api and a notification center then using those to check that the client api catches the notification that was thrown by the retract concern method
 */
- (void)testRetract {
    LTCClientApi *mockApi = mock([LTCClientApi class]);
    NSNotificationCenter *mockCenter = mock([NSNotificationCenter class]);
    LTCConcern *testConcern = [LTCConcern testConcernWithContext:self.context];
    LTCConcernDetailViewModel *viewModel = [[LTCConcernDetailViewModel alloc] initWithConcern:testConcern];
    LTCConcernDetailViewController *viewController = [[LTCConcernDetailViewController alloc] initWithViewModel:viewModel];
    viewController.clientApi = mockApi;
    viewController.notificationCenter = mockCenter;
    [viewController _retractConcern];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    
    [verify(mockApi) retractConcern:anything() completion:(id)argument];
    void (^completion)(GTLRClient_UpdateConcernStatusResponse *response, NSError *error) = [argument value];
    
    GTLRClient_UpdateConcernStatusResponse *retractConcernResponse = [[GTLRClient_UpdateConcernStatusResponse alloc] init];

    retractConcernResponse.concernId = @(12345);
    retractConcernResponse.status = [[GTLRClient_ConcernStatus alloc] init];
    
    completion(retractConcernResponse, nil);
    
    HCArgumentCaptor *notificationArgument = [[HCArgumentCaptor alloc] init];

    
    [verify(mockCenter) postNotificationName: LTCUpdatedConcernStatusNotification object:anything() userInfo:(id) notificationArgument];
    
    NSDictionary *test = [notificationArgument value];
    
    XCTAssertEqual(test[@"status"], retractConcernResponse);    
    
}

@end

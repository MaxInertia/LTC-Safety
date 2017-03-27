//
//  LTCConcernViewControllerTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCConcernViewController.h"
#import "LTCConcernViewModel.h"
#import "LTCCoreDataTestCase.h"
#import "LTCNewConcernViewController.h"
#import "LTCConcern_Testing.h"
#import "LTCClientApi.h"
#import "LTCConcernTableViewCell.h"
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>
/**
 Tests the functionality of the concern view controller.
 */
@interface LTCConcernViewController ()

/**
 The test button needed to be imolimented as to not have an assertion fail in the viewDidLoad of the concern view controller
 */
@property (nonatomic, weak) IBOutlet UIButton *addConcernButton;

@property (nonatomic, strong) LTCClientApi *clientApi;

/**
 The test tableView needed to be imolimented as to not have an assertion fail in the viewDidLoad of the concern view controller
 */
@property (nonatomic, weak) IBOutlet UITableView *tableView;
- (IBAction)presentCreateConcernController:(id)sender;
- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern;
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath;
- (void)_refresh;
@end

@interface LTCConcernViewControllerTests : LTCCoreDataTestCase

@end

@implementation LTCConcernViewControllerTests


/**
 Tests the presentation method of the new concern view controller by presenting a LTCNewConcernViewController. The test method then checks that this has actually presented the view controller and that the presentedViewContoller is of type UINavigationController and LTCNewConcernViewController is it's child.
 */

- (void)testPresentCreateConcernController {
    
    __strong UITableView *tableView =  [[UITableView alloc] init];
    __strong UIButton *addConcernButton = [UIButton buttonWithType:UIButtonTypeSystem];;
    
    LTCConcernViewController *viewController = [[LTCConcernViewController alloc] init];
    viewController.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    //Adding concern button and table view to prevent assertions failing in the viewDidLoad of the concern view controller.

    viewController.addConcernButton = addConcernButton;
    viewController.tableView = tableView;
    
    [UIApplication sharedApplication].keyWindow.rootViewController = viewController;
    [viewController presentCreateConcernController:self];
    
    XCTAssertNotNil(viewController.presentedViewController);
    XCTAssertTrue([viewController.presentedViewController isKindOfClass:[UINavigationController class]]);
    XCTAssertTrue([[viewController.presentedViewController.childViewControllers firstObject] isKindOfClass:[LTCNewConcernViewController class]]);
}

/**
 Tests the submission, updation, and removal of concerns by adding a concern to the view controller, making sure it has been assigned an index in the model, updating the concern nature and making sure the concern nature is correct, and then removing the concern and making sure the model has been updated accordingly.
 */
- (void)testSubmitUpdateRemoveConcern {
    
    __strong UITableView *tableView =  [[UITableView alloc] init];
    __strong UIButton *addConcernButton = [UIButton buttonWithType:UIButtonTypeSystem];;
    
    [tableView registerClass:[LTCConcernTableViewCell class] forCellReuseIdentifier:@"Cell"];
    
    LTCConcernViewController *viewController = [[LTCConcernViewController alloc] init];
    viewController.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    
    //Adding concern button and table view to prevent assertions failing in the viewDidLoad of the concern view controller.
    viewController.addConcernButton = addConcernButton;
    viewController.tableView = tableView;
    
    XCTAssertEqual([viewController.viewModel sectionCount], 1);
    XCTAssertEqual([viewController.viewModel rowCountForSection:0], 0);
    
    // Attempt to insert the concern
    LTCConcern *concern = [LTCConcern testConcernWithContext:self.context];
    [viewController viewController:nil didSubmitConcern:concern];
    
    XCTAssertEqual([viewController.viewModel sectionCount], 1);
    XCTAssertEqual([viewController.viewModel rowCountForSection:0], 1);
    XCTAssertEqual([viewController.viewModel concernAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]].identifier, concern.identifier);
    
    // Update the concern causing it to be reloaded
    concern.concernNature = [NSUUID UUID].UUIDString;
    [viewController viewController:nil didSubmitConcern:concern];
    
    XCTAssertEqual([viewController.viewModel concernAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]].concernNature, concern.concernNature);
    
    // Remove the concern
    NSError *error = nil;
    [viewController.viewModel removeConcern:concern error:&error];
    
    XCTAssertEqual([viewController.viewModel rowCountForSection:0], 0);
    XCTAssertEqual([viewController.viewModel sectionCount], 1);
}
/**
 Tests the refresh of concerns in the view model by mocking the clientApi call to fetchConcerns and checking that the correct behaviour is achieved.
 */
-(void)testRefresh {
    /*
    LTCClientApi *mockApi = mock([LTCClientApi class]);
    LTCConcern *testConcern = [LTCConcern testConcernWithContext:self.context];
    __strong UITableView *tableView =  [[UITableView alloc] init];
    __strong UIButton *addConcernButton = [UIButton buttonWithType:UIButtonTypeSystem];;
    
    LTCConcernViewController *viewController = [[LTCConcernViewController alloc] init];
    viewController.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    
    //Adding concern button and table view to prevent assertions failing in the viewDidLoad of the concern view controller.
    viewController.addConcernButton = addConcernButton;
    viewController.tableView = tableView;
    
    viewController.clientApi = mockApi;
    NSError *error;
    [viewController.viewModel addConcern:testConcern error:&error];
    
    LTCConcernStatus *newStatus = [[LTCConcernStatus alloc] init];
    
    //[testConcern addStatusesObject:newStatus];
    
    [viewController refresh];
    
    //XCTAssert([[viewController.viewModel concernAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]].statuses lastObject] == newStatus);
    
    
    
    
    */
    
    
    
    
}

@end

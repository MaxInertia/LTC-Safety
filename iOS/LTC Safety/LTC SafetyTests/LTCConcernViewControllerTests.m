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
#import "LTCConcernTableViewCell.h"

@interface LTCConcernViewController ()
@property (nonatomic, weak) IBOutlet UIButton *addConcernButton;
@property (nonatomic, weak) IBOutlet UITableView *tableView;
- (IBAction)presentCreateConcernController:(id)sender;
- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern;
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath;
@end

@interface LTCConcernViewControllerTests : LTCCoreDataTestCase

@end

@implementation LTCConcernViewControllerTests

- (void)testPresentCreateConcernController {
    
    __strong UITableView *tableView =  [[UITableView alloc] init];
    
    LTCConcernViewController *viewController = [[LTCConcernViewController alloc] init];
    viewController.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    viewController.addConcernButton = [UIButton buttonWithType:UIButtonTypeSystem];
    viewController.tableView = tableView;
    
    [UIApplication sharedApplication].keyWindow.rootViewController = viewController;
    [viewController presentCreateConcernController:self];

    XCTAssertNotNil(viewController.presentedViewController);
    XCTAssertTrue([viewController.presentedViewController isKindOfClass:[UINavigationController class]]);
    XCTAssertTrue([[viewController.presentedViewController.childViewControllers firstObject] isKindOfClass:[LTCNewConcernViewController class]]);
}

- (void)testSubmitUpdateRemoveConcern {
    
    __strong UITableView *tableView =  [[UITableView alloc] init];
    [tableView registerClass:[LTCConcernTableViewCell class] forCellReuseIdentifier:@"Cell"];
    
    LTCConcernViewController *viewController = [[LTCConcernViewController alloc] init];
    viewController.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.context];
    viewController.addConcernButton = [UIButton buttonWithType:UIButtonTypeSystem];
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

@end

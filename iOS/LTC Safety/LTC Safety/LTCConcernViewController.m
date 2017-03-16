//
//  LTCConcernViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernViewController.h"
#import "LTCNewConcernViewControllerDelegate.h"
#import "LTCConcernViewModel.h"
#import "LTCNewConcernViewController.h"
#import "LTCNewConcernViewModel.h"
#import "LTCConcernTableViewCell.h"
#import "LTCConcernDetailViewModel.h"
#import "LTCConcernDetailViewController.h"

@interface LTCConcernViewController () <LTCNewConcernViewControllerDelegate, LTCConcernViewModelDelegate, UITableViewDataSource, UITableViewDelegate>

/**
 The button that the user clicks to present the form for submitting a new concern.
 */
@property (nonatomic, weak) IBOutlet UIButton *addConcernButton;

/**
 <#Description#>
 */
@property (nonatomic, weak) IBOutlet UITableView *tableView;
@end

@implementation LTCConcernViewController


/**
 Sets the view model to provide the view controller with a new source of data. This should only be called once when the concern view controller is first initialized.

 @param viewModel The view model that will provide the view controller its concern data for displaying.
 */
- (void)setViewModel:(LTCConcernViewModel *)viewModel {
    
    NSAssert1(_viewModel == nil, @"Attempted to set %@'s view model when it already exists.", self);
    
    viewModel.delegate = self;
    _viewModel = viewModel;
}

/**
 Initial setup for the view controller to configure the title and add concern button.
 */
- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = NSLocalizedString(@"CONCERN_VIEW_TITLE", nil);
    
    NSAssert(self.addConcernButton != nil, @"Concern view controller did load with nil add concern button.");
    NSAssert(self.tableView != nil, @"Concern view controller did load with nil table view.");
    
    self.addConcernButton.layer.cornerRadius = 2.5f;
    self.addConcernButton.layer.borderColor = [[UIColor colorWithRed:0xE2/255.0 green:0xE2/255.0 blue:0xE2/255.0 alpha:0xE2/255.0] CGColor];
    self.addConcernButton.layer.borderWidth = 1.0f;
}

/**
 Action callback triggered when the add concern button is clicked.

 @param sender The sender of the action.
 */
- (IBAction)presentCreateConcernController:(id)sender {
    
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.viewModel.objectContext];
    LTCNewConcernViewController *viewController = [[LTCNewConcernViewController alloc] initWithViewModel:viewModel];
    viewController.delegate = self;
    
    UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController:viewController];
    [self presentViewController:navController animated:YES completion:nil];
}

- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern {
    
    NSAssert(concern != nil, @"Attempted to add a nil concern to the concern view model.");
    
    NSError *error = nil;
    [self.viewModel addConcern:concern error:&error];
    
    NSAssert(error == nil, @"Add concern failed with error: %@", error);
}

#pragma mark - UITableView data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.viewModel rowCountForSection:section];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    LTCConcernTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];
    LTCConcern *concern = [self.viewModel concernAtIndexPath:indexPath];
    
    NSAssert(concern != nil, @"Failed to fetch concern at index path: %@", indexPath);
    
    [cell configureWithConcern:concern];
    return cell;
}

#pragma mark - LTCConcernViewModel delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    LTCConcern *selectedConcrn = [self.viewModel concernAtIndexPath:indexPath];
    LTCConcernDetailViewModel *viewModel = [[LTCConcernDetailViewModel alloc] initWithConcern:selectedConcrn];
    LTCConcernDetailViewController *viewController = [[LTCConcernDetailViewController alloc] initWithViewModel:viewModel];
    
    [self.navigationController pushViewController:viewController animated:YES];
    
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)viewModel:(LTCConcernViewModel *)viewModel didInsertConcernsAtIndexPaths:(NSArray *)indexPaths {
    [self.tableView insertRowsAtIndexPaths:indexPaths withRowAnimation:UITableViewRowAnimationFade];
}

- (void)viewModel:(LTCConcernViewModel *)viewModel didDeleteConcernsAtIndexPaths:(NSArray *)indexPaths {
    [self.tableView deleteRowsAtIndexPaths:indexPaths withRowAnimation:UITableViewRowAnimationFade];
}

- (void)viewModel:(LTCConcernViewModel *)viewModel didMoveConcernFromIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
    [self.tableView moveRowAtIndexPath:fromIndexPath toIndexPath:toIndexPath];
}

- (void)viewModel:(LTCConcernViewModel *)viewModel didUpdateConcern:(LTCConcern *)concern atIndexPath:(NSIndexPath *)indexPath {
    LTCConcernTableViewCell *cell = [self.tableView cellForRowAtIndexPath:indexPath];
    [cell configureWithConcern:concern];
}

- (void)viewModelWillBeginUpdates:(LTCConcernViewModel *)viewModel {
    [self.tableView beginUpdates];
}

- (void)viewModelDidFinishUpdates:(LTCConcernViewModel *)viewModel {
    [self.tableView endUpdates];
}

@end

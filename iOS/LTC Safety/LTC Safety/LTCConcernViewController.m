//
//  LTCConcernViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernViewController.h"
#import "LTCNewConcernViewControllerDelegate.h"

@interface LTCConcernViewController () <LTCNewConcernViewControllerDelegate, UITableViewDataSource, UITableViewDelegate>

@end

@implementation LTCConcernViewController

#pragma mark - LTCNewConcernViewController delegate

- (IBAction)presentCreateConcernController:(id)sender {
    
}


- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern {
    
}

#pragma mark - UITableView data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return nil;
}

@end

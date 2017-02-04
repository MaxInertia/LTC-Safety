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

@interface LTCConcernViewController () <LTCNewConcernViewControllerDelegate, LTCConcernViewModelDelegate, UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, weak) IBOutlet UIButton *addConcernButton;
@property (nonatomic, weak) IBOutlet UITableView *tableView;
@end

@implementation LTCConcernViewController



@end

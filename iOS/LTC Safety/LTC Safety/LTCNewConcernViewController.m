//
//  LTCNewConcernViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCNewConcernViewController.h"

@interface LTCNewConcernViewController ()
@property (readonly, nonatomic, weak) LTCNewConcernViewModel *viewModel;
- (void)_submit;
- (void)_cancel;
@end

@implementation LTCNewConcernViewController
@dynamic viewModel;

@end

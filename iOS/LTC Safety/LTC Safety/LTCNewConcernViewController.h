//
//  LTCNewConcernViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XLForm/XLForm.h>
#import "LTCNewConcernViewControllerDelegate.h"
#import "LTCNewConcernViewModel.h"

@interface LTCNewConcernViewController : XLFormViewController
@property (nonatomic, weak) id <LTCNewConcernViewControllerDelegate>delegate;
- (instancetype)initViewModel:(LTCNewConcernViewModel *)viewModel;
@end

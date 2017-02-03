//
//  LTCConcernViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "LTCConcernDetailViewController.h"
#import "LTCConcernViewModel.h"

@interface LTCConcernViewController : UIViewController
@property (strong, nonatomic) LTCConcernDetailViewController *detailViewController;
@property (nonatomic, strong) LTCConcernViewModel *viewModel;
@end


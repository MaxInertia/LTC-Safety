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

/**
 The LTCConcernViewController class is used to display the client's previously submitted concerns. The concerns that its displayed are managed by the LTCConcernViewModel.
 */
@interface LTCConcernViewController : UIViewController

/**
 The view model used to model the concerns that are displayed within the view controller. This provides the view controller with the data necessary to display a concern cell for each concern.
 */
@property (nonatomic, strong) LTCConcernViewModel *viewModel;
@end


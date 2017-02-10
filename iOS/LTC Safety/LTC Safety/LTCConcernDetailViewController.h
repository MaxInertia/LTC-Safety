//
//  LTCConcernDetailViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCConcern+CoreDataClass.h"

/**
 The LTCConcernDetailViewController class is used for displaying to the user the data associated with a single submitted concern in the LTCConcernViewController.
 */
@interface LTCConcernDetailViewController : UIViewController

/**
 The concern who's information is being displayed in the detail view controller.
 */
@property (strong, nonatomic) LTCConcern *concern;
@end


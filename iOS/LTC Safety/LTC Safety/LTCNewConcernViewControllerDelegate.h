//
//  LTCNewConcernViewControllerDelegate.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCConcern+CoreDataClass.h"

@class LTCNewConcernViewController;

/**
 The LTCNewConcernViewControllerDelegate class is used to notify the delegate when a concern was successfully submitted to the client API on the backend server. LTCNewConcernViewController notifies its delegate the user provided concern data is submitted to the client API and a valid concern response is received.
 */
@protocol LTCNewConcernViewControllerDelegate <NSObject>
@required

/**
 Required method used notify the delegate whenever a new concern is submitted by the client.

 @pre The concern must be non-nil.
 @param viewController The view controller that the concern was submitted from.
 @param concern The concern that was submitted.
 */
- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern;
@end

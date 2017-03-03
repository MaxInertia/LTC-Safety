//
//  LTCLoadingViewController.h
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-25.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 The LTCLoadingViewController class is used to configure an alert controller that displays a loading spinner
 */
@interface LTCLoadingViewController : UIAlertController

/**
 Configure an alert controller that displays a loading spinner.
 @return The configured view controller for the loading display
 */
+ (instancetype)configure;
@end

//
//  LTCInputAlertControllerDelegate.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCInputAlertController.h"

@class LTCInputAlertController;

/**
 The LTCInputAlertControllerDelegate protocol is used by the LTCInputAlertController to alert its delegate when the user has finished providing input or dismissed the alert.
 */
@protocol LTCInputAlertControllerDelegate <NSObject>
@required

/**
 A required delegate method used to notify the delegate when the user has finished inputing text and tapped OK.

 @pre input.length > 0
 @param controller The alert controller the user was inputing text into.
 @param input The text the user has input.
 */
- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input;
@optional


/**
 An optional delegate method to notify the delgate when the user dismisses the alert.

 @param controller The controller that was dismissed.
 */
- (void)alertControllerDidCancel:(LTCInputAlertController *)controller;
@end

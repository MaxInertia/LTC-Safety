//
//  LTCInputAlertController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCInputAlertControllerDelegate.h"

/**
 The LTCInputAlertController class is used to display an alert with a textfield for user input. This is used to allow the user to enter custom input in the LTCValueSelectionController class.
 
 History properties: Instances of this class should not vary from the time they are created.
 
 Invariance properties: This class assumes that the delegate is valid and non-nil.
 
 */
@interface LTCInputAlertController : UIAlertController

/**
 The delegate that is notified when the user dimisses the alert or finishes inputing text.
 */
@property (nonatomic, weak) id <LTCInputAlertControllerDelegate>delegate;
@end

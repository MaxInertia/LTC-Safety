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
@protocol LTCInputAlertControllerDelegate <NSObject>
@required
- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input;
@optional
- (void)alertControllerDidCancel:(LTCInputAlertController *)controller;
@end

//
//  LTCInputAlertController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LTCInputAlertController;
@protocol LTCInputAlertControllerDelegate <NSObject>
@required
- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input;
@optional
- (void)alertControllerDidCancel:(LTCInputAlertController *)controller;
@end

@interface LTCInputAlertController : UIAlertController
@property (nonatomic, weak) id <LTCInputAlertControllerDelegate>delegate;
@end

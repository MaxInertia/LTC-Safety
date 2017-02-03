//
//  LTCInputAlertController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCInputAlertControllerDelegate.h"

@interface LTCInputAlertController : UIAlertController
@property (nonatomic, weak) id <LTCInputAlertControllerDelegate>delegate;
@end

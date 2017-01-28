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
@protocol LTCNewConcernViewControllerDelegate <NSObject>
@required
- (void)viewController:(LTCNewConcernViewController *)viewController didSubmitConcern:(LTCConcern *)concern;
@end

//
//  LTCConcernDetailViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCConcern+CoreDataClass.h"

@interface LTCConcernDetailViewController : UIViewController
@property (strong, nonatomic) LTCConcern *concern;
@end


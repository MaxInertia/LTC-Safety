//
//  LTCCategoryViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCCategoryViewController.h"

@implementation LTCCategoryViewController

/**
 The file name for the properties file that the value options are loaded from.
 
 @return The file name for the properties file that the category options are loaded from.
 */
- (NSString *)fileName {
    return @"Categories";
}

/**
 The boolean value for if the user can select other to type in their own value.
 @return YES, since the user should be able to type in their own concern nature.
 
 */
- (BOOL)hasOther {
    return YES;
}
@end

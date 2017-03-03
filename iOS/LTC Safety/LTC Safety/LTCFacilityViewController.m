//
//  LTCFacilityViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCFacilityViewController.h"

@implementation LTCFacilityViewController

/**
 The file name for the properties file that the value options are loaded from.

 @return The file name for the properties file that the facility options are loaded from.
 */
- (NSString *)fileName {
    return @"Facilities";
}

/**
 The boolean value for if the user can select other to type in their own value.
 @return NO, since the user should not be able to type in their own facility.
 
 */
- (BOOL)hasOther {
    return NO;
}

@end

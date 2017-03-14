//
//  LTCLogLevel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCLogLevel.h"

NSString *const LTCLogLevelSevere       = @"SEVERE";
NSString *const LTCLogLevelWarning      = @"WARNING";
NSString *const LTCLogLevelInfo         = @"INFO";

@implementation LTCLogLevelEnum

/**
 Converts a LTCLogLevel to the corresponding string value
 */
+ (NSString *)stringValue:(LTCLogLevel)value {
    switch (value) {
        case kLTCLogLevelSevere:
            return LTCLogLevelSevere;
        case kLTCLogLevelWarning:
            return LTCLogLevelWarning;
        case kLTCLogLevelInfo:
            return LTCLogLevelInfo;
    }
    NSAssert1(NO, @"Attempted to convert unknown enum value to string: %lu", (unsigned long)value);
    return nil;
}

/**
 Converts a string to the correspong LTCLogLevel
 */
+ (LTCLogLevel)enumValue:(NSString *)value {
    
    if ([value isEqualToString:LTCLogLevelSevere]) {
        return kLTCLogLevelSevere;
    } else if ([value isEqualToString:LTCLogLevelWarning]) {
        return kLTCLogLevelWarning;
    } else if ([value isEqualToString:LTCLogLevelInfo]) {
        return kLTCLogLevelInfo;
    }
    NSAssert1(NO, @"Attempted to convert unknown string value to enum: %@", value);
    return 0;
}

@end

//
//  LTCLogLevel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
/**
 The LTCLogLevel class represents the different types of logs that may be recoreded
 */
typedef NS_ENUM(NSUInteger, LTCLogLevel) {
    kLTCLogLevelSevere,
    kLTCLogLevelWarning,
    kLTCLogLevelInfo
};

@interface LTCLogLevelEnum : NSObject
/**
 Converts a LTCLogLevel to a String
 */
+ (NSString *)stringValue:(LTCLogLevel)value;
/**
 Converts a String to a LTCLogLevel
 */
+ (LTCLogLevel)enumValue:(NSString *)value;
@end
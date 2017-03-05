//
//  Logger.m
//  LTC Safety
//
//  Created by Niklaas Neijmeijer on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCLogLevel.h"
#import "LTCLogger.h"
@import Firebase;

@implementation LTCLogger

+ (void)configure {
    
    if ([FIRApp allApps].count == 0) {
        [FIRApp configure];
    }
}

+ (void)log:(NSString *)message level:(LTCLogLevel)level{
 
    [FIRAnalytics logEventWithName:kFIREventSelectContent
                        parameters:@{
                                     kFIRParameterItemID:[LTCLogLevelEnum stringValue:level],
                                     kFIRParameterItemName:message,
                                     kFIRParameterContentType:@"log"
                                     }];
}


@end

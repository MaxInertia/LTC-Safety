//
//  GTLRClientServiceMock.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "GTLRClientServiceMock.h"
#import "GTLRClientObjects_Testing.h"

@implementation GTLRClientServiceMock

- (GTLRServiceTicket *)executeQuery:(id<GTLRQueryProtocol>)queryObj
                  completionHandler:(void (^)(GTLRServiceTicket *ticket, id object,
                                              NSError *error))handler {
    if (self.simulateError) {
        NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
        handler(nil, nil, [NSError errorWithDomain:identifier code:0 userInfo:nil]);
    } else {
        handler(nil, [GTLRClient_Concern testConcern], nil);
    }
    return nil;
}

@end

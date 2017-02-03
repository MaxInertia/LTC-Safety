//
//  LTCNewConcernViewModelMock.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCNewConcernViewModelMock.h"
#import "LTCConcern_Testing.h"

@interface LTCNewConcernViewModel ()
@property (nonatomic, strong) NSManagedObjectContext *context;
@end

@implementation LTCNewConcernViewModelMock

- (void)submitConcernData:(void (^)(LTCConcern *concern, NSError *error))completion {
    
    if (self.simulateError) {
        NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
        completion(nil, [NSError errorWithDomain:identifier code:0 userInfo:nil]);
    } else {
        completion([LTCConcern testConcernWithContext:self.context], nil);
    }
}

@end

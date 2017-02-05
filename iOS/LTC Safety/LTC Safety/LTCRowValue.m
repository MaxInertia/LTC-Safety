//
//  LTCRowValue.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCRowValue.h"

NSString *const LTCTitleKey = @"title";

@interface LTCRowValue ()
@property (readwrite, nonatomic, copy) NSString *tag;
@property (readwrite, nonatomic, copy) NSString *title;
@end

@implementation LTCRowValue

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    
    NSAssert(dictionary != nil, @"Attempted to create a row value with a null dictionary.");
    NSAssert([dictionary valueForKey:LTCTitleKey] != nil, @"Row value dictionary did not contain expected key:%@", LTCTitleKey);
    
    // Extract the tag and call the designated initializer
    NSString *tag = [dictionary valueForKey:LTCTitleKey];
    return (self = [self initWithTag:tag]);
}

- (instancetype)initWithTag:(NSString *)tag {
    
    NSAssert(tag != nil, @"Attempted to initialize a row value with a nil tag");

    if (self = [super init]) {
        self.tag = tag;
        self.title = NSLocalizedString(self.tag, nil);
    }
    NSAssert(self.tag != nil, @"Initialization failed unexpectedly due to nil tag.");
    NSAssert(self.title != nil, @"Initialization failed unexpectedly due to nil title.");
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);

    return self;
}

@end

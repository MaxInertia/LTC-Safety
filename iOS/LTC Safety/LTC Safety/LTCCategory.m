//
//  LTCCategory.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCCategory.h"

NSString *const TITLE_KEY = @"title";

@interface LTCCategory ()
@property (readwrite, nonatomic, copy) NSString *title;
@end

@implementation LTCCategory

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    
    assert(dictionary != nil);
    assert([dictionary valueForKey:TITLE_KEY] != nil);
    
    if (self = [super init]) {
        self.title = [dictionary valueForKey:TITLE_KEY];
    }
    return self;
}

@end

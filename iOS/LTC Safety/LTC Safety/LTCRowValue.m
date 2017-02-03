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
    
    return nil;
}

- (instancetype)initWithTag:(NSString *)tag {
    
    return self;
}

@end

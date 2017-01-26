//
//  LTCCategory.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LTCCategory : NSObject
@property (readonly, nonatomic, copy) NSString *title;
- (instancetype)initWithDictionary:(NSDictionary *)dictionary;
@end

//
//  LTCRowValue.h 
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LTCRowValue : NSObject
@property (readonly, nonatomic, copy) NSString *title;
@property (readonly, nonatomic, copy) NSString *tag;
- (instancetype)initWithDictionary:(NSDictionary *)dictionary;
- (instancetype)initWithTag:(NSString *)tag;
@end

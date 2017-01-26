//
//  LTCCategoryLoader.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCCategory.h"

@interface LTCCategoryLoader : NSObject
+ (NSArray <LTCCategory *> *)loadCategories;
@end

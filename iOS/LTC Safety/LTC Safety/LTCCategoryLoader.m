//
//  LTCCategoryLoader.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCCategoryLoader.h"

@implementation LTCCategoryLoader

+ (NSArray <LTCCategory *> *)loadCategories {

    static NSArray <LTCCategory *> *categories = nil;
    if (categories == nil) {
        NSString *path = [[NSBundle mainBundle] pathForResource:@"Categories" ofType:@"plist"];
        NSArray *categoryDictionaries = [NSArray arrayWithContentsOfFile:path];
        
        assert(categoryDictionaries.count > 0);
        
        NSMutableArray *mutableCategories = [NSMutableArray arrayWithCapacity:categoryDictionaries.count];
        for (NSDictionary *dictionary in categoryDictionaries) {
            LTCCategory *category = [[LTCCategory alloc] initWithDictionary:dictionary];
            [mutableCategories addObject:category];
        }
        // copy to ensure it is immutable
        categories = [mutableCategories copy];
    }
    assert(categories.count > 0);
    return categories;
}

@end

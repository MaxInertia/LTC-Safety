//
//  LTCConcern_Testing.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcern+CoreDataClass.h"

/**
    Provides a class extension on LTCConcern to easilly create concerns populated with data for testing purposes.
 */
@interface LTCConcern (Testing)
+ (instancetype)testConcernWithContext:(NSManagedObjectContext *)context;
@end

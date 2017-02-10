//
//  LTCConcern_Testing.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcern+CoreDataClass.h"

/**
    Provides a class extension on LTCConcern to easily create concerns populated with data for testing purposes.
 */
@interface LTCConcern (Testing)

/**
 Creates an LTCConcern object pre-populated with test data.

 @param context The context that the concern should be inserted into. Although the entity isn't saved, Core Data requires that all entities are initialized with a valid object context.
 @return The LTCConcern object.
 */
+ (instancetype)testConcernWithContext:(NSManagedObjectContext *)context;

@end

//
//  LTCCoreDataTestCase.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-29.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

@import CoreData;

/**
 The LTCCoreDataTestCase is used for unit testing classes that require a managed object context. This class sets up and tears down an in-memory managed object context that can be used by any LTCCoreDataTestCase subclasses.
 */
@interface LTCCoreDataTestCase : XCTestCase

/**
 The in-memory managed object context used for testing classes that use CoreData.
 */
@property (readonly, nonatomic, strong) NSManagedObjectContext *context;
@end

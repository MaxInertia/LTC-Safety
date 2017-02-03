//
//  LTCCoreDataTestCase.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-29.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

@import CoreData;

@interface LTCCoreDataTestCase : XCTestCase
@property (readonly, nonatomic, strong) NSManagedObjectContext *context;
@end

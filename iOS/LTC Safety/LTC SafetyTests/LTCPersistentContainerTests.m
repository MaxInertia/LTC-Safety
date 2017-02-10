//
//  LTCPersistentContainerTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCPersistentContainer.h"

@interface LTCPersistentContainer ()
- (NSPersistentStoreCoordinator *)_loadStoreCoordinatorAtURL:(NSURL *)url withObjectModel:(NSManagedObjectModel *)model error:(NSError **)error;
- (NSManagedObjectModel *)_loadObjectModelWithName:(NSString *)name;
@end
/**
 Tests the functionality of the LTC persistent container view model.
 */
@interface LTCPersistentContainerTests : XCTestCase

@end

@implementation LTCPersistentContainerTests

/**
 Testing the initialization of a persistent container by allocating a new persistent container and then checking that the managedObjectModel, storeCoordinator, viewContext are all not nil. The test then checks that there is 1 persistent store and that there are entities for a LTCConcern, LTCReporter, LTCLocation, and LTCConcern Status.
 */
- (void)testInitWithName {
    NSString *name = @"LTC_Safety";
    LTCPersistentContainer *container = [[LTCPersistentContainer alloc] initWithName:name];
    
    XCTAssertNotNil(container.managedObjectModel);
    XCTAssertNotNil(container.storeCoordinator);
    XCTAssertNotNil(container.viewContext);
        
    XCTAssertEqual(container.storeCoordinator.persistentStores.count, 1);

    NSDictionary *entities = container.managedObjectModel.entitiesByName;
    XCTAssertNotNil(entities[@"LTCConcern"]);
    XCTAssertNotNil(entities[@"LTCReporter"]);
    XCTAssertNotNil(entities[@"LTCLocation"]);
    XCTAssertNotNil(entities[@"LTCConcernStatus"]);
}

/**
 Testing the initialization of a persistent container with corrupt data by providing it with a bad url path and checking that there is 1 persistent store and that it's type is in memory store.
 */
- (void)testInitWithCorruptDatabaseAndFailedLoad {
 
    LTCPersistentContainer *container = [[LTCPersistentContainer alloc] init];
    NSManagedObjectModel *model = [container _loadObjectModelWithName:@"LTC_Safety"];
    NSURL *url = [NSURL fileURLWithPath:@"/corruptTest"];
    
    // Load a store coordinator with an invalid path forcing it to fall back to an in memory store
    NSError *error = nil;
    NSPersistentStoreCoordinator *coordinator = [container _loadStoreCoordinatorAtURL:url withObjectModel:model error:&error];
    
    XCTAssertEqual(coordinator.persistentStores.count, 1);
    XCTAssertEqual(coordinator.persistentStores[0].type, NSInMemoryStoreType);
}

@end

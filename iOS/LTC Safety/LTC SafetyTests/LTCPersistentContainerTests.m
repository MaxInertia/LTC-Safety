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

@interface LTCPersistentContainerTests : XCTestCase

@end

@implementation LTCPersistentContainerTests

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

//
//  LTCCoreDataTestCase.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-29.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCCoreDataTestCase.h"

@interface LTCCoreDataTestCase ()

/**
 The store coordinator that the managed object context belongs to.
 */
@property (readwrite, nonatomic, strong) NSPersistentStoreCoordinator *coordinator;
@property (readwrite, nonatomic, strong) NSManagedObjectContext *context;
@end

@implementation LTCCoreDataTestCase

/**
 Creates a new in-memory managed object context for unit testing classes that use Core Data
 */
- (void)setUp {
    [super setUp];
    
    // Load the xcdatamodel from the application bundle
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"LTC_Safety" withExtension:@"momd"];
    NSManagedObjectModel *model = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    
    NSError *error = nil;
    self.coordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
    [self.coordinator addPersistentStoreWithType:NSInMemoryStoreType configuration:nil URL:nil options:nil error:&error];
    
    XCTAssertNil(error);
    
    self.context = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSMainQueueConcurrencyType];
    [self.context setPersistentStoreCoordinator:self.coordinator];
}

@end
